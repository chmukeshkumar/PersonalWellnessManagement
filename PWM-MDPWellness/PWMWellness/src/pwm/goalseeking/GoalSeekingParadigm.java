/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.goalseeking;

import java.util.List;
import java.util.Map;
import pwm.mdp.PWMAction;
import pwm.mdp.PWMActionSpace;
import pwm.mdp.solver.Action;
import pwm.participant.PWMParticipantInfo;
import pwm.pomdp.Observation;
import pwm.visualizer.GoalSeekingView;
import pwm.visualizer.GoalSeekingViewController;

/**
 *
 * @author cesl
 */
public class GoalSeekingParadigm {
    
    Action bestActionSelected = null;
    MotivationDistribution nutritionMotivationDistribution;
    MotivationDistribution exerciseMotivationDistribution;
    
    public GoalSeekingParadigm() {
        this.nutritionMotivationDistribution = new MotivationDistribution();
        this.exerciseMotivationDistribution  = new MotivationDistribution();
    }
    
    public Action getBestAction(GoalSeekingActionSpace actionSpace, PWMParticipantInfo participant) {
        
        List<GoalSeekingAction> actionset  = actionSpace.generateActionSet(participant, actionSpace.getMinCalories(), 
                                                                                                actionSpace.getMaxCalories(), 
                                                                                                250, 
                                                                                                actionSpace.getMinPALevel(), 
                                                                                                actionSpace.getMaxPALevel(),
                                                                                                0.1);
        double maxReward = Double.NEGATIVE_INFINITY;
        GoalSeekingAction maxRewardAction = null;
        
        ReflectionMapper reflectionMapper = new ReflectionMapper();
        RewardMapper    rewardMapper      = new RewardMapper();
        
        for(GoalSeekingAction action : actionset) {
            Consequence c  = reflectionMapper.estimateConsequence(participant, action);
            double reward = rewardMapper.getReward(participant, action, c);
            System.out.println(""+action.getName() + " " + c.getValue() + " " + reward);
            if(reward > maxReward) {
                maxReward = reward;
                maxRewardAction = action;
            }
        }
        this.bestActionSelected = maxRewardAction;
        return maxRewardAction;
    }
    
    public boolean isWithInTolerance(PWMParticipantInfo participantInfo, int newWeight) {
        int oldWeight = participantInfo.getInitialWeight();
        if(Math.abs(oldWeight - newWeight) <= 1 ) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public void updateDistributions(Observation nutritionObservation, Observation exerciseObservation) {
        if(nutritionObservation != null ) {
            nutritionMotivationDistribution.update(nutritionObservation);
        }
        if(exerciseObservation != null ) {
            exerciseMotivationDistribution.update(exerciseObservation);
        }
    }
    
    public double[] getNutritionDistribution() {
        return nutritionMotivationDistribution.getDistribution();
    }
    
    public double[] getExerciseDistribution() {
        return exerciseMotivationDistribution.getDistribution();
    }
    
    
    public static void main(String[] args) {
        GoalSeekingParadigm model = new GoalSeekingParadigm();
        GoalSeekingView view      = new GoalSeekingView();
        GoalSeekingViewController viewController = new GoalSeekingViewController(model,view);
    }

    public Action getLastSelectedBestAction() {
        return this.bestActionSelected;
    }

    

    
    
}
