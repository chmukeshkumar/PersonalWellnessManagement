/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pwm.visualizer;


import java.awt.Color;
import java.util.Map;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import pwm.mdp.solver.Action;
import pwm.mdp.solver.MDPPolicy;
import pwm.mdp.solver.State;
import pwm.mdp.solver.StateActionTuple;

/**
 *
 * @author munna
 */
class MDPPolicyPanel extends JPanel {

    DefaultCategoryDataset nutritionDataSet = new DefaultCategoryDataset();
    DefaultCategoryDataset exerciseDataSet  = new DefaultCategoryDataset();

    public MDPPolicyPanel () {
        JFreeChart jfreechart = createChart();
        this.add(new ChartPanel(jfreechart));
    }
    
    public void updateData(MDPPolicy policy) {
         Map<String,StateActionTuple> stateActionTuples = policy.getStateActionTuples();
        
        for(Map.Entry<String,StateActionTuple> entry : stateActionTuples.entrySet())
        {
            State s = entry.getValue().getState();
            Action a = entry.getValue().getAction();
            
            int weight = s.getValue();
            String actionName = a.getName();
            if(actionName == null)
            {
                continue;
            }
             String tokens[] = actionName.split("-");
            double calories = Double.valueOf(tokens[0]);
            double pa = Double.valueOf(tokens[1]);

            nutritionDataSet.addValue(calories, "Nutrition", ""+ weight);
            nutritionDataSet.addValue(null,"Dummy 1",""+weight);
            exerciseDataSet.addValue(null,"Dummy 2",""+weight);
            exerciseDataSet.addValue(pa, "PA", ""+weight);
        }
    }
    
    private JFreeChart createChart()
    {
        NumberAxis nutritionAxis = new NumberAxis("Calories/Day");
        nutritionAxis.setLowerBound(0);
        nutritionAxis.setUpperBound(5000);
        nutritionAxis.setAutoRange(false);
//		
	CategoryPlot categoryplot = new CategoryPlot(   nutritionDataSet, 
                                                        new CategoryAxis("Weight(KG)"),
                                                        nutritionAxis,
                                                        new BarRenderer()
                                                    );
        CategoryAxis xaxis = categoryplot.getDomainAxis();
        xaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        LegendItemCollection legend = new LegendItemCollection();
        LegendItem nutritionLegend = new LegendItem("Calories/Day");
        LegendItem exerciseLegend  = new LegendItem("Physical Activity Level");
        nutritionLegend.setFillPaint(Color.BLUE);
        exerciseLegend.setFillPaint(Color.RED);

        legend.add(nutritionLegend);
        legend.add(exerciseLegend);

        categoryplot.setFixedLegendItems(legend);
        JFreeChart jfreechart = new JFreeChart("Policy Visualizer",categoryplot);
        categoryplot.setDataset(1,exerciseDataSet);
        categoryplot.mapDatasetToRangeAxis(1,1);

        NumberAxis exerciseAxis  = new NumberAxis("Physical Activity Level");
        exerciseAxis.setLowerBound(0);
        exerciseAxis.setUpperBound(3.0);
        exerciseAxis.setAutoRange(false);

        categoryplot.setRangeAxis(1, exerciseAxis);
        BarRenderer barrenderer1 = new BarRenderer();
        categoryplot.setRenderer(1, barrenderer1);

//        BarRenderer stackedbarrenderer = (BarRenderer)categoryplot.getRenderer();
//        stackedbarrenderer.setDrawBarOutline(true);
//        stackedbarrenderer.setBaseItemLabelsVisible(false);
//        stackedbarrenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

        ChartUtilities.applyCurrentTheme(jfreechart);

        return jfreechart;
                
                
    }

}
