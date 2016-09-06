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
package pwm.visualizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import pwm.mdp.solver.Action;
import pwm.pomdp.IntensityLevel;
import pwm.pomdp.Observation;
import pwm.pomdp.PWMPOMDPModel;


public class PWMPOMDPViewController {
    
    private PWMPOMDPModel model;
    private PWMPOMDPView view;
    
    public PWMPOMDPViewController(PWMPOMDPModel pomdpModel, PWMPOMDPView view) {
        this.model = pomdpModel;
        this.view = view;
        
        POMDPActionListener listener = new POMDPActionListener();
        view.addActionListener(listener);
    }
    
    private void runSimulation(Observation nutritionObservation, Observation exerciseObservation) {
        model.updateDistributions(nutritionObservation, exerciseObservation);
        Action selectedAction = model.simulateOneStep(view.getParticipantInfo());
        int calories = Integer.valueOf(selectedAction.getName().split("-")[0]);
        double pal   = Double.valueOf(selectedAction.getName().split("-")[1]);
        IntensityLevel nutritionIntensityLevel = model.getSelectedNutritionIntensityLevel();
        IntensityLevel exerciseIntensityLevel  = model.getSelectedNutritionIntensityLevel();
        
        view.updateDistributions(model.getNutritionDistribution(), model.getExerciseDistribution());
        view.setSelectedAction(calories,nutritionIntensityLevel, pal, exerciseIntensityLevel);
    }
    
    class POMDPActionListener implements ActionListener, ChangeListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MyJButton clickedButton = (MyJButton)e.getSource();
            String name = clickedButton.getName();
            Observation newNutritionObservation = null;
            Observation newExerciseObservation = null;
            switch(name) {
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
            
            runSimulation(newNutritionObservation, newExerciseObservation);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            
        }
        
    }
    
}
