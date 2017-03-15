/*
Steven Nim
January 18, 2017
A Doohickey is an Item that can be adjusted/tweeked through the Use option in the Inventory. 
New messages are displayed every other time the Doohickey is used.
 */
package com.rpg.game;

import javax.swing.JOptionPane;

public class Doohickey extends Item {
    private int tweek;//Counts number of adjustments to Doohickey (a.k.a Power Level)

    //Primary constructor
    public Doohickey() {
        super();//call Item superclass constructor
        name = "Doohickey";
        desc = "An unknown contraption that can be improved to be even more useless.";
        
        itemType = 2;//Doohickeys are identified by the Item Type "2"
    }

    /**
     * Secondary constructor
     * @param t Predetermined power level of the Doohickey
     */
    public Doohickey(int t) {
        this();//Call primary constructor
        tweek = t;//Set Doohickey Power Level
    }

    //The user can "improve" the Doohickey and raise its "level"
    public void use() {
        tweek += 1;//Doohickey powers up
        
        if (tweek <= 2) {//Prior to 2 Tweeks, the Doohickey is just a scrap of junk
            JOptionPane.showMessageDialog(null, "You adjusted a gear on the Doohickey and slightly improved it."
                    + "\nNumber of adjustments: " + tweek, "Doohickey Phase 1/7", JOptionPane.INFORMATION_MESSAGE);
            
        } else if (tweek > 2 && tweek <= 4) {//Between 2 & 4 Tweeks, the Doohickey gains sentience
            desc = "An unknown contraption that is actually sentient.";
            JOptionPane.showMessageDialog(null, "The Doohickey made a sound. It was a happy sound."
                    + "\nDoohickey Happiness Level: " + tweek, "Doohickey Phase 2/7", JOptionPane.INFORMATION_MESSAGE);
            
        } else if (tweek > 4 && tweek <= 6) {//Between 4 & 6 Tweeks, the Doohickey becomes evil
            JOptionPane.showMessageDialog(null, "The Doohickey whispered a part of its plan to you."
                    + "\nDoohickey Evilness Level: " + tweek, "Doohickey Phase 3/7", JOptionPane.INFORMATION_MESSAGE);
            
        } else if (tweek > 6 && tweek <= 8) {//Between 6 & 8 Tweeks, the Doohickey is helping you enact world domination
            desc = "An sentient contraption that craves power.";
            JOptionPane.showMessageDialog(null, "You and the Doohickey are currently enacting world domination."
                    + "\nNumber of Superpowers Enslaved: " + tweek, "Doohickey Phase 4/7", JOptionPane.INFORMATION_MESSAGE);
            
        } else if (tweek > 8 && tweek <= 10) {//Between 8 & 10 Tweeks, the Doohickey is trying to kill you
            desc = "An sentient contraption mad with power.";
            JOptionPane.showMessageDialog(null, "You have both taken over the world but the Doohickey wants to betray you!"
                    + "\nNumber of Doohickey Assassination Attempts Avoided: " + tweek,
                     "Doohickey Phase 5/7", JOptionPane.INFORMATION_MESSAGE);
            
        } else if (tweek > 10 && tweek <= 11) {//Between 10 & 11 Tweeks, you are killing the Doohickey slowly
            desc = "An sentient contraption destined for death.";
            JOptionPane.showMessageDialog(null, "You have injected a rusting agent into the Doohickey. Its days are numbered."
                    + "\nPercentage of Doohickey Rusted: " + tweek, "Doohickey Phase 6/7", JOptionPane.INFORMATION_MESSAGE);
            
        } else if (tweek > 11) {//After 11 Tweeks, the Doohickey is dead and no more new messages will apear when it is tweeked
            desc = "The rusting husk of your sentient contraption buddy.";
            JOptionPane.showMessageDialog(null, "The Doohickey has stopped functioning from all of its rust. You are alone."
                    + "\nDays Since Doohickey's Last Functioning Moment: " + tweek,
                    "Doohickey Phase 7/7", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
