/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import javax.swing.JTextField;

/**
 *
 * @author munna
 */
class MyJTextField extends JTextField {

    public MyJTextField(int columns,String defaultText) {
        super(columns);
        if(defaultText != null ) {
            this.setText(defaultText);
        }
        
    }
    
}
