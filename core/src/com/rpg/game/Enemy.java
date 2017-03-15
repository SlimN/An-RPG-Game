/*
Kyle Wilson
January 18, 2017
Enemies are hostile, non-controllable characters. They deal damage if made contact with.
 */

package com.rpg.game;

public class Enemy extends Character {
    private int dmg;//amount of damage an enemy deals
    
    //primary constructor
    public Enemy() {
        super();
        dmg = 0;
    }
    
    /**
     * secondary constructor
     * @param name
     * @param health
     * @param xPos
     * @param yPos
     * @param width
     * @param height
     * @param rotation
     * @param dmg 
     */
    public Enemy(String name, int health, int xPos, int yPos, int width, int height, float rotation, int dmg) {
        super(name, health, xPos, yPos, width, height, rotation);//calls similar constructor in Character class
        this.dmg = dmg;
    }
      
}
