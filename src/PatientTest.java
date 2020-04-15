import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class PatientTest {

	@Test
	void testPatientConstructor() {
		DataReader tesDataReader = new DataReader();
        ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/testSamplePatTest.csv");
        assertEquals(testEncounters.size(),61);
        
        Patient testPatientClass = new Patient(testEncounters);
	}
     @Test
     //This test is for single patient encounters
     void testSinglePatient() {
    	 DataReader tesDataReader = new DataReader();
         ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/testSamplePatTest.csv");
         Patient testPatientClass = new Patient(testEncounters);
        //Check Single Patient
        Patient singleTest = testPatientClass.patientsMap.get(48330783);
        assertEquals(singleTest.ageCat,"9");
        assertEquals(singleTest.race,"Caucasian");
        assertEquals(singleTest.gender,"Female");
        assertEquals(singleTest.encounterTotal,1);
        assertEquals(singleTest.avgNumInpVisits,0);
        assertEquals(singleTest.avgNumProcedures,2);
        assertEquals(singleTest.avgNumLabProcedures,68);
        assertEquals(singleTest.avgNumMedications,28);
        assertEquals(singleTest.avgNumOutpatientVisits,0);
        assertEquals(singleTest.avgNumEmergencyVisits,0);
        assertEquals(singleTest.totA1CElevated,0);
        assertEquals(singleTest.totReadmissions,0);
             
	}
     
     @Test
     //This test is for multiple encounters with no changing values
     void testMultiplePatient() {
    	 DataReader tesDataReader = new DataReader();
         ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/testSamplePatTest.csv");
         Patient testPatientClass = new Patient(testEncounters);
        //Check multiple Patient encounters
        Patient singleTest = testPatientClass.patientsMap.get(8222157);
        assertEquals(singleTest.ageCat,"1");
        assertEquals(singleTest.race,"Caucasian");
        assertEquals(singleTest.gender,"Female");
        assertEquals(singleTest.encounterTotal,2);
        assertEquals(singleTest.avgNumInpVisits,0);
        assertEquals(singleTest.avgNumProcedures,0);
        assertEquals(singleTest.avgNumLabProcedures,41);
        assertEquals(singleTest.avgNumMedications,1);
        assertEquals(singleTest.avgNumOutpatientVisits,0);
        assertEquals(singleTest.avgNumEmergencyVisits,0);
        assertEquals(singleTest.totA1CElevated,0);
        assertEquals(singleTest.totReadmissions,0);
     }
     
     @Test
     //This test looks at multiple encounters with changing values
     void testMultiplePatientSecond() {
    	 DataReader tesDataReader = new DataReader();
         ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/testSamplePatTest.csv");
         Patient testPatientClass = new Patient(testEncounters);
        //Check multiple encounters with all data changing
        Patient singleTest = testPatientClass.patientsMap.get(42519267);
        assertEquals(singleTest.ageCat,"5");
        assertEquals(singleTest.race,"Caucasian");
        assertEquals(singleTest.gender,"Male");
        assertEquals(singleTest.encounterTotal,2, "Should be 2");
        assertEquals(singleTest.avgNumInpVisits,2);
        assertEquals(singleTest.avgNumProcedures,4.50);
        assertEquals(singleTest.avgNumLabProcedures,51);
        assertEquals(singleTest.avgNumMedications,8);
        assertEquals(singleTest.avgNumOutpatientVisits,0);
        assertEquals(singleTest.avgNumEmergencyVisits,0);
        assertEquals(singleTest.totA1CElevated,2, "Should be 2 positive A1C");
        assertEquals(singleTest.totReadmissions,2, "Should be 2 readmission");
        
     }
}
