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
 */
package pwm.goalseeking;

import java.util.ArrayList;
import java.util.List;
import pwm.participant.PWMParticipantInfo;
import pwm.weightdynamics.ThreeCompartmentModel;

/**
 *
 * @author munna
 */
public class ReflectionMapper {
    
    ReflectionMapper() {
        
    }
    
    public Consequence estimateConsequence(PWMParticipantInfo participantInfo, GoalSeekingAction action) {
        
         ThreeCompartmentModel model = new ThreeCompartmentModel(participantInfo.getInitialWeight(),
                                                                participantInfo.getHeight(),
                                                                participantInfo.getAge(),
                                                                participantInfo.getGender(),
                                                                participantInfo.getInitialPA()
                                                               );
        model.setTimeStep(21);
        
        ArrayList<Double> setCalories = new ArrayList();
        ArrayList<Double> setPA       = new ArrayList();
        for(int i=0;i<model.getTimeStep();i++) {
            setCalories.add(action.nutritionCalories);
            setPA.add(action.exPAL);
        }
        model.setCurrentParameters(setCalories, setPA);
        
        double finalWeight =  model.getFinalWeight();
        
        Consequence c = new Consequence(finalWeight);
        return c;
    }
    
}
