/*
Kyle Wilson
January 18, 2017
NPC's are non-hostile, non-controllable characters.
 */

package com.rpg.game;

public class NPC extends Character {
    private String talk;//what an npc says when talked to
    
    //primary constructor
    public NPC() {
        super();
        talk = "";
    }
    
    /**
     * secondary constructor
     * @param name
     * @param health
     * @param xPos
     * @param yPos
     * @param width
     * @param length
     * @param rotation
     * @param talk 
     */
    public NPC(String name, int health, int xPos, int yPos, int width, int length, float rotation, String talk) {
        super(name, health, xPos, yPos, width, length, rotation);//call similar superclass constructor in character class
        this.talk = talk;
    }
}
