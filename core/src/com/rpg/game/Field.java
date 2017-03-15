/**
 * Kyle Wilson 
 * January 18, 2017 
 * The main class for the gameplay.
 */
package com.rpg.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class Field implements Screen {

    private static final int FRAME_COLS = 3, FRAME_ROWS = 1;
    public static final int LOOP = 2;

    private ArrayList<Enemy> enemies;
    private Iterator<Enemy> enemyIterator;
    private float timer;

    BitmapFont yourBitmapFontName, talkText;

    OrthographicCamera camera;

    Body body, body2;
    //World world;
    private Game game;//The Game itself, for changing Screens

    Texture walkSheet;//walking spritesheet for the player
    //boundaries for the player, npcs, and enemy
    Rectangle boundsPlayer, boundsEnemy, boundsNPC, boundsNPC2, boundary, screenBounds;
    Polygon polygonPlayer, polygonEnemy, polygonNPC, polygonNPC2, polygonBoundary;

    SpriteBatch spriteBatch;//draws stuff
    float frameTime;
    Vector2 position;
    Vector2 positionEnemy;
    final int DOWN = 0, LEFT = 1, RIGHT = 2, UP = 3;
    final int eDOWN = 0, eLEFT = 1, eRIGHT = 2, eUP = 3;
    float moveSpeed, delta, enemyMoveSpeed;
    boolean move, moving;//used for spritesheet animations; used to detect when to use idle animation
    Animation <TextureRegion>animation;//handles player animation
    Animation animationIdle;//handles player idle animation
    Animation animationCombat;//handles player combat animation
    Animation <TextureRegion>animationEnemy;//handles enemy animation
    //Animation animationIdleBlink;//handles player animation
    Animation <TextureRegion>animationNPC1;//handles npc animation
    Animation <TextureRegion>animationNPC2;
    //Used for drawing current frames for all Characters
    TextureRegion currentFrame, currentEnemyFrame, currentNPCFrame, currentNPC2Frame;
    
    //Spritesheets of all Characters
    Texture spriteSheet;
    Texture spriteSheetIdle;
    Texture spriteSheetIdleBlink;
    Texture spriteSheetCombat;
    Texture spriteSheetEnemy;
    Texture spriteSheetNPC;
    Texture spriteSheetNPC2;
    TextureRegion textureRegion;
    TextureRegion[] walkFrames;

    Texture img;
    //Stores frames of animation of the characters
    TextureRegion[][] frames;
    TextureRegion[][] idleFrames;
    TextureRegion[][] combatFrames;
    TextureRegion[][] enemyFrames;
    TextureRegion[][] idleBlinkFrames;
    TextureRegion[][] npc1Frames;
    TextureRegion[][] npc2Frames;
    public Character player;//This is you!
    public Enemy enemy;//Hostile NPC
    public NPC npc;//friendly NPC
    public NPC npc2;//NPC that does not exist
    int direction = DOWN, prevDirection;
    float xDistance, yDistance;
    int enemyDirection = DOWN, prevEnemyDirection;
    private String npc1text = "NPC: Hello! You can press B to return to the main menu!";
    private String npc2text = "NPC: Hold 'O' to do your sword attack.";
    private ArrayList<Arrow> arrowsActive = new ArrayList<Arrow>();//arrows that are on the screen
    private ArrayList<Arrow> arrowsInactive = new ArrayList<Arrow>();//arrows that have been trashed
    private Texture background;//the background of the game field

    public Field(Game g) {
        Gdx.graphics.setWindowedMode(800, 500);//Set window size to 1000x500 pixels
        this.game = g;//Store the Game in a variable
        background = new Texture("Background.png");
        timer = 0;//start the timer
        
        //Create every Character
        player = new Character("", 100, 200, 300, 0, 0, 0);
        enemy = new Enemy("", 100, 150, 150, 0, 0, 0, 0);
        npc = new NPC("", 100, 670, 150, 0, 0, 0f, "");
        npc2 = new NPC("", 100, 880, 150, 0, 0, 0f, "");
        
        yourBitmapFontName = new BitmapFont();
        talkText = new BitmapFont();

        spriteBatch = new SpriteBatch(); //create the SpriteBatch
        camera = new OrthographicCamera(); //create the camera

        moveSpeed = 100f; //Move Speed for player
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //get dimensions for camera

        spriteSheet = new Texture(Gdx.files.internal("Character.png")); //find the character spriteSheet
        frames = TextureRegion.split(spriteSheet, spriteSheet.getWidth() / 3, spriteSheet.getHeight() / 4); //divide the sprite sheet
        animation = new Animation(0.05f, frames[0]); //create animation accepting all frames

        //everything that has to do with sprite creating down below
        spriteSheetIdle = new Texture(Gdx.files.internal("CharacterIdle.png"));
        idleFrames = TextureRegion.split(spriteSheetIdle, spriteSheetIdle.getWidth() / 1, spriteSheetIdle.getHeight() / 4);
        animationIdle = new Animation(0.05f, idleFrames[0]);

        //combat for player
        spriteSheetCombat = new Texture(Gdx.files.internal(("CharacterCombat.png")));
        combatFrames = TextureRegion.split(spriteSheetCombat, spriteSheetCombat.getWidth() / 2, spriteSheetCombat.getHeight() / 4);
        animationCombat = new Animation(1f, combatFrames[0]);

        //enemy
        spriteSheetEnemy = new Texture(Gdx.files.internal("Enemy.png"));
        enemyFrames = TextureRegion.split(spriteSheetEnemy, spriteSheetEnemy.getWidth() / 3, spriteSheetEnemy.getHeight() / 4);
        animationEnemy = new Animation(0.5f, enemyFrames[0]);

        //NPC
        spriteSheetNPC = new Texture(Gdx.files.internal("NPC1.png"));
        npc1Frames = TextureRegion.split(spriteSheetNPC, spriteSheetNPC.getWidth() / 20, spriteSheetNPC.getHeight() / 1);
        animationNPC1 = new Animation(0.15f, npc1Frames[0]);

        //NPC2
        spriteSheetNPC2 = new Texture(Gdx.files.internal("NPC2.png"));
        npc2Frames = TextureRegion.split(spriteSheetNPC2, spriteSheetNPC2.getWidth() / 1, spriteSheetNPC2.getHeight() / 1);
        animationNPC2 = new Animation(0.15f, npc2Frames[0]);
        
        //boundary set for player
        boundsPlayer = new Rectangle(0, 0, 32, 20); //create rectangle
        polygonPlayer = new Polygon(new float[]{0, 0, boundsPlayer.width, 0, boundsPlayer.width, boundsPlayer.height, 0, boundsPlayer.height}); //get dimensions for player
        polygonPlayer.setOrigin(boundsPlayer.width / 2, boundsPlayer.height / 2); //set the polygon dimensions

        //boundary for enemy
        boundsEnemy = new Rectangle(0, 0, 50, 50);
        polygonEnemy = new Polygon(new float[]{0, 0, boundsEnemy.width, 0, boundsEnemy.width, boundsEnemy.height, 0, boundsEnemy.height});
        polygonEnemy.setOrigin(boundsEnemy.width / 2, boundsEnemy.height / 2);

        //boundary for npc
        boundsNPC = new Rectangle(npc.xPos, npc.yPos, 75, 100);
        polygonNPC = new Polygon(new float[]{0, 0, boundsNPC.width, 0, boundsNPC.width, boundsNPC.height, 0, boundsNPC.height});
        polygonNPC.setOrigin(boundsNPC.width / 2, boundsNPC.height / 2);

        //boundary for npc2
        boundsNPC2 = new Rectangle(npc2.xPos, npc2.yPos, 45, 70); //mess with this to change the boundary on the second NPC, if it doesn't work properly just get rid of the second npc
        polygonNPC2 = new Polygon(new float[]{0, 0, boundsNPC2.width, 0, boundsNPC2.width, boundsNPC2.height, 0, boundsNPC.height});
        polygonNPC2.setOrigin(boundsNPC2.width / 2, boundsNPC2.height / 2);

        //boundary for enemy action
        boundary = new Rectangle(150, 150, 300, 300);
        polygonBoundary = new Polygon(new float[]{0, 0, boundary.width, 0, boundary.width, boundary.height, 0, boundary.height});
        polygonBoundary.setOrigin(boundary.width / 2, boundary.height / 2);
        
        //boundary for Screen
        screenBounds = new Rectangle(0, 0, 800, 500);
    }

    public void respawnEnemy() {
        enemy = new Enemy("", 100, 180, 180, 0, 0, 0, 0); //respawn a new enemy with same attributes
    }

    public void respawnPlayer() {
        player = new Character("", 100, 200, 300, 0, 0, 0); //respawn a new player with same attributes
    }

    @Override
    public void render(float d) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //sets the background
        prevDirection = direction;//update player direction
        prevEnemyDirection = enemyDirection;//update enemy direction
        delta = Gdx.graphics.getDeltaTime();//get current frame
        frameTime += delta;//accumulate total frame time
        move = false;
        moving = false;

        //set position of each boundary on the objects
        polygonPlayer.setPosition(player.xPos, player.yPos);
        polygonPlayer.setRotation(player.rotation);
        polygonEnemy.setPosition(enemy.xPos, enemy.yPos);
        polygonEnemy.setRotation(enemy.rotation);
        polygonNPC.setPosition(npc.xPos, npc.yPos);
        polygonNPC.setRotation(npc.rotation);
        polygonNPC2.setPosition(npc2.xPos, npc2.yPos);
        polygonNPC2.setRotation(npc2.rotation);
        
        //boundary
        polygonBoundary.setPosition(150, 100);
        //if the player goes inside the boundary the enemy will go towards the player
        if (Intersector.overlapConvexPolygons(polygonPlayer, polygonBoundary)) {
            int XorY = (int) (Math.random() * 5);
            if (enemy.getXPos() > player.getXPos() + 45 && XorY == 1) {
                enemy.setXPos(enemy.getXPos() - 2);
                enemyDirection = eRIGHT;
            } else if (enemy.getXPos() < player.getXPos() - 45 && XorY == 2) {
                enemy.setXPos(enemy.getXPos() + 2);
                enemyDirection = eUP;
            }
            if (enemy.getYPos() > player.getYPos() + 60 && XorY == 3) {
                enemy.setYPos(enemy.getYPos() - 2);
                enemyDirection = eLEFT;
            } else if (enemy.getYPos() < player.getYPos() - 30 && XorY == 4) {
                enemy.setYPos(enemy.getYPos() + 2);
                enemyDirection = eDOWN;
            }
        }

        int change = (int) (moveSpeed * delta);
        if (Gdx.input.isKeyPressed(Keys.A)) { //player goes left
            if (player.xPos > -10) {//only if they don't leave the boundary
                move = true;
                player.xPos -= change;
                direction = LEFT;
            }
        } else if (Gdx.input.isKeyPressed(Keys.D)) { //player goes right
            if (!(player.xPos > 750)) {//only if they don't leave the boundary
                move = true;
                player.xPos += change;
                direction = RIGHT;
            }
        } else if (Gdx.input.isKeyPressed(Keys.W)) { //player goes up
            if (!(player.yPos > 450)) {//only if they don't leave the boundary
                move = true;
                player.yPos += change;
                direction = UP;
            }
        } else if (Gdx.input.isKeyPressed(Keys.S)) { //player goes down
            if (player.yPos > 0) {//only if they don't leave the boundary
                move = true;
                player.yPos -= change;
                direction = DOWN;
            }
        } else { //if none of the keys are being pressed the move is set to false
            move = false;
        }

        //if the player is hitting the npc after being moved
        if (Intersector.overlapConvexPolygons(polygonPlayer, polygonNPC)) {
            //if they're coming from the right
            switch (direction) {
                case RIGHT:
                    //move them back to the left
                    player.xPos -= change + 2;
                    break;
                case LEFT:
                    //move them back to the right
                    player.xPos += change + 2;
                    break;
                case UP:
                    //move them back down
                    player.yPos -= change + 2;
                    break;
                case DOWN:
                    //move them back up
                    player.yPos += change + 2;
                    break;
                default:
                    break;
            }
        }

        if (Intersector.overlapConvexPolygons(polygonPlayer, polygonNPC2)) {
            //if they're coming from the right
            switch (direction) {
                case RIGHT:
                    //move them back to the left
                    player.xPos -= change + 2;
                    break;
                case LEFT:
                    //move them back to the right
                    player.xPos += change + 2;
                    break;
                case UP:
                    //move them back down
                    player.yPos -= change + 2;
                    break;
                case DOWN:
                    //move them back up
                    player.yPos += change + 2;
                    break;
                default:
                    break;
            }
        }

        //Draw the Background
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0);//draw the background
        spriteBatch.end();
        
        if (Intersector.overlapConvexPolygons(polygonPlayer, polygonNPC)) {
            spriteBatch.begin();
            talkText.setColor(Color.RED);
            talkText.draw(spriteBatch, npc1text, 250, 25); //display the text on screen
            spriteBatch.end();
        }

        if (Intersector.overlapConvexPolygons(polygonPlayer, polygonNPC2) && Gdx.input.isKeyPressed(Keys.T)) {
            spriteBatch.begin();
            talkText.setColor(Color.RED);
            talkText.draw(spriteBatch, npc2text, 300, 25); //display the text on screen
            spriteBatch.end();
        }

        //When the Player and the enemy overlaps, the Player will be pushed away from the enemt
        if (Intersector.overlapConvexPolygons(polygonPlayer, polygonEnemy)) {
            switch (direction) {
                case RIGHT://hit an enemy from YOUR right, enemy's left
                    //move them back to the left
                    player.xPos -= change;
                    break;
                case LEFT:
                    //move them back to the right
                    player.xPos += change;
                    break;
                case UP:
                    //move them back down
                    player.yPos -= change;
                    break;
                case DOWN:
                    //move them back up
                    player.yPos += change;
                    break;
                default:
                    break;
            }
        }

        if (move == false) {//player stops moving
            animation = new Animation(0.05f, idleFrames[direction]); //activate idle frames
        } else {
            animation = new Animation(0.15f, frames[direction]); //activate walk frames
        }

        if (direction != prevDirection) {
            animation = new Animation(0.15f, frames[direction]); //activate walk frames
            frameTime = 0; //restart animation
        }

        if (enemyDirection != prevEnemyDirection) {
            animationEnemy = new Animation(0.5f, enemyFrames[enemyDirection]); //activate the enemy frames
            frameTime = 0; //restart animation
        }

        if (Gdx.input.isKeyPressed(Keys.O)) {//User attacks with their sword if O is pressed
            moveSpeed = 0f;
            animation = new Animation(0.5f, combatFrames[direction]); //activate combat frames
        } else {
            moveSpeed = 150f;
        }
        //Check to see if the player hit an enemy
        if (Gdx.input.isKeyPressed(Keys.O) && Intersector.overlapConvexPolygons(polygonPlayer, polygonEnemy)) {
            enemy.health--; //enemy health is subtracted if getting attacked
        }
        
        if (Gdx.input.isKeyPressed(Keys.P)) {//Arrow will fire if P is pressed
            if (Gdx.input.isKeyJustPressed(Keys.P)) {//if P is pressed
                //Add a new Active arrow (still being drawn onto screen)
                arrowsActive.add(new Arrow(player.getXPos() + 5, player.getYPos() + 25, 0));
            }
        }
        

        if (enemy.health == 0) {//If the user runs out of HP, they die
            respawnEnemy(); //respawn the enemy if the enemies health reaches 0
        }

        if (Intersector.overlapConvexPolygons(polygonPlayer, polygonEnemy)) {
            player.health -= .1; //player loses health
        }
        
        if (Gdx.input.isKeyPressed(Keys.B)) {//Pressing the B key sends the user back to Main Menu
            game.setScreen(new MainMenu(game));
        }
        
        if (Gdx.input.isKeyPressed(Keys.I)) {//Pressing the I key lets the user access their Inventory
            game.setScreen(new InventoryScreen(game));
        }

        //get the animation
        currentFrame = animation.getKeyFrame(frameTime, true);
        currentEnemyFrame = animationEnemy.getKeyFrame(frameTime, true);
        currentNPCFrame = animationNPC1.getKeyFrame(frameTime, true);
        currentNPC2Frame = animationNPC2.getKeyFrame(frameTime, true);

        if (player.health <= 0) {
            //GameOver Screen
            respawnPlayer(); //respawn the player when the health reaches 0
        }
        
        //Formats HP
        DecimalFormat hpFormat = new DecimalFormat("#,###.##");
        
        //Continuously update the player and enemy hp
        CharSequence str = "Health: " + hpFormat.format(player.health) 
                + "\nEnemy Health: " + hpFormat.format(enemy.health);

        spriteBatch.begin();
        spriteBatch.draw(currentFrame, player.xPos, player.yPos); //draw the player
        spriteBatch.draw(currentEnemyFrame, enemy.xPos, enemy.yPos); //draw the enemy
        spriteBatch.draw(currentNPCFrame, npc.xPos, npc.yPos, 100, 100); //draw the npc
        //spriteBatch.draw(currentNPC2Frame, npc2.xPos, npc2.yPos, 100, 100); //draw the second npc
        
        for (Arrow a : arrowsActive) {//draw all activearrows
            a.draw(spriteBatch);
        }
        
        yourBitmapFontName.setColor(Color.RED); //set the font color to red
        yourBitmapFontName.draw(spriteBatch, str, 25, 40); //draw the words
        spriteBatch.end();
        
        //Update arrows
        for (Arrow a : arrowsActive) {
            a.update(delta);//Update the arrow's position
            if (a.toErase() == true) {//If the arrow has lasted over 2 seconds
                this.arrowsInactive.add(a);//archive it
            }
        }
        while (arrowsInactive.size() != 0) {//delete all inactive arrows
            arrowsActive.remove(arrowsInactive.get(0));
            arrowsInactive.remove(0);
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();//delete this frame's sprite drawings
        walkSheet.dispose();//delete this frame's spritesheets
        arrowsInactive.clear();//Clear the dead arrows arraylist for this frame
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(true, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {
        Gdx.graphics.setWindowedMode(800, 500);//Set window size to 1000x500 pixels
        animation = new Animation<TextureRegion>(0.15f, frames[0]);
        animationEnemy = new Animation<TextureRegion>(0.15f, frames[0]);
    }

    @Override
    public void hide() {
        
    }

}
