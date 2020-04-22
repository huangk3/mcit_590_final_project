import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataReaderTest {

    @Test
    void inputFileReader() {
        DataReader tesDataReader = new DataReader();
        ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/diabetic_data.csv");
        //assertEquals(testEncounters.size(),3);
        ClinicalEncounter testEncounter = testEncounters.get(0);
        //assertEquals(testEncounter.getWeight(),168);
        String testAge = "[0-10)";
        assertEquals(testEncounter.getAge(),testAge);
        ClinicalEncounter testEncounter2 = testEncounters.get(2);
        assertEquals(testEncounter2.getPatientNbr(),86047875);
    }
}