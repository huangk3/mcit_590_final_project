import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PatientDataReaderTest {

    @org.junit.jupiter.api.Test
    void inputFileReader() {
        PatientDataReader testPatientLoader = new PatientDataReader();
        HashMap<Integer,Patient> patientData = testPatientLoader.inputFileReader("data/patient_grouped.csv");
        assertEquals(patientData.size(),71518);
        Patient test = patientData.get(135);
        assertEquals(test.getAge_category(),6);
        assertEquals(test.getAvg_num_meds(),23.5);
        Patient test2 = patientData.get(36738);
        assertEquals(test2.getTot_a1c_elevated(),1);
        assertEquals(test2.getTot_readmissions(),0);
    }
}