
public class Patient {

	/**
	 * This is the patient class which uses java's default constructor to create and store variables about each patient.
	 */
	Integer patientNbr;
	String race;
	Integer gender;
	Integer ageCat;
	String genderString;
	String ageCatString;
	Integer encounterTotal;
	double avgNumInpVisits;
	double avgNumProcedures;
	double avgNumLabProcedures;
	double avgNumMedications;
	double avgNumOutpatientVisits;
	double avgNumEmergencyVisits;
	Integer totA1CElevated = 0;
	Integer totReadmissions = 0;


	public Integer getPatientNbr() {
		return patientNbr;
	}

	public String getRace() {
		return race;
	}

	public Integer getGender() {
		return gender;
	}

	public Integer getAge() {
		return ageCat;
	}

	public double getTotalInpVisits() {
		return avgNumInpVisits;
	}

	public double getTotalLab() {
		return avgNumLabProcedures;
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

	public String getGenderString() {
		return genderString;
	}

	public String getAgeCatString() {
		return ageCatString;
	}

	/**
	 * This methods returns a double [] with all values of the patient profile for use in calculations
	 * @return
	 */
	public Double[] getProfile() {
		Double[] profile = new Double[]{(double) this.getAge(), this.getTotalLab(), this.getTotalProcedures(),
				this.getTotalOutpatientVisits(), this.getTotalInpVisits(), this.getTotalEmergencyVisits(),
				(double) this.getA1CFlags(), this.getTotalMedications(), (double) this.getGender()};
		return profile;
	}
}

