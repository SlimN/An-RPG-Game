/*
Steven Nim
January 18, 2017
Green Potions are Items that serve no real purpose...
They also smell bad.
 */

package com.rpg.game;

public class GreenPotion extends Item{
    
    //Primary constructor
    public GreenPotion(){
        super();//call Item superclass constructor
        name = "Green Potion";
        desc = "A sickly green potion that serves no purpose other than to make your bag smell like onions.";
        itemType = 3;//Green Potions are identified by the Item Type "3"
    }
    
    //Green Potion serve no purpose when used other than to display this wall of text
    public String use(){
        return "You open up the bottle and experience a waft of disgusting onion scent."
                + "\nYou close the bottle in fear of dying of suffocation.";
    }
}
