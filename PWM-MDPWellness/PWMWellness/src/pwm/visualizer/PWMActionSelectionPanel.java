/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author cesl
 */
class PWMActionSelectionPanel extends JPanel {
    
    MyJButton lowLow;
    MyJButton lowMed;
    MyJButton lowHigh;
    MyJButton medLow;
    MyJButton medMed;
    MyJButton medHigh;
    MyJButton highLow;
    MyJButton highMed;
    MyJButton highHigh;
    
    MyJButton selectedButton;
    
    private ArrayList<MyJButton> allButtons;
    
    PWMActionSelectionPanel() {
        lowLow = new MyJButton("L-L");
        lowMed = new MyJButton("L-M");
        lowHigh = new MyJButton("L-H");
        medLow = new MyJButton("M-L");
        medMed = new MyJButton("M-M");
        medHigh = new MyJButton("M-H");
        highLow = new MyJButton("H-L");
        highMed = new MyJButton("H-M");
        highHigh = new MyJButton("H-H");
        
        allButtons = new ArrayList();
        allButtons.add(highLow);
        allButtons.add(highMed);
        allButtons.add(highHigh);
        allButtons.add(medLow);
        allButtons.add(medMed);
        allButtons.add(medHigh);
        allButtons.add(lowLow);
        allButtons.add(lowMed);
        allButtons.add(lowHigh);
        
        
        this.setLayout(new GridLayout(3,3));
        this.add(highLow);
        this.add(highMed);
        this.add(highHigh);
        this.add(medLow);
        this.add(medMed);
        this.add(medHigh);
        this.add(lowLow);
        this.add(lowMed);
        this.add(lowHigh);
    }
    
    public void addActionListener(ActionListener listener) {
        this.lowLow.addActionListener(listener);
        this.lowMed.addActionListener(listener);
        this.lowHigh.addActionListener(listener);
        this.medLow.addActionListener(listener);
        this.medMed.addActionListener(listener);
        this.medHigh.addActionListener(listener);
        this.highLow.addActionListener(listener);
        this.highMed.addActionListener(listener);
        this.highHigh.addActionListener(listener);
    }

    void setSelectedButton(MyJButton selectedButton) {
        for(MyJButton button : allButtons) {
            if(selectedButton == button) {
                button.setBackground(Color.GRAY);
            }
            else {
                button.setBackground(new MyJButton("").getBackground());
            }
        }
    }

    boolean isActionSelectedButton(MyJButton source) {
        for(MyJButton button : allButtons) {
            if(source == button) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
}
