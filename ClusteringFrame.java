import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.util.Rotation;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import java.awt.Font;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.Dimension;

public class ClusteringFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ClusteringFrame(int encounterID) {
		super("Patients Clustered with the Index Patient");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(150, 150, 1200, 600);
		setVisible(true);
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		JLabel lblTitle = new JLabel("Patients Clustering with Index Patient: " + encounterID);
		lblTitle.setBounds(358, 34, 492, 34);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 15));
 		
		JLabel lblRace = new JLabel("Race Distribution");
		lblRace.setBounds(192, 336, 109, 16);
		lblRace.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblGender = new JLabel("Gender Distribution");
		lblGender.setBounds(532, 336, 133, 16);
		lblGender.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblClusteringPlot = new JLabel("Clustering Plot");
		lblClusteringPlot.setBounds(898, 336, 133, 16);
		lblClusteringPlot.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.setLayout(null);
		contentPane.add(lblRace);
		contentPane.add(lblGender);
		contentPane.add(lblClusteringPlot);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 86, 1103, 232);
		contentPane.add(scrollPane);
		
		//Table to save the clustering patients's clinical profile; 
		table = new JTable(null, new Vector<Object>(MainFrame.getTableHeader()));
		scrollPane.setViewportView(table);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		contentPane.add(lblTitle);
		
		
        //Generate the pie chart of the race distribution among the clustered patient.
        JPanel panelRace = new JPanel();
        panelRace.setBounds(89, 366, 288, 180);
        contentPane.add(panelRace);
        panelRace.setLayout(new BorderLayout(0, 0));
        PieDataset raceDataset = createPieDataset();
        JFreeChart pieChart = createPieChart(raceDataset);  
        ChartPanel pc = new ChartPanel(pieChart);
        panelRace.add(pc);
        
        //Generate the bar chart of gender distribution among the clustered patients
        JPanel panelGender = new JPanel();
        panelGender.setBounds(461, 366, 288, 180);
        contentPane.add(panelGender);
        panelGender.setLayout(new BorderLayout(0, 0));
        CategoryDataset genderDataset = createBarDataset();
        JFreeChart barChart = createBarChart(genderDataset);
        ChartPanel bc = new ChartPanel(barChart);
        panelGender.add(bc);
        
        JPanel panelClustering = new JPanel();
        panelClustering.setBounds(826, 366, 288, 180);
        contentPane.add(panelClustering);
        panelClustering.setLayout(new BorderLayout(0, 0));
        
        XYZDataset ds = createBubbleChartDataset();
        JFreeChart chart = ChartFactory.createBubbleChart("",
                "Num_Procedures", "Num_Medications", ds, PlotOrientation.VERTICAL, true, true,
                false);
        ChartPanel cp = new ChartPanel(chart);
        panelClustering.add(cp);
                
	}
	
	private static XYZDataset createBubbleChartDataset() {

        DefaultXYZDataset ds = new DefaultXYZDataset();

        double[][] data = { {5, 2, 6, 1, 10}, {1, 2, 3, 5, 10} , {0.5 , 1, 2, 0.7, 1}};

        ds.addSeries("Clustered Patients' Profile", data);

        return ds;
    }
	
	
	private static PieDataset createPieDataset() {

		DefaultPieDataset ds = new DefaultPieDataset();

		ds.setValue("Caucassian", 50);
        ds.setValue("African-Ameican", 15);
        ds.setValue("Asian", 5);
        ds.setValue("Mexico", 25);
        return ds;
    }
	
	
	
	private JFreeChart createPieChart(PieDataset dataset) {
        System.out.println("Create Chart");
        JFreeChart chart = ChartFactory.createPieChart3D(
            "", dataset, true, true, false);
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setCircular(true);
        return chart;

    }
	
	private CategoryDataset createBarDataset() {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		ds.addValue(4000, "Male", "");
		ds.addValue(6000, "Female", "");
		ds.addValue(100, "Unknown", "");
		
		return ds;
		
	}
	
	private JFreeChart createBarChart(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(
				"", "Gender", "Count", dataset,PlotOrientation.HORIZONTAL,           
		         true, true, false);
		return chart;
	}
}
