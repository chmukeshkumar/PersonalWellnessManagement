/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.mdp;

import pwm.mdp.solver.State;
import pwm.mdp.solver.TerminalFunction;
import pwm.participant.PWMParticipantInfo;

/**
 *
 * @author mchippa
 */
public class PWMTerminalStateMapper implements TerminalFunction {
    private final PWMParticipantInfo participant;
    
    PWMTerminalStateMapper(PWMParticipantInfo participant) {
        this.participant = participant;
    }
    
    @Override
    public boolean isTerminal(State s) {
       if(s.getValue() < participant.getTargetWeight()) {
           return true;
       }
       return false;
    }
    
}
