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
package pwm.pomdp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import pwm.mdp.MDPModelParams;
import pwm.mdp.PWMAction;
import pwm.mdp.PWMMDPModel;
import pwm.mdp.solver.Action;
import pwm.participant.PWMParticipantInfo;
import pwm.visualizer.PWMPOMDPView;
import pwm.visualizer.PWMPOMDPViewController;

/**
 *
 * @author munna
 */
public class PWMPOMDPModel {
    
    ArrayList<PolicyVector> policyVector_nutrition = new ArrayList();
    ArrayList<PolicyVector> policyVector_exercise = new ArrayList();
    
    
    private PomdpParams nutritionParams = new PomdpParams(5,3,2);
    private PomdpParams exerciseParams  = new PomdpParams(5,3,2);
    
    private IntensityLevel selectedNutritionIntensityLevel; 
    private IntensityLevel selectedExerciseIntensityLevel;
    
  
    PWMPOMDPModel(String policyFileNutrition, String policyFileExercise) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(policyFileNutrition));
        String line;
        while((line = reader.readLine()) != null ) {
            int actionNumber = Integer.valueOf(line);
            String v  = reader.readLine();
            String[] tokens = v.split(" ");
            ArrayList<Double> vector = new ArrayList();
            for(int i=0;i<tokens.length;i++) {
                double value = Double.valueOf(tokens[i]);
                vector.add(value);
            }
            PolicyVector pv = new PolicyVector(IntensityLevel.values()[actionNumber], vector);
            policyVector_nutrition.add(pv);
            reader.readLine();
        }
        
        reader = new BufferedReader(new FileReader(policyFileExercise));
        while((line = reader.readLine()) != null ) {
            int actionNumber = Integer.valueOf(line);
            String v  = reader.readLine();
            String[] tokens = v.split(" ");
            ArrayList<Double> vector = new ArrayList();
            for(int i=0;i<tokens.length;i++) {
                double value = Double.valueOf(tokens[i]);
                vector.add(value);
            }
            PolicyVector pv = new PolicyVector(IntensityLevel.values()[actionNumber], vector);
            policyVector_exercise.add(pv);
            reader.readLine();
        }
        
        
        nutritionParams.setInitialDistribution(new double[]{0.2,0.2,0.2,0.2,0.2});
        exerciseParams.setInitialDistribution(new double[]{0.2,0.2,0.2,0.2,0.2});
        
        nutritionParams.addTransitionProbability(0, 0, new double[] {0.75,0.25,0.0,0.0,0.0} );
        nutritionParams.addTransitionProbability(1, 0, new double[] {0.25,0.5,0.25,0.0,0.0} );
        nutritionParams.addTransitionProbability(2, 0, new double[] {0.0,0.1,0.8,0.1,0.0} );
        nutritionParams.addTransitionProbability(3, 0, new double[] {0.0,0.0,0.1,0.8,0.1} );
        nutritionParams.addTransitionProbability(4, 0, new double[] {0.0,0.0,0.0,0.1,0.9} );
        
        nutritionParams.addTransitionProbability(0, 1, new double[] {0.9,0.1,0.0,0.0,0.0} );
        nutritionParams.addTransitionProbability(1, 1, new double[] {0.3,0.4,0.3,0.0,0.0} );
        nutritionParams.addTransitionProbability(2, 1, new double[] {0.0,0.1,0.4,0.5,0.0} );
        nutritionParams.addTransitionProbability(3, 1, new double[] {0.0,0.0,0.1,0.3,0.6} );
        nutritionParams.addTransitionProbability(4, 1, new double[] {0.0,0.0,0.1,0.4,0.5} );
        
        nutritionParams.addTransitionProbability(0, 2, new double[] {1.0,0.0,0.0,0.0,0.0} );
        nutritionParams.addTransitionProbability(1, 2, new double[] {1.0,0.0,0.0,0.0,0.0} );
        nutritionParams.addTransitionProbability(2, 2, new double[] {1.0,0.0,0.0,0.0,0.0} );
        nutritionParams.addTransitionProbability(3, 2, new double[] {0.0,0.0,0.2,0.4,0.4} );
        nutritionParams.addTransitionProbability(4, 2, new double[] {0.0,0.0,0.0,0.2,0.8} );
        
        exerciseParams.addTransitionProbability(0, 0, new double[] {0.75,0.25,0.0,0.0,0.0} );
        exerciseParams.addTransitionProbability(1, 0, new double[] {0.25,0.5,0.25,0.0,0.0} );
        exerciseParams.addTransitionProbability(2, 0, new double[] {0.0,0.1,0.8,0.1,0.0} );
        exerciseParams.addTransitionProbability(3, 0, new double[] {0.0,0.0,0.1,0.8,0.1} );
        exerciseParams.addTransitionProbability(4, 0, new double[] {0.0,0.0,0.0,0.1,0.9} );
        
        exerciseParams.addTransitionProbability(0, 1, new double[] {0.9,0.1,0.0,0.0,0.0} );
        exerciseParams.addTransitionProbability(1, 1, new double[] {0.3,0.4,0.3,0.0,0.0} );
        exerciseParams.addTransitionProbability(2, 1, new double[] {0.0,0.1,0.4,0.5,0.0} );
        exerciseParams.addTransitionProbability(3, 1, new double[] {0.0,0.0,0.1,0.3,0.6} );
        exerciseParams.addTransitionProbability(4, 1, new double[] {0.0,0.0,0.1,0.4,0.5} );
       
        exerciseParams.addTransitionProbability(0, 2, new double[] {1.0,0.0,0.0,0.0,0.0} );
        exerciseParams.addTransitionProbability(1, 2, new double[] {1.0,0.0,0.0,0.0,0.0} );
        exerciseParams.addTransitionProbability(2, 2, new double[] {1.0,0.0,0.0,0.0,0.0} );
        exerciseParams.addTransitionProbability(3, 2, new double[] {0.0,0.0,0.2,0.4,0.4} );
        exerciseParams.addTransitionProbability(4, 2, new double[] {0.0,0.0,0.0,0.2,0.8} );
        
        nutritionParams.addObservationProbability(0,0,new double[] {0.1,0.9} );
        nutritionParams.addObservationProbability(1,0,new double[] {0.8,0.2} );
        nutritionParams.addObservationProbability(2,0,new double[] {1.0,0.0} );
        nutritionParams.addObservationProbability(3,0,new double[] {1.0,0.0} );
        nutritionParams.addObservationProbability(4,0,new double[] {1.0,0.0} );
        
        nutritionParams.addObservationProbability(0,1,new double[] {0.1,0.9} );
        nutritionParams.addObservationProbability(1,1,new double[] {0.3,0.7} );
        nutritionParams.addObservationProbability(2,1,new double[] {0.5,0.5} );
        nutritionParams.addObservationProbability(3,1,new double[] {1.0,0.0} );
        nutritionParams.addObservationProbability(4,1,new double[] {1.0,0.0} );
        
        nutritionParams.addObservationProbability(0,2,new double[] {0.0,1.0} );
        nutritionParams.addObservationProbability(1,2,new double[] {0.0,1.0} );
        nutritionParams.addObservationProbability(2,2,new double[] {0.0,1.0} );
        nutritionParams.addObservationProbability(3,2,new double[] {0.6,0.4} );
        nutritionParams.addObservationProbability(4,2,new double[] {0.8,0.2} );
        
        exerciseParams.addObservationProbability(0,0,new double[] {0.1,0.9} );
        exerciseParams.addObservationProbability(1,0,new double[] {0.8,0.2} );
        exerciseParams.addObservationProbability(2,0,new double[] {1.0,0.0} );
        exerciseParams.addObservationProbability(3,0,new double[] {1.0,0.0} );
        exerciseParams.addObservationProbability(4,0,new double[] {1.0,0.0} );
        
        exerciseParams.addObservationProbability(0,1,new double[] {0.1,0.9} );
        exerciseParams.addObservationProbability(1,1,new double[] {0.3,0.7} );
        exerciseParams.addObservationProbability(2,1,new double[] {0.5,0.5} );
        exerciseParams.addObservationProbability(3,1,new double[] {1.0,0.0} );
        exerciseParams.addObservationProbability(4,1,new double[] {1.0,0.0} );
        
        exerciseParams.addObservationProbability(0,2,new double[] {0.0,1.0} );
        exerciseParams.addObservationProbability(1,2,new double[] {0.0,1.0} );
        exerciseParams.addObservationProbability(2,2,new double[] {0.0,1.0} );
        exerciseParams.addObservationProbability(3,2,new double[] {0.6,0.4} );
        exerciseParams.addObservationProbability(4,2,new double[] {0.8,0.2} );
    }
    
    void printBeliefDistribution() {
        System.out.println("-------Belief Distribution--------------");
        double nutritionBelief[] = nutritionParams.getBeliefDistribution();
        double exerciseBelief[]  = exerciseParams.getBeliefDistribution();
        
        for (int i=0;i<nutritionBelief.length;i++) {
            System.out.print(" " + nutritionBelief[i]);
        }
        System.out.println("");
        for (int i=0;i<exerciseBelief.length;i++) {
            System.out.print(" " + exerciseBelief[i]);
        }
        System.out.println("\n---------------------------------------");
    }
    
    IntensityLevel getActionIntensity(double[] beliefDistribution, ArrayList<PolicyVector> policyVectors ) {
        double maxValue = Double.MIN_VALUE;
        IntensityLevel intensityLevel = null;
        for (PolicyVector pv : policyVectors ) {
            double value = pv.getBeliefValue(beliefDistribution);
            if(value > maxValue ) {
                maxValue = value;
                intensityLevel = pv.getActionIntensity();
            }
        }
        return intensityLevel;
    }
    
    
    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String policyFile_nutrition = args[0];
        String policyFile_exercise  = args[1];
        PWMPOMDPModel model = new PWMPOMDPModel(policyFile_nutrition, policyFile_exercise);
        PWMPOMDPView view = new PWMPOMDPView();
        PWMPOMDPViewController controller = new PWMPOMDPViewController(model, view);
    }

    public Action simulateOneStep(PWMParticipantInfo participant) {
        selectedNutritionIntensityLevel = getActionIntensity(nutritionParams.getBeliefDistribution(),policyVector_nutrition);
        selectedExerciseIntensityLevel  = getActionIntensity(exerciseParams.getBeliefDistribution(),policyVector_exercise);
        
        int nutrition[] = selectedNutritionIntensityLevel.getCalorieBounds();
        double pal[]       = selectedExerciseIntensityLevel.getPALBounds();
        
//        System.out.println("Selected IntensityLevel Nutrition " + selectedNutritionIntensityLevel + " calorie Bounds " + nutrition[0] + " " + nutrition[1]
//                +" Exercise " + selectedExerciseIntensityLevel + " pal bounds " + pal[0] + " " + pal[1]);
        MDPModelParams mdpModelParams = new MDPModelParams(nutrition[0],nutrition[1],pal[0],pal[1]);
        Action selectedAction = getAction(participant, mdpModelParams);
        System.out.println("Selected Action " + selectedAction.getName());
        return selectedAction;
    }
    
    public void updateDistributions(Observation nutritionObservation, Observation exerciseObservation) {
        if(selectedNutritionIntensityLevel != null  && nutritionObservation != null) {
            nutritionParams.updateDistribution(selectedNutritionIntensityLevel, nutritionObservation);
        }
        if(selectedExerciseIntensityLevel != null && exerciseObservation != null) {
            exerciseParams.updateDistribution(selectedExerciseIntensityLevel, exerciseObservation);
        }
    }
    
    public double[] getNutritionDistribution() {
        return this.nutritionParams.beliefDistribution;
    }
    
    public double[] getExerciseDistribution() {
        return this.exerciseParams.beliefDistribution;
    }

    public IntensityLevel getSelectedNutritionIntensityLevel () {
        return this.selectedNutritionIntensityLevel;
    }
    
    public IntensityLevel getSelectedExerciseIntensityLevel () {
        return this.selectedNutritionIntensityLevel;
    }
    
    public Action getAction(PWMParticipantInfo participant, MDPModelParams modelParams ) {
        PWMMDPModel mdpModel = new PWMMDPModel();
        mdpModel.runMDP(participant, modelParams);
        Action action = mdpModel.getAction(participant.getInitialWeight());
        return action;
    }
    
    
    
    
}
