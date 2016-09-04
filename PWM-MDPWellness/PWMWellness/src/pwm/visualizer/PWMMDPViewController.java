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
import pwm.mdp.MDPModelParams;
import pwm.mdp.PWMMDPModel;
import pwm.mdp.solver.MDPPolicy;
import pwm.participant.PWMParticipantInfo;


public class PWMMDPViewController {
    
    final PWMMDPModel mdpModel;
    final PWMMDPView  mdpView;

    public PWMMDPViewController(PWMMDPModel mdpModel, PWMMDPView mdpView) {
        this.mdpModel = mdpModel;
        this.mdpView  = mdpView;
        
        MDPViewListener listener = new MDPViewListener();
        mdpView.addActionListener(listener);
//        mdpView.addChangeListener(listener);
    }
    
    private void runMDPSimulation() {
        PWMParticipantInfo participantInfo = mdpView.getParticipantInfo();
        MDPModelParams     modelParams     = mdpView.getModelParams();    
        mdpModel.runMDP(participantInfo, modelParams);
        MDPPolicy policy = mdpModel.getPolicy();
        mdpView.updatePolicy(policy,participantInfo);
    }
    
    class MDPViewListener implements ActionListener, ChangeListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            runMDPSimulation();
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            runMDPSimulation();
        }
    }
}
