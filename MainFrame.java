import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JToggleButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTable tableAllPatients;
	private JScrollPane scrollPane;
	private JTextField textIndexPatient;
	
	private static ArrayList<String> tableHeader = new ArrayList<String> (Arrays.asList("Patient_ID", "Race", "Gender", "Age", "Time_in_Hospital", "Num_Procedures", 
			"Num_Medications", "Num_Outpatient", "Num_Emergency"));

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
		super("Clustering Patients Finder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		
		JButton btnImportData = new JButton("Import Data");
		final JFileChooser  fileDialog = new JFileChooser();
		btnImportData.setBounds(197, 449, 119, 29);
		
		btnImportData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fileDialog.showOpenDialog(contentPane);
	            File file = null;
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	file = fileDialog.getSelectedFile();    
	            } else {
	            	return;
	            }
        
		        try {
		            @SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader(file));
		            // get the first line
		            // get the columns name from the first line
		            // set columns name to the jtable model
		            br.readLine();
//		            String[] columnsName = firstLine.split(",");
		            
		            int[] column_index = {0, 2, 3, 4, 9, 13, 14, 15, 16}; 
		            
		            tableAllPatients.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		            
		            DefaultTableModel model = (DefaultTableModel) tableAllPatients.getModel();
		            // get lines from .csv file
		            Object[] csvLines = br.lines().toArray();
		            
		            // extract data from lines
		            // set data to table model
		            for(int i = 0; i < csvLines.length; i++)
		            {
		                String line = csvLines[i].toString().trim();
		                ArrayList<String> columnsData2Show = new ArrayList<String>();
		                
		                String[] dataRow = line.split(",");
		                for (int j =0; j<column_index.length; j++) {
		                	columnsData2Show.add(dataRow[column_index[j]]);
			            }
		                model.addRow(new Vector<Object>(columnsData2Show));
		            }
		            tableAllPatients.setShowGrid(true);
		            tableAllPatients.setGridColor(Color.black);
		            		            
		        } catch (Exception e1) {
		            e1.printStackTrace();;
		        }
				
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnImportData);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(64, 34, 1387, 378);
		contentPane.add(scrollPane);
		
		tableAllPatients = new JTable(null, new Vector<Object>(tableHeader));
		tableAllPatients.setForeground(Color.BLACK);
		tableAllPatients.setBackground(Color.WHITE);
		scrollPane.setViewportView(tableAllPatients);
		
		textIndexPatient = new JTextField();
		textIndexPatient.setBounds(605, 448, 103, 29);
		contentPane.add(textIndexPatient);
		textIndexPatient.setColumns(10);
		
		JLabel lblIP = new JLabel("Index Patient:");
		lblIP.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblIP.setBounds(509, 448, 119, 29);
		contentPane.add(lblIP);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int indexPatientEncounterId = Integer.parseInt(textIndexPatient.getText());
				ClusteringFrame clusteringFrame = new ClusteringFrame (indexPatientEncounterId);
			}
		});
		btnSubmit.setBounds(868, 448, 117, 29);
		contentPane.add(btnSubmit);
		
		JLabel lblIPnote = new JLabel("(Encounter ID)");
		lblIPnote.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblIPnote.setBounds(519, 473, 74, 16);
		contentPane.add(lblIPnote);
	}

	public static ArrayList<String> getTableHeader() {
		return tableHeader;
	}
}
