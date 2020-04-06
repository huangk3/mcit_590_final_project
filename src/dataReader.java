import java.io.File;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class for handling input csv file
 */
public class dataReader {
    ArrayList<clinicalEncounter> encounters = new ArrayList<>();

    /**
     * This method reads input csv file of patient encounters
     * @param filename Input csv file name
     */
    public void inputFileReader(String filename){
        File inputFile = new File(filename);
        try{
           Scanner in = new Scanner(inputFile);
           in.nextLine();
           while (in.hasNextLine()){
               String row = in.nextLine();
               String [] values = row.split(",");
               clinicalEncounter encounter = new clinicalEncounter(values);
               encounters.add(encounter);
           }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
