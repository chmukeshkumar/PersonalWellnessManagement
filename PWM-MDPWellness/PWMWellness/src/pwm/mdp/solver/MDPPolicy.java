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
 *  *    
 *  * 
 */
package pwm.mdp.solver;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mchippa
 */
public class MDPPolicy {
    
    Map<String, StateActionTuple> stateActionTuples;
    
    MDPPolicy() {
        stateActionTuples = new HashMap();
    }
    
    public Map<String,StateActionTuple> getStateActionTuples() {
        return this.stateActionTuples;
    }
  
    
    void addStateActionTuple(StateActionTuple sat) {
        this.stateActionTuples.put(sat.state.getDescription(), sat);
    }
    
    /**
     *
     * @param s
     * @return
     */
    public Action getAction(State s ) {
        return stateActionTuples.get(s.getDescription()).action;
    }
    
    /**
     *
     */
    public void print() {
        System.out.println("=======================");
        for(Map.Entry<String,StateActionTuple> entry : stateActionTuples.entrySet() ) {
            State s = entry.getValue().state;
            Action a = entry.getValue().action;
            
            System.out.println(s.getDescription() + ":" + a.getName());
        }
        System.out.println("=======================");
    }

    /**
     *
     */
    public void printStateValues() {
        System.out.println("#######################");
        for(Map.Entry<String,StateActionTuple> entry : stateActionTuples.entrySet() ) {
            State s = entry.getValue().state;
            Map<Action,Double> actionValues = s.getActionValues();
            System.out.println("----------" + s.getDescription() + "----------------");
            for(Map.Entry<Action,Double> e : actionValues.entrySet()) {
                Action a = e.getKey();
                Double d = e.getValue();
                System.out.println(a.getName()+" = " + d.doubleValue());
            }
        }
        System.out.println("##############################");
    }
     
}
