/**
 * Steven Nim 
 * January 18, 2017 
 * An arbitrary class that exists solely to set the title of the game.
 */
package com.rpg.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class BaseGame extends Game {
    private Game game;//used for screen switching

    @Override
    public void create() {
        Gdx.graphics.setTitle("An RPG Game");//set title of program
        game = this;
        setScreen(new MainMenu(game));//immediately pass it over to the Main Menu
    }
}
