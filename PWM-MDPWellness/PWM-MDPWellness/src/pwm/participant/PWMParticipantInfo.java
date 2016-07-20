/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.participant;

/**
 *
 * @author mchippa
 */
public class PWMParticipantInfo {
    
    protected int initialWeight;
    protected int targetWeight;
    protected double age; // years 
    protected double height; // in meters
    protected String gender; 
    protected double initialPA;

    public PWMParticipantInfo(int initialWeight, int targetWeight, double age, double height, String gender, double initialPA) {
        this.initialWeight = initialWeight;
        this.targetWeight = targetWeight;
        this.age = age;
        this.height = height;
        this.gender = gender;
        this.initialPA = initialPA;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    public int getInitialWeight() {
        return initialWeight;
    }

    public double getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }

    public double getInitialPA() {
        return initialPA;
    }
    
    
}
