import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class PatientDataReader {

    public HashMap<Integer, Patient> inputFileReader(String filename){
        HashMap<Integer, Patient> patients = new HashMap<>();
        File inputFile = new File(filename);
        try{
            Scanner in = new Scanner(inputFile);
            in.nextLine(); //skip header
            while (in.hasNextLine()){
                String row = in.nextLine();
                String [] values = row.split(",");
                Patient patient = new Patient(values);
                patients.put(Integer.parseInt(values[0]),patient);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return patients;
    }
}
