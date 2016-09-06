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
public enum IntensityLevel {
    LOW(0),
    MEDIUM(1),
    HIGH(2);
    
    private final int value;
    private IntensityLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public int[] getCalorieBounds() {
        switch(value) {
            case 0:
                return new int[]{2900,5000};
            case 1:
                return new int[]{1500,2800};
            case 2:
                return new int[]{500,1400};
            default:
                break;
        }
        System.out.println("ERROR!!! Shouldnot reeach here un expected motivation Level in getCalorie Bounds " + value);
        return new int[]{0,0};
    }
    
    public double[] getPALBounds(){
         switch(value) {
            case 0:
                return new double[]{1.0,1.5};
            case 1:
                return new double[]{1.5,2.0};
            case 2:
                return new double[]{2.1,3.0};
            default:
                break;
        }
        System.out.println("ERROR!!! Shouldnot reeach here un expected motivation Level in getCalorie Bounds " + value);
        return new double[]{0,0};
    }
}
