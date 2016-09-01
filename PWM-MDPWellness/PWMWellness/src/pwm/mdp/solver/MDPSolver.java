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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mchippa
 */
public class MDPSolver {
    
    private final Map<String, State> stateSpace;
    private final Map<String, Action> actionSpace;
    private final RewardFunction rewardFunction;
    private final TerminalFunction terminalFunction;
    
    private Map<State, Double> valueFunction;
    private Map<State, List<ActionTransitions> > transitionDynamics;
    
    private double gamma = 0;
    
    /**
     *
     * @param stateSpace
     * @param actionSpace
     * @param rewardFunction
     * @param terminalFunction
     */
    public MDPSolver(Map<String,State> stateSpace, Map<String,Action> actionSpace, RewardFunction rewardFunction, TerminalFunction terminalFunction) {
        this.stateSpace = stateSpace;
        this.actionSpace = actionSpace;
        this.rewardFunction = rewardFunction;
        this.terminalFunction = terminalFunction;
        
        valueFunction = new HashMap<State, Double> ();
        transitionDynamics = new HashMap<State, List<ActionTransitions> >();
    }

    /**
     *
     * @param maxIterations
     * @param maxDelta
     * @param gamma
     * @return
     */
    public MDPPolicy runVI(int maxIterations, double maxDelta, double gamma) {
        this.gamma = gamma;

        for(int i=0;i<=maxIterations;i++) {
            System.out.println("Iteration " + i);
            double delta = 0. ;
            for(Map.Entry<String,State> entry : stateSpace.entrySet() ) {
                State state = entry.getValue();
                double v = this.value(state);
                double maxQ = this.performBellmanUpdateOn(state);
                delta  = Math.max(Math.abs(maxQ-v), delta);
            }
            if(delta < maxDelta ) {
                break;
            }
        }
        MDPPolicy policy = new MDPPolicy();
        for(Map.Entry<String,State> entry : stateSpace.entrySet() ) {
            State s = entry.getValue();
            Map<Action,Double> actionValues = s.getActionValues();
            if(actionValues.size() != 0) {
                double maxValue = Collections.max(actionValues.values());
                for(Map.Entry<Action,Double> e : actionValues.entrySet()) {
                    if(e.getValue() == maxValue) {
                        StateActionTuple sat = new StateActionTuple(s,e.getKey());
                        policy.addStateActionTuple(sat);
                    }
                }
            }
        }
        return policy;
    }
    
    
    private double value(State s ) {
        if(this.terminalFunction.isTerminal(s)) {
            return 0;
        }
        Double value = valueFunction.get(s);
        double v;
        if(value == null ) {
            v = 0;
        } else {
            v = value.doubleValue();
        }
        
        return v;
    }
    
    
    private double performBellmanUpdateOn(State s) {
        if(this.terminalFunction.isTerminal(s) ) {
            valueFunction.put(s,0.);
            return 0.;
        }
        
        double maxQ = Double.NEGATIVE_INFINITY;
        List<ActionTransitions> transitions = this.getActionTransitions(s);
        for(ActionTransitions at : transitions ) { 
            double q = this.computeQ(s, at);
            s.addActionValue(at.action,q);
            if(q > maxQ ) {
                maxQ = q;
            }
        }
        valueFunction.put(s,maxQ);
        return maxQ;
    }
    
    
    private double computeQ(State s, ActionTransitions at) {
        double q = 0;
        for(TransitionProbability tp : at.transitions) {
            double vp = this.value(s);
            double discount = this.gamma;
            double r = this.rewardFunction.reward(s, at.action , tp.s);
            q += tp.p*(r + (discount * vp));
        }
        return q;
    }
    
    
    private List<ActionTransitions> getActionTransitions(State s) {
        List<ActionTransitions> allTransitions = transitionDynamics.get(s);
        if(allTransitions == null ) {
            ArrayList<ActionTransitions> transitions = new ArrayList();
            for(Map.Entry<String,Action> entry : actionSpace.entrySet() ) {
                Action a = entry.getValue();
                if(a.applicableInState(s) ) {
                    List<TransitionProbability> stateTransitions =  a.getTransitions(s);
                    transitions.add(new ActionTransitions(a, stateTransitions ));
                }
            }
            allTransitions = transitions;
            transitionDynamics.put(s, transitions);
        }
        return allTransitions;
    }
}
