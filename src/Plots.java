import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.util.Rotation;

class Plots {
	
	/**
	 * This class receives the patient data and reformat the data and generate the plots. 
	 * (customized for bar plot, pie plot, and bubble plot;) 
	 */	
	
	//create the dataset for pie chart	
	static PieDataset createPieDataset(HashMap<String,Double> raceDist) {
		DefaultPieDataset ds = new DefaultPieDataset();
		for (String r : raceDist.keySet()) {
			ds.setValue(r, raceDist.get(r));
		}
        return ds;
    }
	
	//produce pie chart	
	static JFreeChart createPieChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart3D(
            "", dataset, true, true, false);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setCircular(true);
        return chart;

    }
	
	//create the dataset for bar chart
	static CategoryDataset createBarDataset(HashMap<String,Double> genderDist) {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		for (String g : genderDist.keySet()) {
			ds.addValue(genderDist.get(g), g , "");
		}
        return ds;	
	}
	
	//produce bar chart	
	static JFreeChart createBarChart(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(
				"", "Gender", "Percetage (%)", dataset,PlotOrientation.HORIZONTAL, true, true, false);
		return chart;
	}
	
	//create the dataset for bubble chart
	static XYZDataset createBubbleChartDataset(ArrayList<String[]> Scatter, double bubbleSizeAdjustment) {

		DefaultXYZDataset ds = new DefaultXYZDataset();
		int size = Scatter.size();
		double[] x = new double[size];
		double[] y = new double[size];
		double[] z = new double[size];
        
		for (int i=0; i < Scatter.size(); i++) {
			String[] p = Scatter.get(i);
			x[i]= Double.parseDouble(p[0]);
			y[i]= Double.parseDouble(p[1]);
			z[i]= Math.log1p(bubbleSizeAdjustment/Double.parseDouble(p[2]));
        }
		double[][] data = { x, y, z};
		ds.addSeries("Clustered Patients' Profile", data);
		return ds;
    }
	
	//produce bubble chart	
	static JFreeChart createBubbleChart(String xVar, String yVar, XYZDataset xyzDataset) {
		JFreeChart chart = ChartFactory.createBubbleChart("",
				xVar, yVar, xyzDataset, PlotOrientation.VERTICAL, true, true,
                false);
		return chart;
	}
		
}
