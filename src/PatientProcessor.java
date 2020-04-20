import java.util.ArrayList;
import java.util.HashMap;

public class PatientProcessor {
	//HashMap of patients Key is patient NBR
	HashMap<Integer, Patient> patientsMap = new HashMap<Integer, Patient>();

	/**
	 * This method will take in an ArrayList of Clinical Encounters
	 * Parse thru the data and create patient profiles and store those patient profiles in a HashMap 
	 * @return
	 */

	public HashMap<Integer, Patient> buildPatientProfiles(ArrayList<ClinicalEncounter> clinic) {

		//Loop through encounters list and update or add patient data.
		for(ClinicalEncounter encounter : clinic) {
			if(patientsMap.containsKey(encounter.getPatientNbr())) {
				this.patientUpdate(encounter);
			}

			//Create new patient entry
			else {
				//Create new Patient and add to HashMap
				Patient workingPatient = new Patient();
				workingPatient.ageCat = updateAge(encounter.age);
				workingPatient.ageCatString = encounter.age;
				workingPatient.patientNbr = encounter.getPatientNbr(); 
				workingPatient.race = encounter.getRace();
				workingPatient.gender = updateGender(encounter.gender);
				workingPatient.genderString = encounter.gender;
				workingPatient.encounterTotal = 1;
				workingPatient.avgNumInpVisits = encounter.getNumberInpatient(); 
				workingPatient.avgNumProcedures = encounter.getNumProcedures(); 
				workingPatient.avgNumLabProcedures = encounter.getNumLabProcedures(); 
				workingPatient.avgNumMedications = encounter.getNumMedications(); 
				workingPatient.avgNumOutpatientVisits = encounter.getNumberOutpatient(); 
				workingPatient.avgNumEmergencyVisits = encounter.getNumberEmergency(); 
				workingPatient.totA1CElevated = calcA1CResults(workingPatient.totA1CElevated, encounter.getA1Cresult()); 
				workingPatient.totReadmissions = reAdmit(workingPatient.totReadmissions, encounter.getReadmitted());
				patientsMap.put(workingPatient.getPatientNbr(), workingPatient);
			} 
		}
		return patientsMap;
	}

	/**
	 * This class will grab patient NBR from Hashmap and update values
	 * @param patientNBR
	 * @param update
	 */
	public void  patientUpdate(ClinicalEncounter update) {
		//Variable for working Patient data
		Patient workingPatient = this.patientsMap.get(update.getPatientNbr());
		//Call methods for update
		workingPatient.ageCat = updateAge(update.getAge());
		workingPatient.avgNumLabProcedures = calculateRunningAverage(workingPatient.getTotalLab(), update.getNumLabProcedures(), workingPatient.encounterTotal);
		workingPatient.avgNumProcedures = calculateRunningAverage(workingPatient.getTotalProcedures(), update.getNumProcedures(), workingPatient.encounterTotal);
		workingPatient.avgNumMedications = calculateRunningAverage(workingPatient.getTotalMedications(), update.getNumMedications(), workingPatient.encounterTotal);
		workingPatient.avgNumOutpatientVisits = calculateRunningAverage(workingPatient.getTotalOutpatientVisits(), update.getNumberOutpatient(), workingPatient.encounterTotal);
		workingPatient.avgNumEmergencyVisits = calculateRunningAverage(workingPatient.getTotalEmergencyVisits(), update.getNumberEmergency(), workingPatient.encounterTotal);
		workingPatient.avgNumInpVisits = calculateRunningAverage(workingPatient.getTotalInpVisits(), update.getNumberInpatient(), workingPatient.encounterTotal);
		workingPatient.totA1CElevated = calcA1CResults(workingPatient.getA1CFlags(), update.getA1Cresult());
		workingPatient.totReadmissions = reAdmit(workingPatient.getReadmissions(), update.getReadmitted());
		//Update Encounters total at end.
		workingPatient.encounterTotal = workingPatient.encounterTotal + 1;
		patientsMap.put(workingPatient.getPatientNbr(), workingPatient);
	}

	/**
	 * Method called by constructor to update age.
	 * @param working
	 */
	private Integer updateAge(String age) {
		String [] ageSplit = age.split("-");
		age = ageSplit[1];
		age = age.substring(0, age.length() - 1);
		int updatedAgeCat = Integer.parseInt(age);
		updatedAgeCat = updatedAgeCat / 10;
		return updatedAgeCat;
	}

	/**
	 * This method converts gender "Male or Female" to a 0 or 1 integer
	 * @param gender
	 * @return
	 */
	private Integer updateGender(String gender) {
		gender = gender.toUpperCase();
		if (gender.charAt(0) == 'M') {
			return 0;	
		}
		else {
			return 1;
		}
	}


	/**
	 * Check for A1C elevation by looking for 8 or 7 in char[]
	 * @param update
	 */
	private Integer calcA1CResults(int current, String update) {
		String upResults = update;
		char [] A1CArray = upResults.toCharArray();

		if (A1CArray[1] == '8' || A1CArray[1] == '7' ) {
			current = current + 1;	
			return current;
		}
		return current;
	}

	/**
	 * Check for readmittance by looking for 3 in char[]
	 * @param update
	 */
	private Integer reAdmit(int current, String update) {
		String admit = update;
		char [] admitArray = admit.toCharArray();
		if (admitArray[1] == '3') {
			current = current + 1;
			return current;
		}
		return current;
	}

	/**
	 * Calculate running average given old average, new number and previous number of encounters then format to 
	 * 2 decimal places
	 * @param old
	 * @param update
	 * @param encounter
	 * @return
	 */
	private double calculateRunningAverage(double old, int update, int encounter) {
		double runTotal = old * encounter;
		runTotal = runTotal + update;
		runTotal = runTotal / (encounter + 1);
		runTotal = runTotal * 100;
		runTotal = Math.round(runTotal);
		runTotal = runTotal / 100;
		return (runTotal);
	}
}
