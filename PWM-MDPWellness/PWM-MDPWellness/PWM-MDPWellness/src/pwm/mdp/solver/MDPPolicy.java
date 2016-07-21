/* 
 * Copyright (C) 2016 mchippa
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
