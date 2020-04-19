import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DataAnalysisTest {

    DataAnalysis test = new DataAnalysis();
    DataReader tesDataReader = new DataReader();
    ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/diabetic_data.csv");
    PatientProcessor testProcessor = new PatientProcessor();
    HashMap<Integer, Patient> testPatientMap = testProcessor.buildPatientProfiles(testEncounters);
    @Test
    void calculateEuclidean() {
        assertEquals(test.calculateSimilarity(3146373,8222157,testPatientMap,"euclidean"),10.344080432788601);
    }

    @Test
    void calculateCosine()
    {
        Double cosineDist =test.calculateSimilarity(3146373, 8222157, testPatientMap, "cosine");
        assertEquals(cosineDist,0.9693628892263843);
    }
}