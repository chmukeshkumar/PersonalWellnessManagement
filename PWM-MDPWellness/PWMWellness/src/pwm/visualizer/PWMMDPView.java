/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.event.ChangeListener;
import pwm.mdp.MDPModelParams;
import pwm.mdp.solver.MDPPolicy;
import pwm.participant.PWMParticipantInfo;

/**
 *
 * @author munna
 */
public class PWMMDPView extends JFrame{
    
    ParticipantInfoPanel participantInfoPanel;
    MDPPolicyPanel mdpPolicyPanel;
    PWMModelControlPanel controlPanel;
    
    public PWMMDPView() {
        this.setTitle("Personal Wellness Management - MDP Formulation");
        this.participantInfoPanel = new ParticipantInfoPanel();
        this.mdpPolicyPanel = new MDPPolicyPanel();
        this.controlPanel   = new PWMModelControlPanel();
        
        this.setLayout(new BorderLayout());
        this.add(this.participantInfoPanel,BorderLayout.WEST);
        this.add(this.mdpPolicyPanel,BorderLayout.CENTER);
        this.add(this.controlPanel,BorderLayout.SOUTH);
        
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    void addActionListener(ActionListener mdpActionListener) {
        participantInfoPanel.addActionListener(mdpActionListener);
    }
     void addChangeListener(ChangeListener listener) {
        controlPanel.addChangeListener(listener);
    }
    
    
    void addModelParamsChangeListener(ChangeListener listener) {
        controlPanel.addChangeListener(listener);
    }

    PWMParticipantInfo getParticipantInfo() {
        PWMParticipantInfo participantInfo = participantInfoPanel.getParticipantInfo();
        return participantInfo;
    }

    void updatePolicy(MDPPolicy policy, PWMParticipantInfo participant) {
        mdpPolicyPanel.updateData(policy,participant);
    }

    MDPModelParams getModelParams() {
        int minCalories = controlPanel.minCaloriesSlider.getValue();
        int maxCalories = controlPanel.maxCaloriesSlider.getValue();
        int minPA       = controlPanel.minPASlider.getValue();
        int maxPA       = controlPanel.maxPASlider.getValue();
        
        MDPModelParams modelParams = new MDPModelParams(minCalories, maxCalories, minPA/10.0, maxPA/10.0);
        
        return modelParams;
    }

   
}
