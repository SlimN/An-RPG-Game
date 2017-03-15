/*
Steven Nim
December 16, 2016
An Item is an object that can be manipulated in the Inventory. Some items are consumable.
 */

package com.rpg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item {
    //Every Item has a number associated with it. This is the item's "Item Type".
    //It is useful for sorting and searching for different types of Items in an Array/ArrayList.
    protected int itemType;
    protected String desc;//A description of the Item
    protected String name;//What kind of item is it?
    protected Texture imgT;//the image of the item
    protected Sprite img;//A Sprite version of the image
    
    
    //Primary constructor
    public Item(){
        name = "Item Unknown";
        desc = "No description available.";
    }
    
    /**
     * Secondary constructor
     * @param n given name of item
     * @param d given description of item
     */
    public Item(String n, String d){
        this();
        name = n;
        desc = d;
    }
    
    //Removes the item from existence
    public void remove(){
        //Code could not be implemented in time for end of Stage 3.
    }

    /**
     * Gets the name of the Item (basically, what kind of Item is it?)
     * @return the name of the item
     */
    public String getName(){
        return name;
    }
    
    /**
     * Gets the description of the Item
     * @return the description of the item
     */
    public String getDesc(){
        return desc;
    }
    
    /**
     * Gets the Item Type of an Item (the Item's identifier)
     * @return the Item Type identifier
     */
    public int getItemType(){
        return itemType;
    }
    
    /**
     * Checks to see if an Item is a Health Potion
     * Used for the purposes such as consuming the Health Potion.
     * @return boolean indicating whether you can actually drink the Health Potion
     */
    public boolean isPotion(int itemType){
        if (itemType == 1){//Return true if is HP Potion
            return true;
        }else{//Return false if NOT a HP Potion
            return false;
        }
    }
}
