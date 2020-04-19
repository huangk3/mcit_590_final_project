import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DataAnalysisTest {

    DataAnalysis test = new DataAnalysis();
    PatientDataReader testPatientLoader = new PatientDataReader();
    HashMap<Integer,Patient> patientData = testPatientLoader.inputFileReader("data/patient_grouped.csv");

    @Test
    void calculateEuclidean() {
        assertEquals(test.calculateSimilarity(135,378,patientData,"euclidean"),13.88344337691482);
    }

    @Test
    void calculateCosine()
    {
        Double cosineDist =test.calculateSimilarity(135, 378, patientData, "cosine");
        assertEquals(cosineDist,0.9807302912855623);
    }
}