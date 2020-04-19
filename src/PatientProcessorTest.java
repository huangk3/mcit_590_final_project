import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class PatientProcessorTest {

	@Test
	void testPatientConstructor() {
		DataReader tesDataReader = new DataReader();
		ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/testSamplePatTest.csv");
		assertEquals(testEncounters.size(),61);

	}
	@Test
	//This test is for single patient encounters
	void testSinglePatient() {
		DataReader tesDataReader = new DataReader();
		ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/testSamplePatTest.csv");
		PatientProcessor testProcessor = new PatientProcessor();
		HashMap<Integer, Patient> testPatientMap = testProcessor.buildPatientProfiles(testEncounters);


		//Check Single Patient
		Patient singleTest = testPatientMap.get(48330783);
		assertEquals(singleTest.ageCat, 9);
		assertEquals(singleTest.race,"Caucasian");
		assertEquals(singleTest.gender,1);
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
		PatientProcessor testProcessor = new PatientProcessor();
		HashMap<Integer, Patient> testPatientMap = testProcessor.buildPatientProfiles(testEncounters);

		//Check multiple Patient encounters
		Patient singleTest = testPatientMap.get(8222157);
		assertEquals(singleTest.ageCat, 1);
		assertEquals(singleTest.race,"Caucasian");
		assertEquals(singleTest.gender, 1);
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
		PatientProcessor testProcessor = new PatientProcessor();
		HashMap<Integer, Patient> testPatientMap = testProcessor.buildPatientProfiles(testEncounters);

		//Check multiple encounters with all data changing
		Patient singleTest = testPatientMap.get(42519267);
		assertEquals(singleTest.ageCat, 5);
		assertEquals(singleTest.race,"Caucasian");
		assertEquals(singleTest.gender, 0);
		assertEquals(singleTest.encounterTotal,2, "Should be 2");
		assertEquals(singleTest.avgNumInpVisits,2);
		assertEquals(singleTest.avgNumProcedures,4.50);
		assertEquals(singleTest.avgNumLabProcedures,51);
		assertEquals(singleTest.avgNumMedications,8);
		assertEquals(singleTest.avgNumOutpatientVisits,0);
		assertEquals(singleTest.avgNumEmergencyVisits,0);
		assertEquals(2, singleTest.totA1CElevated, "Should be 2 positive A1C");
		assertEquals(2, singleTest.totReadmissions, "Should be 2 readmission");


	}

}
