/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.goalseeking;

import pwm.pomdp.Observation;

/**
 *
 * @author mchippa
 */
class MotivationDistribution {
    private double[] distribution;
    MotivationDistribution(int levels) {
        distribution = new double[levels];
        for(int i=0;i<levels;i++) {
            distribution[i] = 1.0/levels;
        }
    }
    
    MotivationDistribution() {
        distribution = new double[101];
        for(int i=0;i<distribution.length;i++) {
            distribution[i] = 1.0/distribution.length;
        }
    }
    
    private void updateFollowed() {
        for(int i=0;i<distribution.length;i++) {
            distribution[i] = distribution[i] * i/100.0;
        }
        normalizeDistribution();
    }
    
    private void updateNotFollowed() {
        for(int i=0;i<distribution.length;i++) {
            distribution[i] = distribution[i] * (1.0 - i/100.0);
        }
        normalizeDistribution();
    }
    
    private void normalizeDistribution() { 
        double sum = 0;
        for(int i =0;i<distribution.length;i++) {
            sum += distribution[i];
        }
        for(int i =0;i<distribution.length;i++) {
            distribution[i] = distribution[i] / sum;
        }
    }
    
    public void update(Observation observation ) {
        if(observation == Observation.ADHERED) {
            updateFollowed();
        }
        else if(observation == Observation.NOTADHERED ) {
            updateNotFollowed();
        }
        else {
            
        }
    }
    
    public double[] getDistribution() {
        return this.distribution;
    }

    void resetDistribution() {
        int levels = distribution.length;
        for(int i=0;i<levels;i++) {
            distribution[i] = 1.0/levels;
        }
    }
}
