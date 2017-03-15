/*
Steven Nim
January 19, 2017
The Main Menu is where the user can learn how to play the game, and actually play the game too.
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

public class MainMenu implements Screen {
    private SpriteBatch batch;
    private Game game;
    private Stage stage;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;
    private Skin skin;//how buttons look, dictated by buttonAtlas
    private TextureAtlas buttonAtlas;//will contain code for manipulating buttons
    private Texture titleTexture;//The Title image
    private TextButton startButton;//Starts the game
    private TextButton creditButton;//Displays credits
    private TextButton exitButton;//Exits the user from the game
    private TextButton howButton;//Tells the user how to play the game

    public MainMenu(Game g) {
        Gdx.graphics.setWindowedMode(600, 500);//Resize screen
        game = g;//Update Game to this screen
        stage = new Stage();
        font = new BitmapFont();//So that we can have words on the screen
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);//so that we can click the buttons we draw
        batch = new SpriteBatch();//This batch handles drawing to the screen
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/output/code.pack"));//load up on the button code
        skin = new Skin(buttonAtlas);//apply button code to a new skin for buttons
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;//edits to the font variable affect the font of the TextButtons
        textButtonStyle.up = skin.getDrawable("Start1");//dictate what button looks like unpressed
        textButtonStyle.down = skin.getDrawable("Exit");//dictate what button looks like when pressed

        //Create the Start button and add it to the stage
        startButton = new TextButton("Start", textButtonStyle);
        startButton.setPosition(150, 310);
        stage.addActor(startButton);

        //Create the Credits button and add it to the stage
        creditButton = new TextButton("Credits", textButtonStyle);
        creditButton.setPosition(150, 110);
        stage.addActor(creditButton);
        
        //Create the "How to Play" button and add it to the stage
        howButton = new TextButton("How to Play", textButtonStyle);
        howButton.setPosition(150, 210);
        stage.addActor(howButton);

        //Create the Exit button and add it to the stage
        exitButton = new TextButton("Exit Game", textButtonStyle);
        exitButton.setPosition(150, 10);
        stage.addActor(exitButton);
        
        //Prepare the title
        titleTexture = new Texture("FinalTitle.png");
    }

    @Override
    public void render(float delta) {       
        Gdx.gl.glClearColor(102 / 255f, 68 / 255f, 15 / 255f, 1);//dark brownish background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //First batch draws the Title on the screen
        batch.begin();
        batch.draw(titleTexture, 165, 425);
        batch.end();
        
        //Second Batch draws the elements on the stage
        batch.begin();
        stage.act(delta);//calls each actor on the stage to update each frame
        stage.draw();//draw all of the actor located on the stage

        if (startButton.isPressed()) {//If the Start button is pressed
            game.setScreen(new Field(game));//Change to the main game screen
        }

        if (creditButton.isPressed()) {//If the Credits button is pressed
            game.setScreen(new Credits(game));//Change to the Credits screen
        }
        
        if (howButton.isPressed()) {//If the How to Play button is pressed
            game.setScreen(new HowToPlayScreen(game));//Change to the How to Play screen
        }

        if (exitButton.isPressed()) {//If the Exit button is pressed
            System.exit(0);//Close the entire Program
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
    public void dispose() {
        batch.dispose();//dump the previous frame's drawings
    }
    
    //Clears the screen with a white color
    private void clearWhite() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
