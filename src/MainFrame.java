import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTable tableAllPatients;
	private JScrollPane scrollPane;
	private JTextField textIndexPatient;
	private HashMap<Integer, Patient> patientProfiles;
	private ArrayList<String[]> forDisplay = new ArrayList<String[]>();
	private static ArrayList<String> tableHeader = new ArrayList<String> (Arrays.asList("Patient_ID", "Race", "Gender", "Age", "Num_Procedures", 
			"Num_Medications", "Num_LabTests", "Num_Outpatient", "Num_Inpatient", "Num_ER_Visits"));
	private JTextField textdistancethd;
	private JRadioButton rdbtnEuclidean;
	private JRadioButton rdbtnCosine;
	private ButtonGroup distance;
	private String distanceType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		//set the header and size of the main frame
		super("Clustering Patients Finder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		
		//add the data import button
		JButton btnImportData = new JButton("Import Data");
		final JFileChooser  fileDialog = new JFileChooser();
		btnImportData.setBounds(197, 449, 119, 29);
		
		
		//add the label to show user how many patients are imported
		JLabel lbltotal = new JLabel("Total Number of Patients: "+ 0);
		lbltotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lbltotal.setBounds(1211, 419, 240, 16);
		contentPane.add(lbltotal);
		//read in the csv file after user clicks the 'Import Data' button and fill in patient information into the table   
		btnImportData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open the dialogue box to let user select the input csv file
				int returnVal = fileDialog.showOpenDialog(contentPane);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	
			        try {
			        	//import data
			            DataReader patientData = new DataReader();
		            	ArrayList<ClinicalEncounter> clinicalEncounter = patientData.inputFileReader(
		            			fileDialog.getSelectedFile().getAbsolutePath());
		            	
		            	PatientProcessor patientProcessor = new PatientProcessor();
		            	patientProfiles = patientProcessor.buildPatientProfiles(
		            			clinicalEncounter);
		            	DataAnalysis data = new DataAnalysis();

		            	forDisplay = data.getPatientForDisplay(patientProfiles);
	            		int totalNumPatients = forDisplay.size();	
	            		lbltotal.setText("Total Number of Patients: " + totalNumPatients);
			            tableAllPatients.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			            
			            DefaultTableModel model = (DefaultTableModel) tableAllPatients.getModel();
			            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
			            tableAllPatients.setRowSorter(sorter);
			            // set fill patient information into the table
			            for(int i = 0; i < totalNumPatients; i++)
			            {
			                model.addRow(new Vector<Object>(Arrays.asList(forDisplay.get(i))));
			            }
			            			            
			            tableAllPatients.setShowGrid(true);
			            tableAllPatients.setGridColor(Color.black);	
			    			            
			        } catch (Exception e1) {
			            e1.printStackTrace();;
			        }
			        
			        
	            } else {
	            	return;
	            }
				
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnImportData);
		//make the patient table scrollable
		scrollPane = new JScrollPane();
		scrollPane.setBounds(64, 34, 1387, 378);
		contentPane.add(scrollPane);
		
		//assign the column header of the patient table
		tableAllPatients = new JTable(null, new Vector<Object>(tableHeader));
		tableAllPatients.setForeground(Color.BLACK);
		tableAllPatients.setBackground(Color.WHITE);
		scrollPane.setViewportView(tableAllPatients);
		
		//label for the index patient input textbox
		JLabel lblIP = new JLabel("Index Patient:");
		lblIP.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblIP.setBounds(393, 449, 119, 29);
		contentPane.add(lblIP);
		
		JLabel lblIPnote = new JLabel("(Patient ID)");
		lblIPnote.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblIPnote.setBounds(403, 474, 74, 16);
		contentPane.add(lblIPnote);
		
		//textbox for user to input index patient ID
		textIndexPatient = new JTextField();
		textIndexPatient.setBounds(489, 449, 103, 29);
		contentPane.add(textIndexPatient);
		textIndexPatient.setColumns(10);
		
		//submit button to retrieve the patients with distance less than the threshold input by the user		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//get the index patient ID
				int indexPatientId = Integer.parseInt(textIndexPatient.getText());	
				
				//get the distance threshold
				double distanceThreshold;
				String distanceType = null;
					
				//check radio button return value to see how user wants to calculate the distance between patients 
				if (rdbtnEuclidean.isSelected()) {
					distanceType = "euclidean";
				} else {
					distanceType = "cosine";
				}
				if (patientProfiles.containsKey(indexPatientId)) {
					//validate the distance threshold input by the user
					distanceThreshold = Double.parseDouble(textdistancethd.getText());
					if (distanceThreshold >= 0 && distanceThreshold <= 1) {
						new ClusteringFrame (
								patientProfiles, indexPatientId, distanceThreshold, distanceType);
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "The distance threshold must be between 0-1.",
								"Dialog", JOptionPane.ERROR_MESSAGE);
					}
					
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Can't find Patient" + indexPatientId+" in the input data!",
							"Dialog", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSubmit.setBounds(573, 531, 117, 29);
		contentPane.add(btnSubmit);
	
		JLabel lblMethod = new JLabel("Method for Distance Calculation");
		lblMethod.setBounds(663, 450, 251, 24);
		contentPane.add(lblMethod);
		
		//add distance type radio button group (Euclidean and Cosine) 
		distance = new ButtonGroup();

		rdbtnEuclidean = new JRadioButton("Euclidean");
		rdbtnEuclidean.setSelected(true);
		rdbtnEuclidean.setBounds(655, 474, 141, 23);
		contentPane.add(rdbtnEuclidean);
		
		rdbtnCosine = new JRadioButton("Cosine");
		rdbtnCosine.setBounds(655, 496, 141, 23);
		contentPane.add(rdbtnCosine);
		
		distance.add(rdbtnEuclidean);
		distance.add(rdbtnCosine);
		
		rdbtnEuclidean.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	textdistancethd.setText("0.2");
	        }
	    });
		
		rdbtnCosine.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	textdistancethd.setText("0.4");
	        }
	    });
		
		//create label and input box for user to input the distance threshold 
		JLabel lblthd1 = new JLabel("Patient Distance ");
		lblthd1.setBounds(943, 454, 119, 16);
		contentPane.add(lblthd1);
		
		JLabel lblthd2 = new JLabel("Threshold (0 - 1):");
		lblthd2.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblthd2.setBounds(943, 473, 119, 16);
		contentPane.add(lblthd2);
		
		textdistancethd = new JTextField("0.2");
		textdistancethd.setBounds(1060, 467, 65, 29);
		contentPane.add(textdistancethd);
		textdistancethd.setColumns(10);
				
		
	}

	public static ArrayList<String> getTableHeader() {
		return tableHeader;
	}
}
