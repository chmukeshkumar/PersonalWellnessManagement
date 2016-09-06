/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import pwm.goalseeking.GoalSeekingActionSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pwm.goalseeking.GoalSeekingParadigm;
import pwm.mdp.solver.Action;
import pwm.participant.PWMParticipantInfo;
import pwm.pomdp.IntensityLevel;
import pwm.pomdp.Observation;

/**
 *
 * @author cesl
 */
public class GoalSeekingViewController {
    
    private GoalSeekingParadigm model;
    private GoalSeekingView view;
    
    MyJButton selectedButton;
            
    public GoalSeekingViewController(GoalSeekingParadigm model, GoalSeekingView view) {
        this.model = model;
        this.view = view;
        
        ActionListener listener = new GoalSeekingViewListener();
        view.addActionListener(listener);
    }
    
    void setSliderPositions(MyJButton selectedButton) {
        String name = selectedButton.getText();
        IntensityLevel nutritionIntensity = getIntensityLevel(name.split("-")[0]);
        IntensityLevel exerciseIntensity  = getIntensityLevel(name.split("-")[1]);
        
        int calorieBounds[]  = nutritionIntensity.getCalorieBounds();
        double palBounds[]   = exerciseIntensity.getPALBounds();
        
        view.setSliderPositions(calorieBounds[0], calorieBounds[1], palBounds[0], palBounds[1]);
    }
    
    IntensityLevel getIntensityLevel(String s) {
        switch(s) {
            case "L":
                return IntensityLevel.LOW;
            case "M":
                return IntensityLevel.MEDIUM;
            case "H":
                return IntensityLevel.HIGH;
            default:
                return null;
        }
    }
    
    Action getNewAction() {
        GoalSeekingActionSpace actionSpace = view.getActionSpace();
        PWMParticipantInfo participantInfo = view.getParticipantInfo();
        double currentWeight = view.getCurrentWeight();
        participantInfo.setCurrentWeight(currentWeight);

        Action bestAction = model.getBestAction(actionSpace, participantInfo);
        return bestAction;
    }
    
    void setBestAction(Action action) {
         int calories = Integer.valueOf(action.getName().split("-")[0]);
        double pal  = Double.valueOf(action.getName().split("-")[1]);
        view.setSelectedAction(calories,pal);
        view.resetActionSelection();
    }
    
    class GoalSeekingViewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() instanceof MyJButton ) {
                MyJButton source = (MyJButton)e.getSource();
                if(view.isActionSelectedButton(source)) {
                    selectedButton = source;
                    view.setSelectedButton(selectedButton);
                    setSliderPositions(selectedButton);
                }
                else {
                    if(source.getText().contains("Action") ) {
                        Action bestAction = getNewAction();
                        setBestAction(bestAction);
                    }
                    
                    if(source.getText().contains("Adhered")) {
                        String sourceName = source.getName();
                        Observation newNutritionObservation = null;
                        Observation newExerciseObservation = null;
                        switch(sourceName) {
                            case "nut-adh":
                                newNutritionObservation = Observation.ADHERED;
                                break;
                            case "nut-not-adh":
                                newNutritionObservation = Observation.NOTADHERED;
                                break;
                            case "ex-adh":
                                newExerciseObservation = Observation.ADHERED;
                                break;
                            case "ex-not-adh":
                                newExerciseObservation = Observation.NOTADHERED;
                                break;
                            default:
                                break;
                        }
                        model.updateDistributions(newNutritionObservation, newExerciseObservation);
                        view.updateDistributions(model.getNutritionDistribution(),model.getExerciseDistribution());
                        
                    }
                    
                }
            }
            if(e.getSource() instanceof MyJTextField) {
                MyJTextField tf = (MyJTextField)e.getSource();
                int newWeight = Integer.valueOf(tf.getText());
                PWMParticipantInfo participantInfo = view.getParticipantInfo();
                boolean withInTolerance = model.isWithInTolerance(participantInfo, newWeight); 
                Action action;
                if(withInTolerance) {
                    action = model.getLastSelectedBestAction();
                }else {
                    action = getNewAction();
                }
                setBestAction(action);
                view.setParticipantWeight(newWeight);
            }
            
        }
        
       
    }
}
