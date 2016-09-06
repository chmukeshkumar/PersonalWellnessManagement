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
package pwm.visualizer;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class POMDPBeliefDistributionView extends JPanel {
    private DefaultCategoryDataset beliefStates;
    
    POMDPBeliefDistributionView(String title) {
        ChartPanel Panel = getChartPanel(title);
        this.setLayout(new BorderLayout());
        this.add(Panel,BorderLayout.CENTER);
        update(new double[]{0.2,0.2,0.2,0.2,0.2});
    }
    
     private ChartPanel getChartPanel(String title) {
        beliefStates = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart( title ,"","Probability",beliefStates, PlotOrientation.VERTICAL,true,true,false);
        chart.removeLegend();
        
        CategoryPlot xyplot = (CategoryPlot) chart.getCategoryPlot();
        
        ChartPanel chartPanel = new ChartPanel(chart, true, true, true, true, true);
        chartPanel.setMouseWheelEnabled(true);
        
        xyplot.getDomainAxis().setLabelFont(new Font("Ariel",Font.BOLD,18));
        return chartPanel;
    }
    
    public void update(double[] newNutritionDistribution) {
        
        beliefStates.addValue(newNutritionDistribution[0],"","Pre-contemplation");
        beliefStates.addValue(newNutritionDistribution[1],"","Contemplation");
        beliefStates.addValue(newNutritionDistribution[2],"","Preparation");
        beliefStates.addValue(newNutritionDistribution[3],"","Action");
        beliefStates.addValue(newNutritionDistribution[4],"","Maintenance");
        
    }
    
}
