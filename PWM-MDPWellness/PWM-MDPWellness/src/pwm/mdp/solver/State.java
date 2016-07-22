/*
 * /*
 *  * Copyright (c) 2006-2011 The University of Akron.
 *  * All rights reserved.
 *  *
 *  * Permission to use and copy this software and its documentation for educational
 *  * purposes only, without fee, and without written agreement is hereby granted,
 *  * provided that the above copyright notice, the following two paragraphs, and
 *  * acknowledgment of the authors appear in all copies of this software.
 *  *
 *  * IN NO EVENT SHALL THE UNIVERSITY OF AKRON BE LIABLE TO ANY PARTY FOR DIRECT,
 *  * INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE
 *  * USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF AKRON
 *  * HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *
 *  * THE UNIVERSITY OF AKRON SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 *  * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  * A PARTICULAR PURPOSE. THE SOFTWARE PROVIDED HEREUNDER IS ON AN "AS IS" BASIS,
 *  * AND THE UNIVERSITY OF AKRON HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT,
 *  * UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 *  * 
 *  * Contributing Authors: 
 *  *    Mukesh Kumar Chippa
 *  *    Shivakumar Sastry
 *  *    10, September, 2011
 *  * 
 */
package pwm.mdp.solver;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mchippa
 */
public class State {
    
    private String description;
    private int weight;
    
    Map<Action,Double> actionValues;
    
    /**
     *
     * @param weight
     */
    public State(int weight) {
        actionValues = new HashMap();
        this.weight = weight;
        this.description = ""+weight;
    }
    
    /**
     *
     * @return
     */
    public int getValue() { 
        return this.weight;
    }
    
    /**
     *
     * @return
     */
    public String getDescription() {
        return this.description;
    }
    
    void addActionValue(Action a, double value) {
        this.actionValues.put(a, value);
    }
    
    /**
     *
     * @return
     */
    public Map<Action,Double> getActionValues() {
        return this.actionValues;
    }
        
    /**
     *
     */
    public void printValues() {
        System.out.println("State : " + this.description);
        for(Map.Entry<Action,Double> entry : actionValues.entrySet() ) {
            Action a = entry.getKey();
            Double d = entry.getValue();
            
            System.out.print(a.getName()+":"+d.doubleValue()+",");
        }
    }
}
