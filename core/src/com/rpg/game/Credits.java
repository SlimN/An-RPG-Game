/*
Steven Nim
January 17, 2017
The Credits Screen of the RPG Game.
 */
package com.rpg.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Credits implements Screen{
    private Game game;
    private SpriteBatch batch;
    private Stage stage;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private String output;
    private TextButton backButton;
    
    /**
     * Primary constructor
     * @param g Instance of the previous screen
     */
    public Credits(Game g){
        Gdx.graphics.setWindowedMode(600, 500);//Resize screen
        game = g;//accept instance of game       
        stage = new Stage();//create new stage; stages hold buttons and such and displays them
        font = new BitmapFont();//So that we can have words on the screen
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);//So that we can display objects on the stage to the monitor
        batch = new SpriteBatch();
        //Get info on how certain skins should be displayed
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/output/code.pack"));
        skin = new Skin(buttonAtlas);
        
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("Start1");
        textButtonStyle.down = skin.getDrawable("Exit");
        //Create the Back button and add it to the stage
        backButton = new TextButton("Return to Main Menu", textButtonStyle);
        backButton.setPosition(300, 20);
        stage.addActor(backButton);
        
        //Create output string
        output = "Contributors:\n\nSteven Nim\nItem & character programming, research, combining final program"
                + "\n\nKyle Wilson\nCharacter programming, research, sprites & spritesheets\n\n"
                + "Umair Ahmed\nMap design, research";
        
        batch.begin();
        font.draw(batch, output, 50, 400);//draw output string to screen
        batch.end();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(102 / 255f, 68 / 255f, 15 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //First batch renders text
        batch.begin();
        font.setColor(255 / 255f, 255 / 255f, 253 / 255f, 1);
        font.draw(batch, output, 30, 420);
        batch.end();
        
        //Second batch renders buttons
        batch.begin();
        stage.act(delta);//calls each actor on the stage to update each frame
        stage.draw();//draw all of the actor located on the stage
        
        if (backButton.isPressed()) {//if the user presses the Back button
            game.setScreen(new MainMenu(game));//Return to the Main Menu screen
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
}
