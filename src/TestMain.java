import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class TestMain {
    public static void main(String[] args)  {
        DataAnalysis test = new DataAnalysis();
        DataReader tesDataReader = new DataReader();
        ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/diabetic_data.csv");
        PatientProcessor testProcessor = new PatientProcessor();
        HashMap<Integer, Patient> testPatientMap = testProcessor.buildPatientProfiles(testEncounters);
        ArrayList<String[]> forDisplay = test.getPatientForDisplay(testPatientMap);
//        System.out.println(Arrays.toString(forDisplay.get(0)));

        System.out.println("Min :"+ Arrays.toString(test.minMaxProfileVals(testPatientMap)[0]));
        System.out.println("Max :"+Arrays.toString(test.minMaxProfileVals(testPatientMap)[1]));
//
        HashMap<Integer, Double> distances = test.getDistanceAllPatients(3146373,testPatientMap,"cosine");
//        Double[] patientA = testPatientMap.get(3146373).getProfile();
//        Double[] patientB = testPatientMap.get(8222157).getProfile();
//        System.out.println(Arrays.toString(patientA));
//        System.out.println(Arrays.toString(patientB));
//
//        System.out.println(Arrays.toString(test.normalize(patientA)));
//        System.out.println(Arrays.toString(test.normalize(patientB)));
//
//        Double[] maxVals = test.maxProfileVals(testPatientMap);
//        Double[] minVals = test.minProfileVals(testPatientMap);
//        Double[] range = test.elementWiseSubstraction(maxVals, minVals);
//
//        System.out.println("variation " +Arrays.toString(test.square(range)));
//        System.out.println(test.euclideanDistance(test.normalize(patientA), test.normalize(patientB)));
//        System.out.println(Arrays.toString(test.normalize(testPatientMap.get(3146373).getProfile())));

//        System.out.println("Distance Array size = "+distances.size());
//        System.out.println("Sample distance " + distances.get(17928900));
//    for (Integer patient: distances.keySet()){
//        System.out.println("PatientNum = "+patient+" Distance= "+distances.get(patient));
//    }

//        BufferedWriter br = new BufferedWriter(new FileWriter("out/patientData.csv"));
//        StringBuilder sb = new StringBuilder();

// Append strings from array
//        for (Integer patientNum : testPatientMap.keySet()) {
//            sb.append(Arrays.toString(testPatientMap.get(patientNum).getProfile()));
//            sb.append("\n");
//        }
//
//        br.write(sb.toString());
//        br.close();

        mapValueComparator compareVals = new mapValueComparator(distances);
        TreeMap<Integer, Double> sorted = new TreeMap<>(compareVals);

        sorted.putAll(distances);
//
        System.out.println("first "+sorted.firstEntry());
        System.out.println("Last " +sorted.lastEntry());
//
//        System.out.println("sorted Map size = "+ sorted.size());
//        for (Integer patientNum:sorted.keySet()){
//  Double temp = sorted.get(patientNum);
//            System.out.println("For patient Num = "+patientNum+" Distance ="+ sorted.get(patientNum));
//
//        }

//        System.out.println("Treemap entry " + sorted.get(3146373));

        ArrayList<Integer> neighbours = test.getNeighbours(distances,.3);

//        System.out.println("Total neighbors = "+neighbours.size() );

        ArrayList<String []> forDisplay2 = test.getPatientForDisplay(testPatientMap,distances,neighbours);
        System.out.println(Arrays.toString(forDisplay2.get(0)));
        System.out.println(Arrays.toString(forDisplay2.get(1)));
        System.out.println(Arrays.toString(forDisplay2.get(2)));

//    HashMap<String,Double> testRaceDist= test.getRaceDistribution(neighbours, testPatientMap, "race");
//        for(String race:testRaceDist.keySet()){
//            System.out.println(race + " is "+testRaceDist.get(race) +"%");
//        }
//
//        HashMap<String,Double> testGenderDist= test.getRaceDistribution(neighbours, testPatientMap, "gender");
//        for(String race:testGenderDist.keySet()){
//            System.out.println(race + " is "+testGenderDist.get(race) +"%");
//        }
//
//        ArrayList<String[]> testScatter = test.getScatterPlotData("Total Procedures",
//                "Total Emergency room visits",forDisplay2);
//
//        System.out.println("Scatter Data 0"+ Arrays.toString(testScatter.get(0)));
//        System.out.println("Scatter Data 1"+ Arrays.toString(testScatter.get(1)));
//        System.out.println("Scatter Data 2" + Arrays.toString(testScatter.get(2)));
    }
}
