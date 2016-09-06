/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author cesl
 */
class MotivationDistributionPanel extends JPanel {
    private XYSeriesCollection motivationDataSet;
    private XYPlot xyplot;
    public MotivationDistributionPanel(String title) {
        motivationDataSet = new XYSeriesCollection();
        JPanel panel = createPanel(title);
        this.setPreferredSize(new Dimension(500,300));
        this.setLayout(new BorderLayout());
        this.add(panel);
    }
    
    private JPanel createPanel(String title) {
        IntervalXYDataset xybardataset = new XYBarDataset(motivationDataSet,0.2D);
        JFreeChart jfreechart = ChartFactory.createXYBarChart(title,"",false,"Likelihood",xybardataset,  PlotOrientation.VERTICAL,true,false,false);
        xyplot = (XYPlot) jfreechart.getPlot();
        NumberAxis numberaxis = (NumberAxis)xyplot.getDomainAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        XYBarRenderer xybarrenderer = (XYBarRenderer)xyplot.getRenderer();
        xybarrenderer.setDrawBarOutline(false);
        
        return new ChartPanel(jfreechart);
    }
    
    public void updateDistribution(double[] distribution) {
        motivationDataSet.removeAllSeries();
        XYSeries xyseries = new XYSeries("Motivation");
        for(int i=0;i<distribution.length;i++) {
            xyseries.add(i,distribution[i]);
        }
        motivationDataSet.addSeries(xyseries);
    } 
}
