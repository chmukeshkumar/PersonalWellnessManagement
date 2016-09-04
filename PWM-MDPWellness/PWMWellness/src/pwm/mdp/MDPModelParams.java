/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.mdp;

/**
 *
 * @author munna
 */
public class MDPModelParams {
    private int minCalories;
    private int maxCalories;
    private double minPALevel;
    private double maxPALevel;

    public int getMinCalories() {
        return minCalories;
    }

    public int getMaxCalories() {
        return maxCalories;
    }

    public double getMinPALevel() {
        return minPALevel;
    }

    public double getMaxPALevel() {
        return maxPALevel;
    }

    public MDPModelParams(int minCalories, int maxCalories, double minPALevel, double maxPALevel) {
        this.minCalories = minCalories;
        this.maxCalories = maxCalories;
        this.minPALevel = minPALevel;
        this.maxPALevel = maxPALevel;
    }
    
    
}
