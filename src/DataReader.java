import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for handling input csv file
 */
public class DataReader {

    /**
     * This method reads input csv file of patient encounters
     * @param filename Input csv file name
     */
    public ArrayList<ClinicalEncounter> inputFileReader(String filename){
        ArrayList<ClinicalEncounter> encounters = new ArrayList<>();
        File inputFile = new File(filename);
        try{
           Scanner in = new Scanner(inputFile);
           while (in.hasNextLine()){
               String row = in.nextLine();
               String [] values = row.split(",");
               ClinicalEncounter encounter = new ClinicalEncounter(values);
               encounters.add(encounter);
           }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return encounters;
    }

}
