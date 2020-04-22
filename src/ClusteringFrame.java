import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYZDataset;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class ClusteringFrame extends JFrame {

	private JPanel contentPane;
	private JTable tableClusteringPatients;
	private HashMap<Integer, Double> similarities;
	private HashMap<String,Double> raceDist;
	private HashMap<String,Double> genderDist;
	private ArrayList<String[]> Scatter;
	private String xVar;
	private String yVar;
	/**
	 * Create the frame.
	 */
	public ClusteringFrame(HashMap<Integer, Patient> patientProfiles, int indexPatientId, double distanceThreshold, String distanceType) {
		//set the header and size of the clustering Frame
		super("Patients Clustered with the Index Patient");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(150, 150, 1200, 600);
		setVisible(true);
		contentPane = new JPanel();		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		
		//Label to show the index Patient's ID
		JLabel lblTitle = new JLabel("Patients Clustered with Index Patient: " + indexPatientId);
		lblTitle.setBounds(379, 6, 424, 34);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 15));
 		
		//Label for the panel of race distribution plot 
		JLabel lblRace = new JLabel("Race Distribution");
		lblRace.setBounds(153, 305, 109, 16);
		lblRace.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Label for the panel of gender distribution plot
		JLabel lblGender = new JLabel("Gender Distribution");
		lblGender.setBounds(531, 305, 133, 16);
		lblGender.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Label for the panel of 2-d clustering plot
		JLabel lblClusteringPlot = new JLabel("Clustering Plot");
		lblClusteringPlot.setBounds(928, 305, 133, 16);
		lblClusteringPlot.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.setLayout(null);
		contentPane.add(lblRace);
		contentPane.add(lblGender);
		contentPane.add(lblClusteringPlot);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 57, 1103, 229);
		contentPane.add(scrollPane);
		
		//Table to save the clustering patients's clinical profile;
		ArrayList<String> distanceTableHeader = new ArrayList<>(); 
		ArrayList<String> templateHeader = MainFrame.getTableHeader();
		for (int i = 0; i < templateHeader.size(); i++ ) {
			
			distanceTableHeader.add(templateHeader.get(i));
		}
		distanceTableHeader.add(distanceType+ " Distance");
		tableClusteringPatients = new JTable(null, new Vector<Object>(distanceTableHeader));
		tableClusteringPatients.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPane.setViewportView(tableClusteringPatients);
		DefaultTableModel model = (DefaultTableModel) tableClusteringPatients.getModel();
		
		//Enable the column sorting function 
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
		tableClusteringPatients.setRowSorter(sorter);
		contentPane.add(lblTitle);
		
		//Obtain the similarity (distance) between the index patient and all patients loaded 
		DataAnalysis patientClustering = new DataAnalysis();
		similarities = patientClustering.getDistanceAllPatients(indexPatientId, patientProfiles, distanceType);
		
		//Refine the patient by only including patients whose distance to the index patient is less than the threshold entered by user. 
        ArrayList<Integer> neighbours = patientClustering.getNeighbours(similarities, distanceThreshold);
        ArrayList<String []> forDisplay = patientClustering.getPatientForDisplay(patientProfiles, similarities, neighbours);

        //Fill in the table using the metrics of patients clustered with the index patient.  
        for(int i = 0; i < forDisplay.size(); i++)
        {
            model.addRow(new Vector<Object>(Arrays.asList(forDisplay.get(i))));
        }
        tableClusteringPatients.setShowGrid(true);
        tableClusteringPatients.setGridColor(Color.black);
        
        //show user how many patients meet the clustering criteria.
        JLabel lblnbrSize = new JLabel("N = " + neighbours.size());
        lblnbrSize.setHorizontalAlignment(SwingConstants.CENTER);
        lblnbrSize.setBounds(529, 35, 135, 16);
        contentPane.add(lblnbrSize);
        //Generate the pie chart of the race distribution among the clustered patient.
        raceDist = patientClustering.getRaceDistribution(neighbours, patientProfiles, "race");
        
        
        //Add the panel to inject the race distribution plot
        JPanel panelRace = new JPanel();
        panelRace.setBounds(48, 333, 350, 213);
        contentPane.add(panelRace);
        panelRace.setLayout(new BorderLayout(0, 0));

        //Generate race distribution plot among the clustered patients
        PieDataset raceDataset = Plots.createPieDataset(raceDist);
        JFreeChart pieChart = Plots.createPieChart(raceDataset);  
        ChartPanel pc = new ChartPanel(pieChart);
        panelRace.add(pc);
        
        //Generate the bar chart of gender distribution among the clustered patients
        genderDist = patientClustering.getRaceDistribution(neighbours, patientProfiles, "gender");
        JPanel panelGender = new JPanel();
        panelGender.setBounds(444, 333, 329, 213);
        contentPane.add(panelGender);
        panelGender.setLayout(new BorderLayout(0, 0));
        CategoryDataset genderDataset = Plots.createBarDataset(genderDist);
        JFreeChart barChart = Plots.createBarChart(genderDataset);
        ChartPanel bc = new ChartPanel(barChart);
        panelGender.add(bc);
        
        //xVar is the variable that will be used for x axis in the clustering bubble plot.
        //yVar is the variable that will be used for y axis in the clustering bubble plot.
        xVar = "Total Emergency room visits";
        yVar = "Total Procedures";
        Scatter = patientClustering.getScatterPlotData(xVar, yVar, forDisplay);
        JPanel panelClustering = new JPanel();
        panelClustering.setBounds(826, 333, 325, 213);
        contentPane.add(panelClustering);
        panelClustering.setLayout(new BorderLayout(0, 0));
        
        //Generate the bubble plot xVar vs yVar  among the clustered patients
        double bubbleSizeAdjustment; //adjust the bubble size according to the distance type used selected;
        if (distanceType.equals("euclidean")) {
        	bubbleSizeAdjustment =  0.025;
        } else {
        	bubbleSizeAdjustment =  0.25;
        }
        
        XYZDataset scatterDataset = Plots.createBubbleChartDataset(Scatter, bubbleSizeAdjustment);
        JFreeChart catterChart = Plots.createBubbleChart(xVar, yVar, scatterDataset);
        ChartPanel cp = new ChartPanel(catterChart);
        panelClustering.add(cp);
        
        
                
	}
}
