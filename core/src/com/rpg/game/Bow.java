/*
Steven Nim
December 21, 2016
A bow is an weapon Item that fires an arrow projectile. The arrow damages enemies.
NOTE: The arrow handles collision with objects, NOT the bow. 
      The bow exists solely to appear in the inventory.
 */

package com.rpg.game;

public class Bow extends Item{
    private int quiver;//this is how many arrows there are left to hurt people with (UNUSED)
    
    //Primary constructor
    public Bow(){
        super();//call Item superclass constructor
        quiver = 0;//empty quiver haha
        name = "Bow";
        desc = "A common oak shortbow.";
        
        itemType = 0;//Bows are identified by the Item Type "0"
    }
    
    /**
     * Secondary constructor
     * @param q Given number of arrows to shoot things with
     */
    public Bow(int q){
        super();//call Item superclass constructor
        quiver = q;//get given number of arrows
    }
    
    //This is the flavour text that displays when you try to use the bow in the Inventory.
    public String use(){
        return "Don't use the bow here, use it against the enemies!";
    }
}
