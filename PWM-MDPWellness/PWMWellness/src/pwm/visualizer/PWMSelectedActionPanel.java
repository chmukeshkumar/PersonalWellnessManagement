/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import pwm.pomdp.IntensityLevel;

/**
 *
 * @author cesl
 */
public class PWMSelectedActionPanel extends JPanel {
    
    MyJLabel nutritionIntensityLevel;
    MyJLabel exerciseIntensityLevel;
    
    MyJLabel nutritionCalories;
    MyJLabel exercisePAL;
    
    PWMSelectedActionPanel() {
        this.nutritionIntensityLevel = new MyJLabel("None");
        nutritionIntensityLevel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        this.exerciseIntensityLevel  = new MyJLabel("None");
        exerciseIntensityLevel.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
        this.nutritionCalories = new MyJLabel("None");
        nutritionCalories.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
        this.exercisePAL = new MyJLabel("None");
        exercisePAL.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
        
        JPanel nutritionPanel = new JPanel(new GridLayout(2,1));
        nutritionPanel.add(nutritionIntensityLevel);
        nutritionPanel.add(nutritionCalories);
        nutritionPanel.setBorder(new TitledBorder("Nutrition"));
        
        JPanel exercisePanel = new JPanel(new GridLayout(2,1));
        exercisePanel.add(exerciseIntensityLevel);
        exercisePanel.add(exercisePAL);
        exercisePanel.setBorder(new TitledBorder("Exercise"));
        
        this.setLayout(new GridLayout(2,1));
        this.add(nutritionPanel);
        this.add(exercisePanel);
        
        this.revalidate();
    }
    
    public void setNutritionIntensityLevel(IntensityLevel level) {
        switch(level) {
            case LOW:
                nutritionIntensityLevel.setText("LOW Intensity");
                break;
            case MEDIUM:
                nutritionIntensityLevel.setText("MEDIUM Intensity");
                break;
            case HIGH:
                nutritionIntensityLevel.setText("HIGH Intensity");
        }
    }
    
    public void setExerciseIntensityLevel(IntensityLevel level) {
        switch(level) {
            case LOW:
                exerciseIntensityLevel.setText("LOW Intensity");
                break;
            case MEDIUM:
                exerciseIntensityLevel.setText("MEDIUM Intensity");
                break;
            case HIGH:
                exerciseIntensityLevel.setText("HIGH Intensity");
        }
    }
    
    public void setNutritionCalories(int calories) {
        nutritionCalories.setText(calories+" Cal/day");
    }
    
    public void setExercisePAL(double pal) {
        int p = (int)((double)pal * 10);
        double pp = p/10.0;
        exercisePAL.setText(pp+"");
    }
}
