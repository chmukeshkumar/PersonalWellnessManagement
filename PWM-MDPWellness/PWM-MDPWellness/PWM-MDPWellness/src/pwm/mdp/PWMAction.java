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
import java.util.List;
import java.util.Map;
import pwm.mdp.solver.Action;
import pwm.mdp.solver.State;
import pwm.mdp.solver.TransitionProbability;
import pwm.participant.PWMParticipantInfo;
import pwm.weightdynamics.ThreeCompartmentModel;

/**
 *
 * @author mchippa
 */

class PWMAction extends Action {
    public double nutritionCalories;
    public double exerciseCalories;
    
    private Map stateSpace;
    private PWMParticipantInfo participantInfo;
    static int time_step = 21; // days
    
    PWMAction(PWMParticipantInfo participant, String name, double nutritionCalories, double exerciseCalories) { 
        super(name);
        this.participantInfo = participant;
        this.nutritionCalories = nutritionCalories;
        this.exerciseCalories  = exerciseCalories;
    }
    
    public double getPAL(PWMParticipantInfo info) { 
        return exerciseCalories/info.getInitialWeight();
    }
    
    protected int estimateActionConsequence(int currentWeight) {
        ThreeCompartmentModel model = new ThreeCompartmentModel(currentWeight,
                                                                participantInfo.getHeight(),
                                                                participantInfo.getAge(),
                                                                participantInfo.getGender(),
                                                                participantInfo.getInitialPA()
                                                               );
        model.setTimeStep(time_step);
        
        ArrayList<Double> setCalories = new ArrayList();
        ArrayList<Double> setPA       = new ArrayList();
        for(int i=0;i<model.getTimeStep();i++) {
            setCalories.add(nutritionCalories);
            setPA.add(getPAL(participantInfo));
        }
        model.setCurrentParameters(setCalories, setPA);
        
        return model.getFinalWeight();
    }

    @Override
    public List<TransitionProbability> getTransitions(State s) {
       int currentWeight = Integer.valueOf(s.getDescription());
       int finalWeight1   = estimateActionConsequence(currentWeight);
       State newState1 = getState(finalWeight1);
       TransitionProbability tp1 = new TransitionProbability(newState1,0.8);
       int finalWeight2 = finalWeight1 - 1;
       State newState2  = getState(finalWeight2);
       TransitionProbability tp2 = new TransitionProbability(newState2, 0.1);
       int finalWeight3 = finalWeight1 + 1;
       State newState3  = getState(finalWeight3);
       TransitionProbability tp3 = new TransitionProbability(newState3, 0.1);
       
       ArrayList<TransitionProbability> transitions = new ArrayList();
       transitions.add(tp1);
       transitions.add(tp2);
       transitions.add(tp3);
       
       return transitions;
    }
    
    State getState(int weight) {
        if(stateSpace == null ) {
            throw new NullPointerException("State Space for action " + this.getName() + " is not set");
        }
        if(weight < participantInfo.getTargetWeight() ) {
            weight = participantInfo.getTargetWeight();
        }
        if(weight > participantInfo.getInitialWeight() ) {
            weight = participantInfo.getInitialWeight();
        }
        return (State) stateSpace.get(""+weight);
     }

    
    protected void setStateSpace(Map stateSpace) {
        this.stateSpace = stateSpace;
    }

    @Override
    public boolean isApplicableInState(State s) {
        int statevalue = s.getValue();
        if(statevalue > participantInfo.getInitialWeight() ) {
            return false;
        }
        if(statevalue < participantInfo.getTargetWeight() ) {
            return false;
        } 
        return true;
    }
    
    
}
