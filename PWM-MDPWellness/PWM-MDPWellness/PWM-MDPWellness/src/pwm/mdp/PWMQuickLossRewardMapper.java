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
