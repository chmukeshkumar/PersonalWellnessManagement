/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import java.awt.GridLayout;
import java.util.Hashtable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;

/**
 *
 * @author cesl
 */
class ModelControlPanel extends JPanel{

    MyJSlider minCaloriesSlider;
    MyJSlider maxCaloriesSlider;
    MyJSlider minPASlider;
    MyJSlider maxPASlider;
    
    public ModelControlPanel() {
        super();
        this.setLayout(new GridLayout(2, 2));
        minCaloriesSlider = new MyJSlider(JSlider.HORIZONTAL,500,5000, 500);
        minCaloriesSlider.setMinorTickSpacing(250);
        maxCaloriesSlider = new MyJSlider(JSlider.HORIZONTAL,500,5000, 5000);
        maxCaloriesSlider.setMinorTickSpacing(250);
        
        minPASlider = new MyJSlider(JSlider.HORIZONTAL,10,30,10);
        minPASlider.setMinorTickSpacing(1);
        
        maxPASlider = new MyJSlider(JSlider.HORIZONTAL,10,30,30);
        maxPASlider.setMinorTickSpacing(1);
        
        Hashtable calorieTable = new Hashtable();
        calorieTable.put(new Integer(500),  new JLabel("500"));
        calorieTable.put(new Integer(2500), new JLabel("2500"));
        calorieTable.put(new Integer(5000), new JLabel("5000"));
        
        minCaloriesSlider.setLabelTable(calorieTable);
        maxCaloriesSlider.setLabelTable(calorieTable);
        
        Hashtable paTable = new Hashtable();
        paTable.put(new Integer(10), new JLabel("1.0"));
        paTable.put(new Integer(20), new JLabel("2.0"));
        paTable.put(new Integer(30), new JLabel("3.0"));
        minPASlider.setLabelTable(paTable);
        maxPASlider.setLabelTable(paTable);
        
        minCaloriesSlider.setLabelTable(calorieTable);
        maxCaloriesSlider.setLabelTable(calorieTable);
        
        minCaloriesSlider.setPaintTicks(true);
        minCaloriesSlider.setPaintLabels(true);
        maxCaloriesSlider.setPaintTicks(true);
        maxCaloriesSlider.setPaintLabels(true);
        minPASlider.setPaintTicks(true);
        minPASlider.setPaintLabels(true);
        maxPASlider.setPaintTicks(true);
        maxPASlider.setPaintLabels(true);
        
        minCaloriesSlider.setSnapToTicks(true);
        maxCaloriesSlider.setSnapToTicks(true);
        minPASlider.setSnapToTicks(true);
        minPASlider.setSnapToTicks(true);
        
        TitledBorder minCalorieBorder = new TitledBorder("Minimum Calories");
        minCaloriesSlider.setBorder(minCalorieBorder);
        maxCaloriesSlider.setBorder(new TitledBorder("Maximum Calories"));
        minPASlider.setBorder(new TitledBorder("Minimum Physcial Activity Level"));
        maxPASlider.setBorder(new TitledBorder("Maximum Physical Activity Level"));
        this.add(minCaloriesSlider);
        this.add(maxCaloriesSlider);
        this.add(minPASlider);
        this.add(maxPASlider);
    }
    
    public void addChangeListener(ChangeListener listener) {
        this.minCaloriesSlider.addChangeListener(listener);
        this.maxCaloriesSlider.addChangeListener(listener);
        this.minPASlider.addChangeListener(listener);
        this.maxPASlider.addChangeListener(listener);
    }
    
    
    
}
