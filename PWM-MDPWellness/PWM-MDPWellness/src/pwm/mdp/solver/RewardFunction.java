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
public interface RewardFunction {
    public double reward(State s, Action a, State sprime);
}
