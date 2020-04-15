
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Patient {

	//variables from PDF and table 1.
	Integer patientNbr;
	String race;
	String gender;
	String ageCat;
	Integer encounterTotal;
	double avgNumInpVisits;
	double avgNumProcedures;
	double avgNumLabProcedures;
	double avgNumMedications;
	double avgNumOutpatientVisits;
	double avgNumEmergencyVisits;
	Integer totA1CElevated = 0;
	Integer totReadmissions = 0;


	//HashMap of patients Key is patient NBR
	HashMap<Integer, Patient> patientsMap = new HashMap<Integer, Patient>();

	/**
	 * This is the patient class constructor it will take in an ArrayList of Clinical Encounters
	 * Parse thru the data and create patient profiles and store those patient profiles in a HashMap 
	 * @return
	 */

	public Patient(ArrayList<ClinicalEncounter> clinic) {

		//Loop through encounters list and update or add patient data.
		for(ClinicalEncounter encounter : clinic) {
			if(patientsMap.containsKey(encounter.getPatientNbr())) {
				this.patientUpdate(encounter);
			}

			//Call overloaded constructor to create new patient
			else {
				Patient workingPatient = new Patient(encounter);
				patientsMap.put(workingPatient.getPatientNbr(), workingPatient);
			}
		}	
	}

	/**
	 * This is the patient class overloaded constructor it will take in an a single Clinical Encounter
	 * Parse thru the data and create patient profiles and store those patient profiles in a HashMap 
	 * @return
	 */

	public Patient(ClinicalEncounter clinic) {
		this.updateAge(clinic);
		this.patientNbr = clinic.getPatientNbr();
		this.race = clinic.getRace();
		this.gender = clinic.getGender();
		this.encounterTotal = 1;
		this.avgNumInpVisits = clinic.getNumberInpatient();
		this.avgNumProcedures = clinic.getNumProcedures();
		this.avgNumLabProcedures = clinic.getNumLabProcedures();
		this.avgNumMedications = clinic.getNumMedications();
		this.avgNumOutpatientVisits = clinic.getNumberOutpatient();
		this.avgNumEmergencyVisits = clinic.getNumberEmergency();
		this.calcA1CResults(clinic);
		this.reAdmit(clinic);
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
		workingPatient.updateAge(update);
		workingPatient.updateNumProcedure(update);
		workingPatient.updateNumLabProcedures(update);
		workingPatient.updateNumMedications(update);
		workingPatient.updateNumOutPatient(update);
		workingPatient.updateNumEmergency(update);
		workingPatient.updateNumInpatient(update);
		workingPatient.calcA1CResults(update);
		workingPatient.reAdmit(update);
		//Update Encounters total at end.
		workingPatient.encounterTotal = workingPatient.encounterTotal + 1;
	}

	/**
	 * Method called by constructor to update age.
	 * @param working
	 */
	private void updateAge(ClinicalEncounter update) {
		String age = update.getAge();
		String [] ageSplit = age.split("-");
		age = ageSplit[1];
		age = age.substring(0, age.length() - 1);
		int updatedAgeCat = Integer.parseInt(age);
		updatedAgeCat = updatedAgeCat / 10;
		this.ageCat = String.valueOf(updatedAgeCat);
	}

	/**
	 * Method called by constructor to update number of Procedures.
	 * @param working
	 */
	private void updateNumProcedure(ClinicalEncounter update) { 	
		this.avgNumProcedures = this.calculateRunningAverage(this.avgNumProcedures, update.getNumProcedures(), this.encounterTotal);
	}

	/**
	 * Update lab procedures
	 * @param update
	 */
	private void updateNumLabProcedures(ClinicalEncounter update) {
		this.avgNumLabProcedures = this.calculateRunningAverage(this.avgNumLabProcedures, update.getNumLabProcedures(), this.encounterTotal);
	}

	/**
	 * Update average number of medications
	 * @param update
	 */
	private void updateNumMedications(ClinicalEncounter update) {
		this.avgNumMedications = this.calculateRunningAverage(this.avgNumMedications, update.getNumMedications(), this.encounterTotal);
	}

	/**
	 * Update average number of outpatient visits
	 * @param update
	 */
	private void updateNumOutPatient(ClinicalEncounter update) {
		this.avgNumOutpatientVisits = this.calculateRunningAverage(this.avgNumOutpatientVisits, update.getNumberOutpatient(), this.encounterTotal); 	
	}

	/**
	 * Update average number of emergency room visits
	 * @param update
	 */
	private void updateNumEmergency(ClinicalEncounter update) {
		this.avgNumEmergencyVisits = this.calculateRunningAverage(this.avgNumEmergencyVisits, update.getNumberEmergency(), this.encounterTotal);
	}

	/**
	 * Update average number of Inpatient visits
	 * @param update
	 */
	private void updateNumInpatient(ClinicalEncounter update) {
		this.avgNumInpVisits = this.calculateRunningAverage(this.avgNumInpVisits, update.getNumberInpatient(), this.encounterTotal);
	}

	/**
	 * Check for A1C elevation by looking for 8 or 7 in char[]
	 * @param update
	 */
	private void calcA1CResults(ClinicalEncounter update) {
		String upResults = update.getA1Cresult();
		char [] A1CArray = upResults.toCharArray();

		if (A1CArray[1] == '8' || A1CArray[1] == '7' ) {
			this.totA1CElevated = this.totA1CElevated + 1;	
		}
	}

	/**
	 * Check for redmitance by looking 3 in char[]
	 * @param update
	 */
	private void reAdmit(ClinicalEncounter update) {
		String admit = update.getReadmitted();
		char [] admitArray = admit.toCharArray();
		if (admitArray[1] == '3') {
			this.totReadmissions= this.totReadmissions + 1;	
		}
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

	public Integer getPatientNbr() {
		return patientNbr;
	}

	public String getRace() {
		return race;
	}

	public String getGender() {
		return gender;
	}

	public String getAge() {
		return ageCat;
	}

	public double getTotalInpVisits() {
		return avgNumInpVisits;
	}

	public double getTotalProcedures() {
		return avgNumProcedures;
	}

	public double getTotalMedications() {
		return avgNumMedications;
	}

	public double getTotalOutpatientVisits() {
		return avgNumOutpatientVisits;
	}

	public double getTotalEmergencyVisits() {
		return avgNumEmergencyVisits;
	}

	public Integer getA1CFlags() {
		return totA1CElevated;
	}

	public Integer getReadmissions() {
		return totReadmissions;
	}
}
