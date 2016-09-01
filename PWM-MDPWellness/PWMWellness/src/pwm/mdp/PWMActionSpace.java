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
