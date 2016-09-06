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
class POMDPModelControlPanel extends JPanel {
    PWMModelControlPanel pwmModelControlPanel;
    
    MyJButton nutritionAdheredButton;
    MyJButton nutritionNotAdheredButton;
    MyJButton exerciseAdheredButton;
    MyJButton exerciseNotAdheredButton;
    
    POMDPModelControlPanel() {
        pwmModelControlPanel = new PWMModelControlPanel();
        nutritionAdheredButton = new MyJButton("Adhered");
        nutritionAdheredButton.setName("nut-adh");
        nutritionNotAdheredButton = new MyJButton("Not Adhered");
        nutritionNotAdheredButton.setName("nut-not-adh");
        JPanel nutritionAdheredPanel = new JPanel(new GridLayout(1,2));
        nutritionAdheredPanel.add(nutritionAdheredButton);
        nutritionAdheredPanel.add(nutritionNotAdheredButton);
        nutritionAdheredPanel.setBorder(new TitledBorder("Nutrition Adherence"));
        
        exerciseAdheredButton = new MyJButton("Adhered");
        exerciseAdheredButton.setName("ex-adh");
        exerciseNotAdheredButton = new MyJButton("Not Adhered");
        exerciseNotAdheredButton.setName("ex-not-adh");
        JPanel exerciseAdheredPanel = new JPanel(new GridLayout(1,2));
        exerciseAdheredPanel.add(exerciseAdheredButton);
        exerciseAdheredPanel.add(exerciseNotAdheredButton);
        exerciseAdheredPanel.setBorder(new TitledBorder("Exercise Adherence"));
        
        JPanel adherencePanel = new JPanel(new GridLayout(2,1));
        adherencePanel.add(nutritionAdheredPanel);
        adherencePanel.add(exerciseAdheredPanel);
        
        this.setLayout(new GridLayout(1,2));
        this.add(pwmModelControlPanel);
        this.add(adherencePanel);
    }
    
    void addActionListener(ActionListener listener) {
        nutritionAdheredButton.addActionListener(listener);
        nutritionNotAdheredButton.addActionListener(listener);
        exerciseAdheredButton.addActionListener(listener);
        exerciseNotAdheredButton.addActionListener(listener);
    }
    
    void setSliderPositions(int minCalories, int maxCalories, double minPA, double maxPA) {
        this.pwmModelControlPanel.setSliderPositions(minCalories, maxCalories, minPA, maxPA);
    }

    double[] getSliderPositions() {
        return this.pwmModelControlPanel.getSliderPositions();
    }
}
