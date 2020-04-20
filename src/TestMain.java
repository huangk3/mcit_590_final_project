import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class TestMain {
    public static void main(String[] args) {
        DataAnalysis test = new DataAnalysis();
        DataReader tesDataReader = new DataReader();
        ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/testSamplePatTest.csv");
        PatientProcessor testProcessor = new PatientProcessor();
        HashMap<Integer, Patient> testPatientMap = testProcessor.buildPatientProfiles(testEncounters);
        ArrayList<String[]> forDisplay = test.getPatientForDisplay(testPatientMap);
//        System.out.println(Arrays.toString(forDisplay.get(0)));

        HashMap<Integer, Double> distances = test.getDistanceAllPatients(3146373,testPatientMap,"euclidean");

//        System.out.println("Distance Array size = "+distances.size());
//        System.out.println("Sample distance " + distances.get(17928900));
//    for (Integer patient: distances.keySet()){
//        System.out.println("PatientNum = "+patient+" Distance= "+distances.get(patient));
//    }

        mapValueComparator compareVals = new mapValueComparator(distances);
        TreeMap<Integer, Double> sorted = new TreeMap<>(compareVals);

        sorted.putAll(distances);

        System.out.println("sorted Map size = "+ sorted.size());
//        for (Integer patientNum:sorted.keySet()){
//  Double temp = sorted.get(patientNum);
//            System.out.println("For patient Num = "+patientNum+" Distance ="+ sorted.get(patientNum));
//
//        }

//        System.out.println("Treemap entry " + sorted.get(3146373));

        ArrayList<Integer> neighbours = test.getNeighbours(distances,10);

        System.out.println("Total neighbors = "+neighbours.size() );

        ArrayList<String []> forDisplay2 = test.getPatientForDisplay(testPatientMap,distances,neighbours);
        System.out.println(Arrays.toString(forDisplay2.get(0)));
        System.out.println(Arrays.toString(forDisplay2.get(1)));
        System.out.println(Arrays.toString(forDisplay2.get(2)));

    HashMap<String,Double> testRaceDist= test.getRaceDistribution(neighbours, testPatientMap, "race");
        for(String race:testRaceDist.keySet()){
            System.out.println(race + " is "+testRaceDist.get(race) +"%");
        }

        HashMap<String,Double> testGenderDist= test.getRaceDistribution(neighbours, testPatientMap, "gender");
        for(String race:testGenderDist.keySet()){
            System.out.println(race + " is "+testGenderDist.get(race) +"%");
        }

        ArrayList<String[]> testScatter = test.getScatterPlotData("Total Procedures",
                "Total Emergency room visits",forDisplay2);

        System.out.println("Scatter Data 0"+ Arrays.toString(testScatter.get(0)));
        System.out.println("Scatter Data 1"+ Arrays.toString(testScatter.get(1)));
        System.out.println("Scatter Data 2" + Arrays.toString(testScatter.get(2)));
    }
}
