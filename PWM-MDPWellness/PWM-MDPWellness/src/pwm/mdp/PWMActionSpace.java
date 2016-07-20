/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.mdp;

import java.util.HashMap;
import java.util.Map;
import pwm.participant.PWMParticipantInfo;

/**
 *
 * @author mchippa
 */
class PWMActionSpace {
    
    Map<String, PWMAction> actionSpace = new HashMap<String, PWMAction>();
    
    Map generateActionSet(PWMParticipantInfo participant, double minNutritionCalories, double maxNutritionCalories, double nutritionCalorieStep,
                                            double minExerciseCalories, double maxExerciseCalories, double exerciseCalorieStep) {
         
        for(double calories = minNutritionCalories; calories <= maxNutritionCalories; calories+=nutritionCalorieStep) {
            for(double excalories = minExerciseCalories; excalories <= maxExerciseCalories; excalories+=exerciseCalorieStep) {
                int nutritionCalories = (int) calories;
                int exerciseCalories  = (int) excalories;
                String actionName = ""+nutritionCalories+"-"+exerciseCalories;
                PWMAction action = new PWMAction(participant, actionName, nutritionCalories, exerciseCalories);
                actionSpace.put(actionName, action);
            }
        }
        return actionSpace;
    }
    
    Map<String,PWMAction> getActionSpace() {
        return this.actionSpace;
    }
    
    public void setStateSpace(Map stateSpace) {
        for(Map.Entry<String, PWMAction> entry : actionSpace.entrySet() ) {
            entry.getValue().setStateSpace(stateSpace);
        }
    }
    
}
