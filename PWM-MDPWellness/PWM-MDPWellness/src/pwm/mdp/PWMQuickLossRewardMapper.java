/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.mdp;

import pwm.mdp.solver.Action;
import pwm.mdp.solver.RewardFunction;
import pwm.mdp.solver.State;
import pwm.participant.PWMParticipantInfo;

/**
 *
 * @author mchippa
 */
class PWMQuickLossRewardMapper implements RewardFunction {

    PWMParticipantInfo participant; 
    PWMQuickLossRewardMapper(PWMParticipantInfo participant) {
        this.participant = participant;
    }

    @Override
    public double reward(State s, Action a, State sprime) {
        int newWeight = sprime.getValue();
        int oldWeight = s.getValue();
        
        double difference = oldWeight - newWeight;
        
        double reward = 0;
        
        if(difference < 0 ) { // ended up actually gaining weight 
            reward = 0;
        }
        else {
            reward = 10 + (90/(participant.getTargetWeight() - participant.getInitialWeight() ))*(newWeight - participant.getInitialWeight());
        }
        
        return reward;
    }
    
}
