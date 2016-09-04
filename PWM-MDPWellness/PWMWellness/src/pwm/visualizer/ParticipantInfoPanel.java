/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import pwm.participant.PWMParticipantInfo;

/**
 *
 * @author munna
 */
class ParticipantInfoPanel extends JPanel {
    
    MyJTextField currentWeight;
    MyJTextField height_feet;
    MyJTextField height_inches;
    MyJTextField age;
    MyJTextField gender;
    MyJTextField targetWeight;
    MyJTextField currentPAL;
    
    MyJButton simulateButton;

    
    public ParticipantInfoPanel() {
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets=new Insets(5, 5, 5, 5);
        c.gridwidth = 5;c.gridx =0; c.gridy=0;
        this.add(new MyJLabel("Participant Info"),c);
        
        c.gridwidth = 1;
        c.gridy++; c.gridx = 0;
        this.add(new MyJLabel("Current Weight"),c);
        c.gridx++;
        this.currentWeight = new MyJTextField(5,"120");
        this.add(currentWeight,c);
        c.gridx++;
        this.add(new MyJLabel("Pounds"),c);
        
        c.gridy++;c.gridx = 0;
        this.add(new MyJLabel("Height"),c);
        c.gridx++;
        this.height_feet = new MyJTextField(5,"5");
        this.add(height_feet,c);
        c.gridx++;
        this.add(new MyJLabel("Feet"),c);
        c.gridx++;
        this.height_inches = new MyJTextField(5,"10");
        this.add(height_inches,c);
        c.gridx++;
        this.add(new MyJLabel("Inches"),c);
        
        c.gridy++; c.gridx = 0;
        this.add(new MyJLabel("Age"),c);
        c.gridx++;
        this.age = new MyJTextField(5,"29");
        this.add(age,c);
        c.gridx++;
        this.add(new MyJLabel("Years"),c);
        
        c.gridy++; c.gridx = 0;
        this.add(new MyJLabel("Gender"),c);
        c.gridx++;
        this.gender = new MyJTextField(5,"Male");
        this.add(gender,c);
        
        c.gridy++; c.gridx = 0;
        this.add(new MyJLabel("Current PAL"),c);
        c.gridx++;
        this.currentPAL = new MyJTextField(5,"1.2");
        this.add(this.currentPAL,c);
        
        c.gridy++; c.gridx = 0;
        this.add(new MyJLabel("Target Weight"),c);
        c.gridx++;
        this.targetWeight = new MyJTextField(5,"75");
        this.add(targetWeight,c);
        c.gridx++;
        this.add(new MyJLabel("Pounds"),c);
        
        c.gridy++; c.gridx = 0;
        this.simulateButton = new MyJButton("Simulate");
        this.add(simulateButton,c);
    }
    
    public void addActionListener(ActionListener listener) {
        this.simulateButton.addActionListener(listener);
    }

    PWMParticipantInfo getParticipantInfo() {
       try {
           double currentWeight = Double.valueOf(this.currentWeight.getText());
           double height_feet   = Double.valueOf(this.height_feet.getText());
           double height_inches = Double.valueOf(this.height_inches.getText());
           double age           = Double.valueOf(this.age.getText());
           String gender        = this.gender.getText();
           double currentPAl    = Double.valueOf(this.currentPAL.getText());
           double targetWeight  = Double.valueOf(this.targetWeight.getText());
           
           double height = (height_feet*12 + height_inches)*0.0254;
           
           PWMParticipantInfo participant = new PWMParticipantInfo((int)currentWeight, (int)targetWeight, age, height, gender, currentPAl);
           return participant;
           
       } catch(Exception e) {
           System.out.print(e);
       }
       
       return null;
    }
    
}
