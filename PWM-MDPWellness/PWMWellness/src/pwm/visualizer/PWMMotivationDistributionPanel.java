/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author cesl
 */
class PWMMotivationDistributionPanel extends JPanel {
    private MotivationDistributionPanel nutritionDistributionPanel;
    private MotivationDistributionPanel exerciseDistributionPanel;
    
    PWMMotivationDistributionPanel() {
        this.nutritionDistributionPanel = new MotivationDistributionPanel("Nutrition Distribution");
        this.exerciseDistributionPanel = new MotivationDistributionPanel("Exercise Distribution");
        
        this.setLayout(new GridLayout(2,1));
        this.add(nutritionDistributionPanel);
        this.add(exerciseDistributionPanel);
    }
    
    public void updateDistribution(double[] nutritionDistribution, double[] exerciseDistribution) {
        this.nutritionDistributionPanel.updateDistribution(nutritionDistribution);
        this.exerciseDistributionPanel.updateDistribution(exerciseDistribution);
    }
}
