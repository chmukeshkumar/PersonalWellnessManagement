/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pwm.participant.PWMParticipantInfo;
import pwm.pomdp.IntensityLevel;

/**
 *
 * @author cesl
 */
public class PWMPOMDPView extends JFrame {
    
    private POMDPBeliefDistributionView nutritionDistributionPanel;
    private POMDPBeliefDistributionView exerciseDistributionPanel;
    private ParticipantInfoPanel participantPanel;
    private POMDPModelControlPanel   modelControlPanel;
    private PWMSelectedActionPanel selectedActionPanel;
    
    public PWMPOMDPView() {
        
        nutritionDistributionPanel = new POMDPBeliefDistributionView("Nutrition Distribution");
        exerciseDistributionPanel = new POMDPBeliefDistributionView("Exercise Distribution");
        participantPanel = new ParticipantInfoPanel();
        modelControlPanel = new POMDPModelControlPanel();
        selectedActionPanel = new PWMSelectedActionPanel();
        
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(participantPanel);
        panel.add(selectedActionPanel);
        
        JPanel distributionPanel = new JPanel();
        distributionPanel.setLayout(new GridLayout(2, 1));
        distributionPanel.add(nutritionDistributionPanel);
        distributionPanel.add(exerciseDistributionPanel);
        
        this.setLayout(new BorderLayout());
        this.add(panel,BorderLayout.WEST);
        this.add(distributionPanel,BorderLayout.CENTER);
        this.add(modelControlPanel,BorderLayout.SOUTH);
        
        this.setPreferredSize(new Dimension(750,750));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    void addActionListener(ActionListener listener) {
        participantPanel.addActionListener(listener);
        modelControlPanel.addActionListener(listener);
    }
    
    PWMParticipantInfo getParticipantInfo() {
        PWMParticipantInfo participantInfo = participantPanel.getParticipantInfo();
        return participantInfo;
    }

    void updateDistributions(double[] nutritionDistribution, double[] exerciseDistribution) {
        nutritionDistributionPanel.update(nutritionDistribution);
        exerciseDistributionPanel.update(exerciseDistribution);
    }

    void setSelectedAction(int calories, IntensityLevel nutritionIntensityLevel, double pal, IntensityLevel exerciseIntensityLevel) {
        selectedActionPanel.setNutritionCalories(calories);
        selectedActionPanel.setNutritionIntensityLevel(nutritionIntensityLevel);
        selectedActionPanel.setExercisePAL(pal);
        selectedActionPanel.setExerciseIntensityLevel(exerciseIntensityLevel);
    }
}
