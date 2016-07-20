/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.mdp;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mchippa
 */
public class PWMStateSpace {
    Map<String, PWMState> stateSpace = new HashMap<String,PWMState> ();
    
    Map generateStateSpace(int initialWeight, int finalWeight, int stepSize) {
        for (int w = finalWeight; w<= initialWeight ; w+=stepSize ) {
            stateSpace.put(""+w, new PWMState(w));
        }
        return stateSpace;
    }
    
    Map<String,PWMState> getStateSpace() {
        return this.stateSpace;
    }
    
    
}
