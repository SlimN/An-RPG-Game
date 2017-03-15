/*
Kyle Wilson, steven Nim
January 18, 2017
Characters are drawable Sprites with hitboxes and sometimes movement.
Their position can be manipulated.
 */

package com.rpg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Character {
    
    public String name;
    public double health;//hp
    public int xPos;//x position
    public int yPos;//y position
    public int width;
    public int height;
    public float rotation;//direction character is facing
    public Rectangle hitbox;//the arrow's hitbox
    public Texture img;//the image of the character that appears on screen
    public Sprite spr;//the image of the character that appears on screen, as a Sprite
    
    //Primary constructor
    public Character() {
        name = "";
        health = 0;
        xPos = 0;
        yPos = 0;
        width = 0;
        height = 0;
        rotation = 0f;
    }
    
    /**
     * Secondary constructor
     * @param name
     * @param health
     * @param xPos
     * @param yPos
     * @param width
     * @param height
     * @param rotation 
     */
    public Character(String name, double health, int xPos, int yPos, int width, int height, float rotation) {
        this.name = name;
        this.health = health;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }
    
    /**
     * moves player to new spot based on given x,y coordinates
     * @param x given x position
     * @param y given y position
     */
    public void move(int x, int y) {
        xPos = x;
        yPos = y;
    }
    
    /**
     * Set rotation of the character
     * @param r given rotation
     */
    public void setRotation(int r) {
        rotation = r;
    }
    //Get the character's rotation
    public float getRotation() {
        return rotation;
    }
    //set character's height
    public void setHeight(int h) {
        height = h;
    }
    //set character's width
    public void setWidth(int w) {
        width = w;
    }
    //get character height
    public int getHeight() {
        return height;
    }
    //get character width
    public int getWidth() {
        return width;
    }
    //set character x position
    public void setXPos(int x) {
        xPos = x;
    }
    //set character y position
    public void setYPos(int y) {
        yPos = y;
    }
    //get character x position
    public int getXPos() {
        return xPos;
    }
    //get character y position
    public int getYPos() {
        return yPos;
    }
    //set character hp
    public void setHealth(double h) {
        health = h;
    }
    //get character hp
    public double getHealth() {
        return health;
    }
    
    //Sets the image of the character
    public void setTexture(Texture t) {
        this.img = t;
    }
    
    //Gets the image of the character
    public Texture getTexture() {
        return img;
    }
    
    /**
     * Draws something relative to the player's coordinates
     * Used for drawing arrows relative to Player's position
     * @param batch 
     */
    public void draw(SpriteBatch batch){
        batch.draw(img, hitbox.x, hitbox.y, 50, 13);
    }
    
    //Unused combat method
    public boolean getCombat() {
        return false;
    }
    
    //Unused toString() method
    public String toString() {
        String str = "";
        return str;
    }

}
