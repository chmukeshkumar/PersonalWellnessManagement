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
import pwm.visualizer.PWMMDPView;
import pwm.visualizer.PWMMDPViewController;

/**
 *
 * @author mchippa
 */
public class PWMMDPModel {
    
    MDPPolicy policy;
    
    PWMMDPModel(){
        
    }
    
    public void runMDP(PWMParticipantInfo participantInfo, MDPModelParams modelParams) {
          
        PWMStateSpace pwmStateSpace  = new PWMStateSpace();
        Map stateSpace  = pwmStateSpace.generateStateSpace(participantInfo.getInitialWeight(), participantInfo.getTargetWeight());
//        
        PWMActionSpace pwmActionSpace = new  PWMActionSpace();
        Map actionSpace = pwmActionSpace.generateActionSet(participantInfo, 500,4000,100,0,1000,50);
        pwmActionSpace.setStateSpace(stateSpace);
//        
        RewardFunction rewardMapper = new PWMQuickLossRewardMapper(participantInfo);
        TerminalFunction terminalMapper = new PWMTerminalStateMapper(participantInfo);
//        
        performReachabilityAnalysis(pwmStateSpace, pwmActionSpace);
//        
        MDPSolver solver = new MDPSolver(stateSpace,actionSpace,rewardMapper,terminalMapper);
        policy = solver.runVI(50,0.0001,0.01);
//        
//        policy.print();
//        policy.printStateValues();
    }
    
    public MDPPolicy getPolicy() {
        return this.policy;
    }
    
    
    private void performReachabilityAnalysis(PWMStateSpace stateSpace, PWMActionSpace actionSpace ) { 
        Map<String,PWMState> states = stateSpace.getStateSpace();
        Map<String,PWMAction> actions = actionSpace.getActionSpace();
        
        Map<String,PWMState> statesCopy = new HashMap();
        statesCopy.putAll(states);
        
        int minStateValue = Integer.MAX_VALUE;
        int maxStateValue = Integer.MIN_VALUE;
        
        for(Map.Entry<String,PWMState> stateEntry : statesCopy.entrySet() ) {
            for(Map.Entry<String, PWMAction> actionEntry : actions.entrySet()) {
                PWMState s = stateEntry.getValue();
                PWMAction a = actionEntry.getValue();
                if(a.applicableInState(s)) {
                     int finalWeight = a.estimateActionConsequence(s.getValue());
                     ArrayList<Integer> transitions = new ArrayList();
                     transitions.add(finalWeight); 
                     transitions.add(finalWeight- 1);
                     transitions.add(finalWeight + 1 );
                     for(Integer tp : transitions ) {
                         int stateValue = tp.intValue();
                         if(stateValue < minStateValue ) {
                             minStateValue = stateValue;
                         }
                         if(stateValue > maxStateValue ) {
                             maxStateValue = stateValue;
                         }
                     }
                }
            } 
        }
        
        for(int s = minStateValue ; s <= maxStateValue ; s++ ) {
            String statename = ""+s;
            if(states.containsKey(statename) == false ) {
                states.put(statename, new PWMState(s));
            }
        }
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        PWMMDPView mdpView = new PWMMDPView();
        PWMMDPModel mdpModel = new PWMMDPModel();
        PWMMDPViewController viewController = new PWMMDPViewController(mdpModel, mdpView);
    }
}
