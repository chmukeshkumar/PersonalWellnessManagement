/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pwm.mdp.solver.MDPPolicy;
import pwm.participant.PWMParticipantInfo;

/**
 *
 * @author munna
 */
public class PWMMDPView extends JFrame{
    
    ParticipantInfoPanel participantInfoPanel;
    MDPPolicyPanel mdpPolicyPanel;
    
    public PWMMDPView() {
        this.setTitle("Personal Wellness Management - MDP Formulation");
        this.participantInfoPanel = new ParticipantInfoPanel();
        this.mdpPolicyPanel = new MDPPolicyPanel();
        
        this.setLayout(new BorderLayout());
        this.add(this.participantInfoPanel,BorderLayout.WEST);
        this.add(this.mdpPolicyPanel,BorderLayout.CENTER);
        
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    void addActionListener(ActionListener mdpActionListener) {
        participantInfoPanel.addActionListener(mdpActionListener);
    }

    PWMParticipantInfo getParticipantInfo() {
        PWMParticipantInfo participantInfo = participantInfoPanel.getParticipantInfo();
        return participantInfo;
    }

    void updatePolicy(MDPPolicy policy) {
        mdpPolicyPanel.updateData(policy);
    }
    
}
