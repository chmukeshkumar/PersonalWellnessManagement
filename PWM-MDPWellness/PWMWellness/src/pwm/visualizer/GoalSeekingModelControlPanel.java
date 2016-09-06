/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author cesl
 */
public class GoalSeekingModelControlPanel extends JPanel {
    
    POMDPModelControlPanel pomdpModelControlPanel;
    MyJTextField newWeightTextField;
    
    GoalSeekingModelControlPanel() {
        pomdpModelControlPanel = new POMDPModelControlPanel();
        newWeightTextField = new MyJTextField(5,"120");
        
        JPanel newPanel = new JPanel(new GridLayout(2,1));
        MyJLabel label = new MyJLabel("New Weight");
        newPanel.add(label);
        newPanel.add(newWeightTextField);
        
        this.setLayout(new GridLayout(1,2));
        this.add(pomdpModelControlPanel);
        this.add(newPanel);
    }
    
    void addActionListener(ActionListener listener) {
        pomdpModelControlPanel.addActionListener(listener);
        newWeightTextField.addActionListener(listener);
    }
    
    void setSliderPositions(int minCalories, int maxCalories, double minPA, double maxPA) {
        this.pomdpModelControlPanel.setSliderPositions(minCalories, maxCalories, minPA, maxPA);
    }

    double[] getSliderPositions() {
        return this.pomdpModelControlPanel.getSliderPositions();
    }

    double getCurrentWeight() {
        return Double.valueOf(this.newWeightTextField.getText());
    }
    
    
    
}
