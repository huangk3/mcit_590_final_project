import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class PatientTest {

	@Test
	void testPatientConstructor() {
		DataReader tesDataReader = new DataReader();
		ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/testSamplePatTest.csv");
		assertEquals(testEncounters.size(),61);


	}
	@Test
	//This test is for get Patient Profile
	void testGetPatientProfileOne() {
		DataReader tesDataReader = new DataReader();
		ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/testSamplePatTest.csv");
		PatientProcessor testProcessor = new PatientProcessor();
		HashMap<Integer, Patient> testPatientMap = testProcessor.buildPatientProfiles(testEncounters);

		//Check Single Patient
		Patient singleTest = testPatientMap.get(48330783);
		Double [] control = new Double[] {9.0, 68.0, 2.0, 0.0, 0.0, 0.0, 0.0, 28.0, 1.0};

		for (int i = 0; i < singleTest.getProfile().length; i ++) {
			assertEquals(control[i], singleTest.getProfile()[i] ,"Should be a match to control");
		}

	}

	@Test
	//This test is for get Patient Profile
	void testGetPatientProfileTwo() {
		DataReader tesDataReader = new DataReader();
		ArrayList<ClinicalEncounter> testEncounters = tesDataReader.inputFileReader("data/testSamplePatTest.csv");
		PatientProcessor testProcessor = new PatientProcessor();
		HashMap<Integer, Patient> testPatientMap = testProcessor.buildPatientProfiles(testEncounters);

		//Check Single Patient
		Patient singleTest = testPatientMap.get(42519267);
		Double [] control = new Double[] {5.0, 51.0, 4.5, 0.0, 2.0, 0.0, 2.0, 8.0, 0.0};

		for (int i = 0; i < singleTest.getProfile().length; i ++) {
			assertEquals(control[i], singleTest.getProfile()[i] ,"Should be a match to control");
		}
	}
}
