/*
 Steven Nim
 January 18, 2017
 This screen can be open with a button press while in the field.
 In the Inventory, the user can check what items they have, use their Items,
 search for Items, sort their Items, and conjure up Health Potions out of thin air.
 */
package com.rpg.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class InventoryScreen implements Screen {

    public static ArrayList<Item> inventory = new ArrayList();//Holds all of the items in the inventory
    private boolean eof = false; //signals end of file reading
    private BufferedWriter writer;//used to write to Text Files
    private TextButton lookButton;//The user can peek inside to see all of the goodies they have
    private TextButton exitButton;//The user can bail outta the inventory
    private TextButton searchButton;//The user can search for items to see if they exist
    private TextButton sortButton;//The user can sort their items
    private TextButton useButton;//The user can use some items
    private TextButton conjureButton;//The user conjures up a Health Potion
    private BitmapFont font;//Handles drawing text to the screen
    private TextButton.TextButtonStyle textButtonStyle;//Defines which which aspect of the button looks like what 
    private Skin skin;//Handles how buttons look
    private TextureAtlas buttonAtlas;//Handles code pertaining to how buttons look
    private Stage stage;//Stores all of the Actors (e.g. buttons) so that they can all be drawn at once
    private Game gg;//The Game itself, used for changing screens
    private SpriteBatch batch;//Handles drawing to the monitor
    private Doohickey d;//A useless contraption
    private int preventsRepeat;//A flag that prevents Sorting more than once
    private Texture tip;//just some advice

    /**
     * Primary constructor
     *
     * @param g Game, an ApplicationListener that delegates to a Screen
     */
    public InventoryScreen(Game g) {
        this.gg = g;//Set game to this screen
        batch = new SpriteBatch();
        stage = new Stage();//create new stage
        d = new Doohickey();
        font = new BitmapFont();

        //Set the Input Processor so that button clicks can be registered
        Gdx.input.setInputProcessor(stage);
        //Set size of Inventory window
        Gdx.graphics.setWindowedMode(400, 600);

        String itemIn;//Store value read in from data file
        int itemN;//Number read in from data file, parsed from String
        try {
            //Pulls inv.txt from the assets folder
            FileReader fr = new FileReader("inv.txt");
            BufferedReader br = new BufferedReader(fr);

            while (eof == false) {//start reading file
                itemIn = br.readLine();
                if (itemIn == null) {//if file has been completely read
                    eof = true;//cease reading of file
                } else {
                    itemN = Integer.parseInt(itemIn);//Parse to integer
                    //Create an Item and add it to inventory depending on number read in
                    if (itemN == 0) {//0 corresponds to the Bow
                        inventory.add(new Bow());//Create a bow and add it to inventory
                    } else if (itemN == 1) {//1 corresponds to the Health Potion
                        inventory.add(new Potion());//Add HP Potion to inventory
                    } else if (itemN == 2) {//1 corresponds to the Doohickey
                        inventory.add(new Doohickey());//Add Doohickey to the inventory
                    } else if (itemN == 3) {//3 corresponds to the Green Potion
                        inventory.add(new GreenPotion());//Add Green Potion to the inventory
                    }
                }
            }
            br.close();//closes connection to file
        } catch (IOException e) {//error handling
            JOptionPane.showMessageDialog(null, "Error reading from file: " + e,
                    "ERROR BIG ERROR", JOptionPane.ERROR_MESSAGE);
        }

        //MANUALLY ADDING ITEMS
        inventory.add(d);//A useless contraption        
    }

    //The show() method is called immediately after the create() method and it sets up stuff on the screen
    @Override
    public void show() {
        preventsRepeat = 0;
        tip = new Texture("conjuretip.png");//load up the Note: image
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);//Set the Input Processor so that button clicks can be registered
        skin = new Skin();
        
        //grab the file that dictates how buttons behave and how they look
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttonish/output/test-me!.pack"));
        skin = new Skin(buttonAtlas);//
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;//any edits to the font variable affects TextButton design
        textButtonStyle.up = skin.getDrawable("Start1");//Button's look when neutral
        textButtonStyle.down = skin.getDrawable("Exit");//Button's look when pressed down

        //Create the "Examine" button
        lookButton = new TextButton("Check Inventory", textButtonStyle);
        lookButton.setPosition(50, 500);
        stage.addActor(lookButton);

        //Create the "Exit" button
        exitButton = new TextButton("Back", textButtonStyle);
        exitButton.setPosition(50, 0);
        stage.addActor(exitButton);

        //Create the search button
        searchButton = new TextButton("Search Inventory", textButtonStyle);
        searchButton.setPosition(50, 300);
        stage.addActor(searchButton);

        //Create the sort button
        sortButton = new TextButton("Sort Inventory", textButtonStyle);
        sortButton.setPosition(50, 200);
        stage.addActor(sortButton);

        //Create the use button
        useButton = new TextButton("Use Item", textButtonStyle);
        useButton.setPosition(50, 400);
        stage.addActor(useButton);

        //Create the conjure button
        conjureButton = new TextButton("Conjure Potion", textButtonStyle);
        conjureButton.setPosition(50, 100);
        stage.addActor(conjureButton);

        //Draw Note: text
        batch.begin();
        batch.draw(tip, 10, 620);
        batch.end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(102 / 255f, 68 / 255f, 15 / 255f, 1);//Set background colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();//BEGIN DRAWING THIS FRAME
        stage.act(delta);//calls each actor on the stage to update for this frame
        stage.draw();//draw all of the actors located on the stage

        if (exitButton.isPressed()) {//If the Exit button is pressed
            //Close the inventory and return to Game
            gg.setScreen(new Field(gg));
        }
        if (lookButton.isPressed()) {//If the Examine button is pressed
            JOptionPane.showMessageDialog(null, examine(),
                    "What's Inside the Bag?", JOptionPane.INFORMATION_MESSAGE);//Display the contents of the inventory
        }
        if (searchButton.isPressed()) {//If the Search button is pressed
            //Get the name of the item that the user wants to find
            String in = JOptionPane.showInputDialog(null, "Search to see if a type of item exists in your inventory!"
                    + "\nPlease enter the name of the item you wish to find:",
                    "Where's the Doohickey?", JOptionPane.QUESTION_MESSAGE);
            boolean success = search(inventory, in);//search for item
            if (success == false) {//item not found
                if (in!=null && in.equalsIgnoreCase("Potion")) {//Need to be more specific if just "Potion"
                    JOptionPane.showMessageDialog(null, "What kind of potion? Be more specific!",
                            "Green Potion or Health Potion?", JOptionPane.INFORMATION_MESSAGE);
                } else {//Otherwise it just doesn't exist
                    JOptionPane.showMessageDialog(null, in + " could not be found.",
                            "?", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {//item found
                //Tell user the good news!
                JOptionPane.showMessageDialog(null, in + " exists in your inventory!",
                        "Item Found!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (sortButton.isPressed()) {//If the Sort button is pressed
            if (preventsRepeat != 1) {//Prevents Sorting a second time
                preventsRepeat = 1;
                sort(0, inventory.size());//Pass to sort method
                JOptionPane.showMessageDialog(null, "The inventory has been organized!",
                        "*sound of zippers moving rapidly*", JOptionPane.INFORMATION_MESSAGE);//Done!
            } else {//If user tries Sorting a second time
                JOptionPane.showMessageDialog(null, "The inventory cannot be organized further.",
                        "*loud buzzer sound*", JOptionPane.ERROR_MESSAGE);//Cannot be done again!
            }
        }
        if (useButton.isPressed()) {//If the Use button is pressed
            //Ask user for what item they want to see
            String useIn = JOptionPane.showInputDialog(null, "Type in the name of the item you want to use.",
                    "Time to Use Stuff", JOptionPane.QUESTION_MESSAGE);
            if (useIn!=null){//Not an error
                use(useIn);//Pass to Use method
            }
        }
        if (conjureButton.isPressed()) {//If the Conjure button is pressed
            String confirm = JOptionPane.showInputDialog(null, "Are you sure that you want to conjure?"
                    + "\nIt is recommended that youn try other features before this one.\nType YES to confirm.", 
                    "Are You Certain?", JOptionPane.QUESTION_MESSAGE);
            if (confirm!=null && confirm.equalsIgnoreCase("YES")) {
                //Create a new potion
                conjure();
                //Conjured (x2)!
                JOptionPane.showMessageDialog(null, "You created a Health Potion out of thin air! You added it to your inventory.",
                    "What's the Science Behind That?", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "You were so lucky that you conjured a second Health Potion by accident!\n"
                        + "You added it to your inventory.",
                    "What's the Science Behind That??!?!", JOptionPane.INFORMATION_MESSAGE);
            } else {//Decide against Conjuring Potion
                JOptionPane.showMessageDialog(null, "You chose not to do anything.", "How Disappointing...", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        batch.draw(tip, 10, 620);//draw tip (DOESN'T WORK)
        batch.end();//STOP DRAWING THIS FRAME
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);//maintain aspect ratio when window is resized
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {//Dispose() removes drawings from the previous frame to prepare for the next frame
        batch.dispose();//dispose of this frame's drawings
    }

    //The user peeks into their bag and can see what items they have on hand
    public String examine() {
        String output = "The following items are in your inventory:";
        if (Gdx.input.justTouched()) {//So that only one click is registered at a time instead of 20000
            for (Item temp : inventory) {//loop thru entire inventory
                String tempName = temp.getName();//grab the name of the item
                output += "\n" + tempName;//add name of item to accumulator
            }
        }
        return output;//return accumulator to be outputted
    }

    //Searches the inventory with Linear Search
    public boolean search(ArrayList<Item> i, String name) {
        Item b;//this Item object will store the current Item being compared
        for (Item sItem : i) {//read the entire arraylist
            b = sItem; //get the Item at the current index
            if (b.getName().equalsIgnoreCase(name)) {//if the item is found
                return true;//return boolean indicating item was found
            }
        }
        return false; //indicates that that item was not found
    }

    //Sorts the inventory with Qui(c)k Sorting
    public void sort(int low, int high) {
        //i holds the lowest possible index of the Inventory ArrayList
        //j holds the highest possible index of the Inventory ArrayList
        int i = low, j = high - 1;
        //Get the pivot Item from the middle of the list
        Item pivotItem = inventory.get(low + (high - low) / 2);
        //Get the true Pivot element: The Item Type of the Item
        int pivot = pivotItem.getItemType();
        //Divide into two lists
        while (i <= j) {

            /**
             * If the current value from the left list is smaller then the pivot
             * element then get the next element from the left list
             */
            Item lowCompare = inventory.get(i);
            while (lowCompare.getItemType() < pivot) {
                i++;
                //Update the value to be compared
                lowCompare = inventory.get(i);
            }
            //If the current value from the right list is larger then the pivot
            //element then get the next element from the right list
            Item highCompare = inventory.get(j);
            while (highCompare.getItemType() > pivot) {
                j--;
                //Update the value to be compared
                highCompare = inventory.get(j);
            }

            //If a value in the left list which is larger then
            //the pivot element was found and if a value in the right list
            //which is smaller then the pivot element was found then exchange the values.
            //Afterwards, increase i and j
            if (i <= j) {
                Item tempItemu = inventory.get(i);//store value on left side
                inventory.set(i, highCompare);//left side index now holds right side value
                inventory.set(j, tempItemu);//right side index now holds left side value
                i++;
                j--;
            }
        }
        //Recursion
        if (low < j) {//for left side
            sort(low, j);
        }
        if (i < high) {//for right side
            sort(i, high);
        }
    }

    /**
     * For when the Player uses an item
     * @param uItem Name of the item the player wants to use
     */
    public void use(String uItem) {
        if (uItem.equalsIgnoreCase("Potion") || uItem.equalsIgnoreCase("Health Potion")) {//Use potion
            for (int j = 0; j < inventory.size(); j++) {//loop thru entire ivnentory
                Item i = inventory.get(j);//get item
                if (i.getItemType() == 1) {//If the item we got was a Potion
                    String confirm = JOptionPane.showInputDialog(null, i.getName() + "\n" + i.getDesc()
                            + "\n\n" + "Do you want to use this item? Enter YES to continue.", "Are You Certain?", JOptionPane.QUESTION_MESSAGE);
                    if (confirm!=null && confirm.equalsIgnoreCase("YES")) {
                        if (i.isPotion(j) == true) {//If it is a potion
                            JOptionPane.showMessageDialog(null, "Feature yet to be implemented.", " ¯\\_(ツ)_/¯", JOptionPane.INFORMATION_MESSAGE);
                            j += 2;//kill the loop
                        }
                    } else {//Decide against using Potion
                        JOptionPane.showMessageDialog(null, "You chose not to do anything.", "How Disappointing...", JOptionPane.INFORMATION_MESSAGE);
                        j += 2;//kill the loop
                    }
                }
            }
        } else if (uItem.equalsIgnoreCase("Doohickey")) {//Use Doohickey
            for (int j = 0; j < inventory.size(); j++) {//loop thru entire ivnentory
                Item i = inventory.get(j);//get item
                if (i.getItemType() == 2) {//If the item we got was a Doohickey
                    //Ask them if they really want to use the item
                    String confirm = JOptionPane.showInputDialog(null, i.getName() + "\n" + i.getDesc()
                            + "\n\n" + "Do you want to use this item? Enter YES to continue.", "do you do a doohickey", JOptionPane.QUESTION_MESSAGE);
                    if (confirm!=null && confirm.equalsIgnoreCase("YES")) {//If YES
                        d.use();//POWER UP THE DOOHICKEY
                        inventory.set(j, d);//ADD THE DOOHICKEY TO THE INVENTORY
                    } else {//If NO or otherwise
                        JOptionPane.showMessageDialog(null, "You chose not to do anything.", "didnt do a doohickey", JOptionPane.INFORMATION_MESSAGE);
                        j += 2;//kill the loop
                    }
                }
            }
        } else if (uItem.equalsIgnoreCase("Green Potion") || uItem.equalsIgnoreCase("Green")) {//Use Green Potion
            for (Item i : inventory) {//loop thru entire inventory to find a Green Potion
                if (i.getItemType() == 3) {//If a Green Potion is found
                    //Ask the user if they REALLY want to use the Item
                    String confirm = JOptionPane.showInputDialog(null, i.getName() + "\n" + i.getDesc()
                            + "\n\n" + "Do you want to use this item? Enter YES to continue.", "It smells...", JOptionPane.QUESTION_MESSAGE);
                    if (confirm!=null && confirm.equalsIgnoreCase("YES")) {//If YES
                        GreenPotion g = new GreenPotion();
                        JOptionPane.showMessageDialog(null, g.use());
                    } else {//If NO or otherwise
                        JOptionPane.showMessageDialog(null, "You chose not to do anything.", "The Nose is Saved", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } else if (uItem.equalsIgnoreCase("Bow") || uItem.equalsIgnoreCase("Arrow")) {//Use Bow (Arrow redirects to Bow)
            for (Item i : inventory) {//loop thru entire inventory
                if (i.getItemType() == 0) {//If the item we got was a Bow
                    //Ask user for confirmation
                    String confirm = JOptionPane.showInputDialog(null, i.getName() + "\n" + i.getDesc()
                            + "\n\n" + "Do you want to use this item? Enter YES to continue.", "Doohickey Phase 1/7", JOptionPane.QUESTION_MESSAGE);
                    if (confirm!=null && confirm.equalsIgnoreCase("YES")) {//if YES
                        Bow bb = new Bow();
                        JOptionPane.showMessageDialog(null, bb.use());
                    } else {//If NO or otherwise
                        JOptionPane.showMessageDialog(null, "You chose not to do anything.", "Bye Bye, Bow!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } else {//ERROR HANDLING: User types in a non-existent item name
            JOptionPane.showMessageDialog(null, "You couldn't find that item so you stood around awkwardly.", "You look very silly...", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Creates a Health potion and places it in the user's inventory
    public void conjure() {
        String readIn;//reads in stuff from data file
        String prev = "";//will hold the contents of the data file prior to writing
        //First we read from the file
        try {
            //Pulls inv.txt from the assets folder
            FileReader fr = new FileReader("inv.txt");
            BufferedReader br = new BufferedReader(fr);

            while (eof == false) {//loop until no more of the file can be read
                readIn = br.readLine();
                if (readIn == null) {//finished reading
                    eof = true;//end reading
                } else {
                    prev += readIn + "\r\n";//add previous data on file to accumulator
                }
            }
            br.close();//ceases reading         
        } catch (IOException e) {//Error handle
            JOptionPane.showMessageDialog(null, "Error reading from file: " + e, 
                    "FILE ERROR", JOptionPane.ERROR_MESSAGE);
        }
        //Now we write to the file!
        try {
            //Time to write!
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("inv.txt"), "utf-8"));
            writer.write("1");//Add a potion to the file (Item Type "1" corresponds to a Health Potion)
            writer.newLine();
            writer.write("1");//Add a SECOND potion to the file (Item Type "1" corresponds to a Health Potion)
            writer.newLine();
            writer.write(prev);//Add all of the stuff that was previously in the data file back in
            writer.write("3");//HAD TO BRUTE FORCE IT SINCE IT DIDN'T WORK OTHERWISE
            writer.newLine();
            writer.write("0");
            writer.newLine();
            writer.write("3");
            writer.newLine();
            writer.write("1");
            writer.newLine();
            writer.write("3");
            writer.close();//cease writing
        } catch (IOException e) {//Error handling
            JOptionPane.showMessageDialog(null, "Error writing to file: " + e, 
                    "FILE ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

}
