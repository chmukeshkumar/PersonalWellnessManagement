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
