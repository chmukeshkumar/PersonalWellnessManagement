/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.mdp.solver;

import java.util.List;

/**
 *
 * @author mchippa
 */
public abstract class Action {
    protected String name; 
    
    public Action(String name) {
        this.name = name;
    }
    
    public String getName() { 
        return this.name;
    }
    
    public final boolean applicableInState(State s) {
        return true;
    }
    
    public abstract List<TransitionProbability> getTransitions(State s);
    
    public boolean isApplicableInState(State s) {
        return true;
    }
    
}
