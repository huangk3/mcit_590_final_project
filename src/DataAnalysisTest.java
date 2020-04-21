import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class DataAnalysisTest {

    DataAnalysis test = new DataAnalysis();
    DataReader tesDataReader = new DataReader();
    ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/diabetic_data.csv");
    PatientProcessor testProcessor = new PatientProcessor();
    HashMap<Integer, Patient> testPatientMap = testProcessor.buildPatientProfiles(testEncounters);
    HashMap<Integer, Double> distances = test.getDistanceAllPatients(3146373,testPatientMap,"euclidean");
    Double[] maxVals = test.maxProfileVals(testPatientMap);
    Double[] minVals = test.minProfileVals(testPatientMap);
    Double[] range = test.elementWiseSubstraction(maxVals, minVals);


    @Test
    void checkMean(){
    assertEquals(test.mean(testPatientMap.get(3146373).getProfile()),5.666666666666667);
    }

    @Test
    void checkStdDev(){
        assertEquals(test.stdDev(testPatientMap.get(3146373).getProfile()),11.125546178852424);
    }

    @Test
    void checkNormalVector(){
        Double[] testProfile = testPatientMap.get(3146373).getProfile();
        Double[] nomalizedProfile = test.normalize(testProfile);
        assertEquals(nomalizedProfile[0],-0.05992215, 0.001);
        assertEquals(nomalizedProfile[7],0.29961076, 0.001);

    }

    @Test
    void calculateEuclidean() {

//        assertEquals();
        assertEquals(test.calculateSimilarity(3146373,8222157,testPatientMap,range,"euclidean"),0.0692254697526158,0.0001);
    }

    @Test
    void calculateCosine()
    {
        Double cosineDist =test.calculateSimilarity(3146373, 8222157, testPatientMap, range, "cosine");
        assertEquals(cosineDist, 0.9693628892263843);
    }

    @Test
    void distanceCal()
    {
        assertEquals(distances.size(),testPatientMap.size());
    }

    @Test
    void sortedData()
    {
        mapValueComparator compareVals = new mapValueComparator(distances);
        TreeMap<Integer, Double> sorted = new TreeMap<>(compareVals);
        sorted.putAll(distances);

        assertEquals(sorted.keySet().toArray()[0],3146373);
        assertEquals(sorted.get(3146373),0.0);
    }



}