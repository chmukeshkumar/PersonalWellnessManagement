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
