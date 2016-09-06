/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.goalseeking;

import java.util.ArrayList;
import java.util.List;
import pwm.mdp.MDPModelParams;
import pwm.mdp.PWMAction;
import pwm.participant.PWMParticipantInfo;

/**
 *
 * @author cesl
 */
public class GoalSeekingActionSpace extends MDPModelParams {
    
    private List<GoalSeekingAction> actionSet; 
    
    public GoalSeekingActionSpace(int minCalories, int maxCalories, double minPALevel, double maxPALevel) {
        super(minCalories, maxCalories, minPALevel, maxPALevel);
    }

    List<GoalSeekingAction> generateActionSet(PWMParticipantInfo participant, int minCalories, int maxCalories, int nutStepSize, double minPALevel, double maxPALevel, double exStepSize) {
        actionSet = new ArrayList();
        for(double calories = minCalories; calories <= maxCalories; calories+=nutStepSize) {
            for(double exPal = minPALevel; exPal <= maxPALevel; exPal+=exStepSize) {
                int nutritionCalories = (int) calories;
                String actionName = ""+nutritionCalories+"-"+exPal;
                GoalSeekingAction action = new GoalSeekingAction(participant, actionName, nutritionCalories, exPal);
                actionSet.add(action);
            }
        }
        return actionSet;
    }
    
}
