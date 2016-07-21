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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pwm.mdp.solver.MDPPolicy;
import pwm.mdp.solver.MDPSolver;
import pwm.mdp.solver.RewardFunction;
import pwm.mdp.solver.State;
import pwm.mdp.solver.TerminalFunction;
import pwm.mdp.solver.TransitionProbability;
import pwm.participant.PWMParticipantInfo;

/**
 *
 * @author mchippa
 */
public class PWMMDPWellness {

    PWMMDPWellness() { 
        PWMParticipantInfo participant  = new PWMParticipantInfo(150, 80, 28, 1.5, "Male", 1.2);
        PWMStateSpace pwmStateSpace  = new PWMStateSpace();
        Map stateSpace  = pwmStateSpace.generateStateSpace(participant.getInitialWeight(), participant.getTargetWeight(), 1);
        
        PWMActionSpace pwmActionSpace = new  PWMActionSpace();
        Map actionSpace = pwmActionSpace.generateActionSet(participant, 500,4000,100,0,1000,50);
        pwmActionSpace.setStateSpace(stateSpace);
        
        RewardFunction rewardMapper = new PWMQuickLossRewardMapper(participant);
        TerminalFunction terminalMapper = new PWMTerminalStateMapper(participant);
        
//        performReachabilityAnalysis(pwmStateSpace, pwmActionSpace);
        
        MDPSolver solver = new MDPSolver(stateSpace,actionSpace,rewardMapper,terminalMapper);
        MDPPolicy policy = solver.runVI(50,0.0001,0.01);
        
        policy.print();
        policy.printStateValues();
    }
    
    
//    private void performReachabilityAnalysis(PWMStateSpace stateSpace, PWMActionSpace actionSpace ) { 
//        Map<String,PWMState> states = stateSpace.getStateSpace();
//        Map<String,PWMAction> actions = actionSpace.getActionSpace();
//        
//        Map<String,PWMState> statesCopy = new HashMap();
//        statesCopy.putAll(states);
//        
//        int minStateValue = Integer.MAX_VALUE;
//        int maxStateValue = Integer.MIN_VALUE;
//        
//        for(Map.Entry<String,PWMState> stateEntry : statesCopy.entrySet() ) {
//            for(Map.Entry<String, PWMAction> actionEntry : actions.entrySet()) {
//                PWMState s = stateEntry.getValue();
//                PWMAction a = actionEntry.getValue();
//                if(a.applicableInState(s)) {
//                     int finalWeight = a.estimateActionConsequence(s.getValue());
//                     ArrayList<Integer> transitions = new ArrayList();
//                     transitions.add(finalWeight); 
//                     transitions.add(finalWeight- 1);
//                     transitions.add(finalWeight + 1 );
//                     for(Integer tp : transitions ) {
//                         int stateValue = tp.intValue();
//                         if(stateValue < minStateValue ) {
//                             minStateValue = stateValue;
//                         }
//                         if(stateValue > maxStateValue ) {
//                             maxStateValue = stateValue;
//                         }
//                     }
//                }
//            } 
//        }
//        
//        for(int s = minStateValue ; s <= maxStateValue ; s++ ) {
//            String statename = ""+s;
//            if(states.containsKey(statename) == false ) {
//                states.put(statename, new PWMState(s));
//            }
//        }
//        
//    }
    
    /**
     *
     * @param args
     */
        
    public static void main(String[] args) {
        new PWMMDPWellness();
    }
    
}
