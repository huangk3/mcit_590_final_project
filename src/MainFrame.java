import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTable tableAllPatients;
	private JScrollPane scrollPane;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnImportData = new JButton("Import Data");
		btnImportData.setBounds(11, 28, 119, 29);
		btnImportData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filePath = "/Users/kehuang/eclipse-workspace/591FinalProject/src/mcit_590_final_project/data/diabetic_data.csv";
		        File file = new File(filePath);
		        
		        try {
		            BufferedReader br = new BufferedReader(new FileReader(file));
		            // get the first line
		            // get the columns name from the first line
		            // set columns name to the jtable model
		            String firstLine = br.readLine().trim();
		            String[] columnsName = firstLine.split(",");
		            
		            int[] column_index = {0, 2, 3, 4, 9, 13, 14, 15, 16}; 
		            ArrayList<String> columnsName2Show = new ArrayList<String>();
		            
		            for (int i =0; i<column_index.length; i++) {
		            	columnsName2Show.add(columnsName[i]);
		            }
		            
		            tableAllPatients.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		            DefaultTableModel model = (DefaultTableModel) tableAllPatients.getModel();
		            model.setColumnIdentifiers(new Vector<Object>(columnsName2Show));
		            
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
		                	columnsData2Show.add(dataRow[j]);
			            }
		                model.addRow(new Vector<Object>(columnsData2Show));
		            }
		            
		            
		            
		            
		        } catch (Exception e1) {
		            e1.printStackTrace();;
		        }
				
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnImportData);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(148, 34, 1317, 378);
		contentPane.add(scrollPane);
		
		tableAllPatients = new JTable();
		tableAllPatients.setForeground(Color.BLACK);
		tableAllPatients.setBackground(Color.WHITE);
		scrollPane.setViewportView(tableAllPatients);
	}
}
