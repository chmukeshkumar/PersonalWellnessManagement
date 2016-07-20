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
public class ActionTransitions {
    
    Action action;
    List<TransitionProbability> transitions;

    protected ActionTransitions(Action action) {
        this.action = action;
    }

    protected ActionTransitions(Action action, List<TransitionProbability> transitions) {
        this.action = action;
        this.transitions = transitions;
    }
    
    protected void addTransition(TransitionProbability tp ) {
        this.transitions.add(tp);
    }
    
}
