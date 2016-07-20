/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.mdp.solver;

/**
 *
 * @author mchippa
 */
public class TransitionProbability {
    public State s; 
    
    public double p;
    
    public TransitionProbability(State s, double p) {
        this.s = s;
        this.p = p;
    }
}
