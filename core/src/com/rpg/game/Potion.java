/*
Steven Nim
December 16, 2016
A Health Potion is a one-time consumable item that restores the user's HP by 25.
But the HP restoring functionality is yet to be implemented.
 */

package com.rpg.game;

public class Potion extends Item{
    private int hp;//Amount of HP the user restores from consuming an HP Potion
    
    /**
     * Primary constructor
     */
    public Potion(){
        super();//call Item superclass constructor
        hp = 20;//The amount of HP a potion restores is 20HP
        name = "Health Potion";
        desc = "A refreshing red potion that restores 20HP to the user.";
        
        itemType = 1;//HP Potions are identified by the Item Type "1"
    }
    
    //User consumes the potion to restore health
    //Potion vanishes from existence shortly after
    public void consume(){
        //The user would normally restore health here.
        //But the feature could not be implemented in time.
        //Sorry!
        
        //Remove the potion from existence
        super.remove();
    }
    
    
}
