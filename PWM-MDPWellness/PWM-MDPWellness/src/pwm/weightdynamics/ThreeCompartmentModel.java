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
package pwm.weightdynamics;

import java.util.ArrayList;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;

/**
 * The three compartment human weight dynamics model developed by NIDDHK. 
 * 
 */
public class ThreeCompartmentModel implements FirstOrderDifferentialEquations {
    private double Ginit = 0.5;
    private double row_G = 4200; // kcal/Kg
    private double row_F = 9488.5277192; // kcal/kg
    private double row_L = 1816.4435936; // kcal/kg
    private final double beta_tef = 0.1;
    private final double beta_at  = 0.14;
    private final double tau_at   = 14; //days
    private final double gammaF = 3.107074568;
    private final double gammaL = 21.988527712;
    private final double etaL   = 229.44550656;
    private final double etaF   = 179.2542975;
    
    private final double _Na_ = 3220;
    private final double xi_Na = 3000;
    private final double Xi_Cl = 4000;
    

    private double kG;
    private double CI_b;
    private double K;
    
    double ciFraction;
    double deltaNa;
    double baseLineCalories;
    
    double initialWeight;
    double height;
    double age;
    String gender;
    double pal_init;
    
    ArrayList<Double> currentCalories;
    ArrayList<Double> pal_final;
    
    private int timeStep;
    
    /**
     *
     * @param currentWeight The Participant current Weight in KG's
     * @param height        The participant current height in meters
     * @param age           The participant current age     in years
     * @param gender        Participant Gender (Male / Female )
     * @param pal_init      The participant current exercise activity level ( 1.0 - 3.0 )
     *                      1.0 - No physical activity 
     *                      2.0 - High Intensity physical activity 3-4 times a week
     */
    public ThreeCompartmentModel(int currentWeight, double height, double age, String gender, double pal_init)    {
        this.initialWeight = currentWeight;
        this.ciFraction = 0.5;
        this.deltaNa = 0;
        this.pal_init = pal_init;
        
        this.height = height;
        this.age = age;
        this.gender = gender;
        
        baseLineCalories = getRMR(initialWeight) * pal_init;
        CI_b = baseLineCalories * 0.5;
        kG = CI_b/(Math.pow(Ginit,2));
        
        updateK();
    }
    
    /**
     *
     * @return the total number of variables in this model
     */
    @Override
    public int getDimension() {
        return 5;
    }
    
    /**
     *
     * @return the time step in days that the model uses to estimate the participant
     *         weight after. 
     */
    public int getTimeStep() {
        return this.timeStep;
    }
    
    /**
     *
     * @param time set the time in days to estimate the participant weight after
     */
    public void setTimeStep(int time) {
        this.timeStep = time;
    }

    /**
     *
     * @param t
     * @param y
     * @param yDot
     * @throws MaxCountExceededException
     * @throws DimensionMismatchException
     */
    @Override
    public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
        double bodyWeight = y[0] + y[1] + y[2] + y[3];
        double rmr = getRMR(bodyWeight);
        double calories = getCalories(t);
        double palFinal = getPAL(t);
        double delta = getDelta(palFinal, rmr, bodyWeight);
        double ciIntake = calories*ciFraction;
        double P = getP(y[2]);
        double tef = getTEF(calories);
        double ee = K + gammaF*y[2] + gammaL*y[3] + delta*bodyWeight + tef + y[4] + etaL*yDot[3] + etaF*yDot[2];
        
        yDot[0] = (ciIntake - kG*Math.pow(y[0],2))/row_G;
        yDot[1] = (deltaNa - xi_Na*(y[1] - getInitialECF()) - Xi_Cl*(1 - ciIntake/CI_b))/_Na_;
        yDot[2] = ((1-P)*(calories - ee - row_G*yDot[0] ))/row_F;
        yDot[3] = ((P)*(calories - ee - row_G*yDot[0] ))/row_L;
        yDot[4] = (beta_at*(calories - baseLineCalories) - y[4])/tau_at;
    }
    
    private double getCalories(double t)
    {
        int day = (int)t;
        return currentCalories.get(day);
    }
    
    private double getPAL(double t)
    {
        int day = (int)t;
        return pal_final.get(day);
    }
    
    private void updateK()
    {
        double tef = getTEF(baseLineCalories);
        double rmr = getRMR(initialWeight);
        double delta = getDelta(pal_init,rmr,initialWeight);
        double at = 0;
        
        double fatMass = getInitialFatMass();
        double leanMass = getInitialLeanMass();
        K = baseLineCalories - (gammaF*fatMass + gammaL*leanMass + delta*initialWeight + tef + at );
    }
    
    double getInitialLeanMass()
    {
        double fatmass = getInitialFatMass();
        double initECF = getInitialECF();
        return (initialWeight -Ginit - initECF - fatmass);
    }
    
    double getInitialECF() {
        double initECF = 0.7*0.235*initialWeight;
        return initECF;
    }
    
    double getBaseLineCalories()
    {
        return this.baseLineCalories;
    }

    
    double getInitialFatMass()
    {
        double fm = 0;
        if(gender.contains("F"))
        {
            fm = initialWeight/100*((0.14*age) + (39.96*Math.log(initialWeight/(Math.pow(height,2))) - 102.01));
        }    
       else
        {
            fm = initialWeight/100*((0.14*age) + (37.31*Math.log(initialWeight/(Math.pow(height,2))) - 103.94));
        }
        
        return fm;
    }
    
    private double getP(double fatMass)
    {
        double C = 10.4*row_L/row_F;
        return C/(C+fatMass);
    }
    
    
    private double getTEF(double calories)
    {
        return beta_tef*(calories- baseLineCalories);
    }
    
    private double getDelta(double pal,double rmr,double BW)
    {
        double delta = ((1 - beta_tef)*pal - 1)*rmr/BW;
        return delta;
    }
    
    /**
     *
     * @param weight
     * @return
     */
    double getRMR(double weight)
    {
        double rmr = 0;
        if(gender.contains("F"))
        {
            rmr = 9.99*weight + 6.25*(height*100) - 4.92*age - 161;
        }
        else
        {
            rmr = 9.99*weight + 6.25*(height*100) - 4.92*age + 5.0;
        }
        
        return rmr;
    }
    
    /**
     *
     * @param currentCalories A list of daily calories that the participant is supposed to eat.
     * @param pal_final A list of daily physical activity level that the participant should be exercising at. 
     */
    public void setCurrentParameters(ArrayList<Double> currentCalories, ArrayList<Double> pal_final) {
        this.currentCalories = currentCalories;
        this.pal_final = pal_final;
    }

    /**
     *
     * @return the participant final weight at the end of time_step, as calculated by the model.
     */
    public int getFinalWeight() {
        double[] y = new double[]{0.5,this.getInitialECF(),this.getInitialFatMass(),this.getInitialLeanMass(),0};
        FirstOrderIntegrator integrator = new ClassicalRungeKuttaIntegrator(0.1);
        integrator.integrate(this, 0, y, (timeStep-1), y);
        double finalWeight = (y[0]+y[1]+y[2]+y[3]);
                    
//        System.out.println(" initial Weight "+currentWeight+" "+calories+" "+pa + " final Weight" + finalWeight );
        return (int) finalWeight;
    }
}
