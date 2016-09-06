/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import pwm.goalseeking.GoalSeekingActionSpace;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pwm.participant.PWMParticipantInfo;

/**
 *
 * @author cesl
 */
public class GoalSeekingView extends JFrame {
    
    private ParticipantInfoPanel participantInfoPanel;
    private GoalSeekingModelControlPanel  modelControlPanel;
    private PWMActionSelectionPanel actionSelectionPanel;
    private PWMSelectedActionPanel selectedActionPanel;
    private PWMMotivationDistributionPanel motivationDistributionPanel;
    
    public GoalSeekingView()
    {
        participantInfoPanel = new ParticipantInfoPanel();
        participantInfoPanel.simulateButton.setText("Get Action");
        
        actionSelectionPanel = new PWMActionSelectionPanel();
        selectedActionPanel  = new PWMSelectedActionPanel();
        motivationDistributionPanel = new PWMMotivationDistributionPanel();
        modelControlPanel    = new GoalSeekingModelControlPanel();
        
        JPanel panel = new JPanel(new GridLayout(3,1));
        panel.add(participantInfoPanel);
        panel.add(actionSelectionPanel);
        panel.add(selectedActionPanel);
        
        this.setLayout(new BorderLayout());
        this.add(panel,BorderLayout.WEST);
        this.add(motivationDistributionPanel, BorderLayout.CENTER);
        this.add(modelControlPanel,BorderLayout.SOUTH);
        
        this.pack();
        this.setPreferredSize(new Dimension(750,700));
        this.setVisible(true);
    }

    void addActionListener(ActionListener listener) {
        this.participantInfoPanel.addActionListener(listener);
        this.actionSelectionPanel.addActionListener(listener);
        this.modelControlPanel.addActionListener(listener);
    }

    void setSelectedButton(MyJButton selectedButton) {
        actionSelectionPanel.setSelectedButton(selectedButton);
    }

    boolean isActionSelectedButton(MyJButton source) {
        return this.actionSelectionPanel.isActionSelectedButton(source);
    }
    
     void setSliderPositions(int minCalories, int maxCalories, double minPA, double maxPA) {
        this.modelControlPanel.setSliderPositions(minCalories, maxCalories, minPA, maxPA);
    }

    GoalSeekingActionSpace getActionSpace() {
        double sliderPositions[] = this.modelControlPanel.getSliderPositions();
        GoalSeekingActionSpace actionSpace = new GoalSeekingActionSpace((int)sliderPositions[0],
                                                                        (int)sliderPositions[1],
                                                                        sliderPositions[2]/10.0,
                                                                        sliderPositions[3]/10.0
        );
        return actionSpace;
    }
    
    public PWMParticipantInfo getParticipantInfo() {
        return participantInfoPanel.getParticipantInfo();
    }

    void setSelectedAction(int calories, double pal) {
        
        this.selectedActionPanel.setNutritionCalories(calories);
        this.selectedActionPanel.setExercisePAL(pal);
    }

    double getCurrentWeight() {
        return this.modelControlPanel.getCurrentWeight();
    }

    void resetActionSelection() {
        this.actionSelectionPanel.setSelectedButton(null);
    }

    void setParticipantWeight(int newWeight) {
        this.participantInfoPanel.setInitialWeight(newWeight);
    }

    void updateDistributions(double[] nutritionDistribution, double[] exerciseDistribution) {
       motivationDistributionPanel.updateDistribution(nutritionDistribution, exerciseDistribution);
    }

    
}
