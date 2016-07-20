/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.mdp.solver;

import java.util.ArrayList;
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
    
    public MDPSolver(Map<String,State> stateSpace, Map<String,Action> actionSpace, RewardFunction rewardFunction, TerminalFunction terminalFunction) {
        this.stateSpace = stateSpace;
        this.actionSpace = actionSpace;
        this.rewardFunction = rewardFunction;
        this.terminalFunction = terminalFunction;
        
        valueFunction = new HashMap<State, Double> ();
        transitionDynamics = new HashMap<State, List<ActionTransitions> >();
    }

    public void runVI(int maxIterations, double maxDelta, double gamma) {
        this.gamma = gamma;
//        performReachabilityAnalysis();
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
    }
    
    
//    private void performReachabilityAnalysis() { 
//        Map <String,State
//        for(Map.Entry<String,State> stateEntry : stateSpace.entrySet() ) {
//            for(Map.Entry<String,Action> actionEntry : actionSpace.entrySet() ) {
//                if()
//            }
//        }
//    }
    
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
