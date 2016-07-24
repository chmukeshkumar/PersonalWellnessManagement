/*
 * /*
 *  * Copyright (c) 2006-2011 The University of Akron.
 *  * All rights reserved.
 *  *
 *  * Permission to use and copy this software and its documentation for educational
 *  * purposes only, without fee, and without written agreement is hereby granted,
 *  * provided that the above copyright notice, the following two paragraphs, and
 *  * acknowledgment of the authors appear in all copies of this software.
 *  *
 *  * IN NO EVENT SHALL THE UNIVERSITY OF AKRON BE LIABLE TO ANY PARTY FOR DIRECT,
 *  * INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE
 *  * USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF AKRON
 *  * HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  *
 *  * THE UNIVERSITY OF AKRON SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 *  * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  * A PARTICULAR PURPOSE. THE SOFTWARE PROVIDED HEREUNDER IS ON AN "AS IS" BASIS,
 *  * AND THE UNIVERSITY OF AKRON HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT,
 *  * UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 *  * 
 *  * Contributing Authors: 
 *  *    Mukesh Kumar Chippa
 *  *    Shivakumar Sastry
 *  * 
 */
package pwm.pomdp;

import java.util.ArrayList;

/**
 *
 * @author munna
 */
public class PolicyVector {
    
    int actionNumber;
    ArrayList<Double> vector = new ArrayList();
    
    PolicyVector(int actionNumber, ArrayList<Double> vector) {
        this.actionNumber = actionNumber;
        this.vector.addAll(vector);
    }
    
    /**
     *
     * @return
     */
    public int getActionNumber() {
        return this.actionNumber;
    }
    
    /**
     *
     * @param beliefState
     * @return
     */
    public double getBeliefValue(double[] beliefState ) {
        double sum = 0;
        if(beliefState.length != vector.size() ) {
            System.err.println("Belief vector size and vector sizes not equal !!! belief vector size "  + beliefState.length + " value vector size " + vector.size() );
        }
            
        for(int i=0;i<beliefState.length;i++ ) {
            sum += vector.get(i) * beliefState[i];
        }
        return sum;
    }
    
}
