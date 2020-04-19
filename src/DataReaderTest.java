import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataReaderTest {

    @Test
    void inputFileReader() {
        DataReader tesDataReader = new DataReader();
        ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/testSample.csv");
        //assertEquals(testEncounters.size(),3);
        ClinicalEncounter testEncounter = testEncounters.get(0);
        //assertEquals(testEncounter.getWeight(),168);
        String testSpecialty = "Pediatrics-Endocrinology";
        assertEquals(testEncounter.getMedicalSpecialty(),testSpecialty);
        ClinicalEncounter testEncounter2 = testEncounters.get(2);
        assertEquals(testEncounter2.getPatientNbr(),86047875);
    }
}