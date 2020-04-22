import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataAnalysisTest {

    DataAnalysis test = new DataAnalysis();
    DataReader tesDataReader = new DataReader();
    ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/diabetic_data.csv");
    PatientProcessor testProcessor = new PatientProcessor();
    HashMap<Integer, Patient> testPatientMap = testProcessor.buildPatientProfiles(testEncounters);
    HashMap<Integer, Double> distances = test.getDistanceAllPatients(3146373,testPatientMap,"euclidean");
    Double[][] minMaxVals = test.minMaxProfileVals(testPatientMap);
    Double[] range = test.elementWiseSubstraction(minMaxVals[0], minMaxVals[1]);

    // Test the mean of the array calculation
    @Test
    void checkMean(){
    assertEquals(test.mean(testPatientMap.get(3146373).getProfile()),5.666666666666667);
    }

    // Test to check standard deviation of array calculation
    @Test
    void checkStdDev(){
        assertEquals(test.stdDev(testPatientMap.get(3146373).getProfile()),11.125546178852424);
    }

    // Test to check if data is normalized as expected
    @Test
    void checkNormalVector(){
        Double[] testProfile = testPatientMap.get(3146373).getProfile();
        Double[] nomalizedProfile = test.normalize(testProfile);
        assertEquals(nomalizedProfile[0],-0.05992215, 0.001);
        assertEquals(nomalizedProfile[7],0.29961076, 0.001);

    }

    // Test the calculation of euclidean distance
    @Test
    void calculateEuclidean() {

        assertEquals(test.calculateSimilarity(3146373,8222157,
                testPatientMap,range,"euclidean"),0.3707,0.001);
    }

    // Test the calculation of cosine distance
    @Test
    void calculateCosine()
    {
        Double cosineDist =test.calculateSimilarity(3146373, 8222157,
                testPatientMap, range, "cosine");
        assertEquals(cosineDist, 0.9693628892263843, 0.001);
    }

    // Test if all the members processed when calculating distances among all members

    @Test
    void distanceCalVecSize()
    {
        assertEquals(distances.size(),testPatientMap.size());
    }

    // Confirm if the sorting of the data worked as expected
    @Test
    void sortedData()
    {
        mapValueComparator compareVals = new mapValueComparator(distances);
        TreeMap<Integer, Double> sorted = new TreeMap<>(compareVals);
        sorted.putAll(distances);

        assertEquals(sorted.keySet().toArray()[0],3146373);
        assertEquals(sorted.get(3146373),0.0);
    }

    // Check if the min and max values of factors used in utilization profile calculated correctly
    @Test
    void minMaxValCheck(){
        Double[] minArrayTruth = {1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
        Double[] maxArrayTruth = {10.0, 121.0, 6.0, 36.0, 14.13, 44.72, 9.0, 81.0, 1.0};
        assertArrayEquals(minMaxVals[0],minArrayTruth);
        assertArrayEquals(minMaxVals[1],maxArrayTruth);
    }

    // Test the data returned as expected for front-end
    @Test
    void  displayData(){
        ArrayList<Integer> neighbours = test.getNeighbours(distances,.3);
        ArrayList<String []> forDisplay2 = test.getPatientForDisplay(testPatientMap,distances,neighbours);
        String[] testDisplayData = {"3146373", "Caucasian", "Male", "[40-50)", "1.0", "9.0", "36.0", "0.0", "0.0", "0.0", "0.0"};
        assertArrayEquals(forDisplay2.get(0),testDisplayData);
    }

}