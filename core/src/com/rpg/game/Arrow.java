/*
 Steven Nim
 December 21, 2016
 An arrow is a projectile fired from a bow.
 There is currently no hit detection for arrows on enemies.
 */
package com.rpg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Arrow extends Item{
    private Rectangle hitbox;//the arrow's hitbox
    public float x;//x-position of the arrow
    public float y;//y-position of the arrow
    private float a;//angle at which the arrow is fired in
    private float time;//The amount of time an arrow lasys for before blinking out of existence
    private Vector2 position;//A 2D position vector
    private float speed;//number of pixels the arrow moves across the screen a second
    
    /**
     * Primary constructor
     * @param gX given starting x position
     * @param gY given starting y position
     * @param angle the angle at which the arrow is being fired
     */
    public Arrow(float gX, float gY, float angle) {
        x = gX;
        y = gY;
        a = angle;//get given arrow angle
        speed = 200;//arrow travels at approximately 200 pixels a second
        time = 1;//arrows will last for a single second before blining out of existence
        position = new Vector2();//Create new position vector
        imgT = new Texture("Arrow.png");//get the image of the arrow
        img = new Sprite(imgT);//make the Bow a sprite
        hitbox = new Rectangle(x, y, 50, 13); //create a hitbox surrounding the arrow
    }

    /**
     * Updates the position of the arrow with each render
     * @param delta The time changed between the previous render call and the current one
     */
    public void update(float delta) {
        hitbox.x += speed * (float)Math.cos(a) * delta;//update x hitbox
        hitbox.y += speed * (float)Math.sin(a) * delta;//update y hitbox
        time -= delta;//calculate the new amount of time remaining
    }
    
    /**
     * Checks to see if the arrow has been active for over a second
     * If yes, then return true
     * @return boolean indicating whether the arrow is ready to be erased
     */
    public boolean toErase(){
        if (time < 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Draws the arrow
     * @param batch
     */
    public void draw(SpriteBatch batch){
        batch.draw(this.imgT, hitbox.x, hitbox.y, 50, 13);
    }
    
    //Gets the Bow's x-position
    public float getX(){
        return x;
    }
    
    //Sets the Bow's x-position
    public void setX(float gX){
        x = gX;
    }
    
    //Gets the Bow's y-position
    public float getY(){
        return y;
    }
    
    //Sets the Bow's y-position
    public void setY(float gY){
        y = gY;
    }
    
    //Gets the dimensions of the arrow's hitbox
    public Rectangle getHitbox(){
        return hitbox;
    }
}
