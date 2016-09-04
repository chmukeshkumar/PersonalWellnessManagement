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

public class PWMAction extends Action {

    /**
     *
     */
    public double nutritionCalories;

    /**
     *
     */
    public double exPAL;
    
    private Map stateSpace;
    private PWMParticipantInfo participantInfo;
    static int time_step = 21; // days
    
    /**
     *
     * @param participant
     * @param name
     * @param nutritionCalories
     * @param exPAL
     */
    public PWMAction(PWMParticipantInfo participant, String name, double nutritionCalories, double exPAL)       { 
        super(name);
        this.participantInfo = participant;
        this.nutritionCalories = nutritionCalories;
        this.exPAL  = exPAL;
    }
    
    /**
     *
     * @param info
     * @return
     */
    public double getPAL(PWMParticipantInfo info) { 
        return this.exPAL;
    }
    
    /**
     *
     * @param currentWeight
     * @return
     */
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
            setPA.add(this.exPAL);
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

    /**
     *
     * @param stateSpace
     */
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