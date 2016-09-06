/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.pomdp;

/**
 *
 * @author cesl
 */
public enum Observation {
    ADHERED(0),
    NOTADHERED(1);
    
    private final int value;
    private Observation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
