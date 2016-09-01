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

/**
 *
 * @author munna
 */
public class PomdpParams {
    double transitionProbability[][][];
    double observationProbability[][][];
    
    double beliefDistribution[];
    
    PomdpParams(int states, int actions, int observations) {
        transitionProbability = new double[states][states][actions];
        observationProbability = new double[states][observations][actions];
    }
    
    void setInitialDistribution(double[] initialDistribution) {
        this.beliefDistribution = new double[initialDistribution.length];
        for(int i =0 ;i<initialDistribution.length;i++) {
            this.beliefDistribution[i] = initialDistribution[i];
        }
    }
    
    void addTransitionProbability(int fromState, int action, double[] probabilities) {
        for(int i=0;i<probabilities.length;i++) {
            transitionProbability[fromState][i][action] = probabilities[i];
        }
    }
    
    
    double getTransitionProbability(int fromState, int action, int toState) {
        return transitionProbability[fromState][toState][action];
    }
    
    void addObservationProbability(int fromState, int action, double[] probabilities) {
        for(int i=0;i<probabilities.length;i++) {
            observationProbability[fromState][i][action] = probabilities[i];
        }
    }
    
    double getObservationProbability(int fromState, int action, int observation) {
        return observationProbability[fromState][observation][action];
    }

    void updateDistribution(int actionNumber, int observationNumber) {
        double newBeliefDistribution[] = new double[beliefDistribution.length];
        for(int stateNumber=0;stateNumber<beliefDistribution.length;stateNumber++) {
            double ob1 = getObservationProbability(stateNumber, actionNumber, observationNumber);
            double stateSum = getTransitionSum(actionNumber, stateNumber);
            double normalization = getNormalization(actionNumber, observationNumber);
            
            newBeliefDistribution[stateNumber] = ob1 * stateSum / normalization;
        }
        
        for(int i=0;i<beliefDistribution.length;i++) {
            this.beliefDistribution[i] = newBeliefDistribution[i];
        }
    }
    
     double getNormalization(int actionNumber, int observationNumber) {
        double sum = 0;
        for(int s=0;s<beliefDistribution.length;s++) {
            for(int sprime = 0;sprime<beliefDistribution.length;sprime++) {
               double p1 = getObservationProbability(sprime, actionNumber, observationNumber);
               double p2 = getTransitionProbability(s,actionNumber, sprime);
               double p3 = beliefDistribution[s];
               sum += (p1 * p2 * p3);
            }
        }
        return sum;
    }
    
    double getTransitionSum(int actionNumber ,int stateNumber) {
        double sum = 0;
        for(int s=0;s<beliefDistribution.length;s++ ) {
            double t = getTransitionProbability(s,actionNumber,stateNumber);
            double p = beliefDistribution[s];
             sum += (t * p);
        }
        return sum;
    }
    
    double[] getBeliefDistribution() {
        return this.beliefDistribution;
    }
    
    
             
    
    
    
}
