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
 *  * 
 */
package pwm.participant;

/**
 *
 * @author mchippa
 */
public class PWMParticipantInfo {
    
    /**
     *
     */
    protected int initialWeight;

    /**
     *
     */
    protected int targetWeight;

    /**
     *
     */
    protected double age; // years 

    /**
     *
     */
    protected double height; // in meters

    /**
     *
     */
    protected String gender; 

    /**
     *
     */
    protected double initialPA;

    /**
     *
     * @param initialWeight
     * @param targetWeight
     * @param age
     * @param height
     * @param gender
     * @param initialPA
     */
    public PWMParticipantInfo(int initialWeight, int targetWeight, double age, double height, String gender, double initialPA) {
        this.initialWeight = initialWeight;
        this.targetWeight = targetWeight;
        this.age = age;
        this.height = height;
        this.gender = gender;
        this.initialPA = initialPA;
    }

    /**
     *
     * @return
     */
    public int getTargetWeight() {
        return targetWeight;
    }

    /**
     *
     * @return
     */
    public int getInitialWeight() {
        return initialWeight;
    }

    /**
     *
     * @return
     */
    public double getAge() {
        return age;
    }

    /**
     *
     * @return
     */
    public double getHeight() {
        return height;
    }

    /**
     *
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @return
     */
    public double getInitialPA() {
        return initialPA;
    }

    public void setCurrentWeight(double currentWeight) {
        this.initialWeight = (int)currentWeight;
    }
    
    
}
