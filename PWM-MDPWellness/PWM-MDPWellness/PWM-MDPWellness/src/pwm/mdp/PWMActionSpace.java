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
