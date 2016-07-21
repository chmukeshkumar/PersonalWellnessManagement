/* 
 * Copyright (C) 2016 mchippa
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
