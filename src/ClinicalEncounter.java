public class ClinicalEncounter {
    Integer encounterId;
    Integer patientNbr;
    String race;
    String gender;
    String age;
    Integer weight;
    Integer admissionTypeDd;
    Integer dischargeDispositionId;
    Integer admissionSourceId;
    Integer timeInHospital;
    String payerCode;
    String medicalSpecialty;
    Integer numLabProcedures;
    Integer numProcedures;
    Integer numMedications;
    Integer numberOutpatient;
    Integer numberEmergency;
    Integer numberInpatient;
    String diag1;
    String diag2;
    String diag3;
    Integer numberDiagnoses;
    String maxGluSerum;
    String A1Cresult;
    String metformin;
    String repaglinide;
    String nateglinide;
    String chlorpropamide;
    String glimepiride;
    String acetohexamide;
    String glipizide;
    String glyburide;
    String tolbutamide;
    String pioglitazone;
    String rosiglitazone;
    String acarbose;
    String miglitol;
    String troglitazone;
    String tolazamide;
    String examide;
    String citoglipton;
    String insulin;
    String glyburideMetformin;
    String glipizideMetformin;
    String glimepiridePioglitazone;
    String metforminRosiglitazone;
    String metforminPioglitazone;
    String change;
    String diabetesMed;
    String readmitted;

    /**
     * Constructor for the clinicalEcounter Class
     * @param values string array created by dataReader
     */
    public ClinicalEncounter(String[] values) {
        this.encounterId = Integer.parseInt(values[0]);
        this.patientNbr = Integer.parseInt(values[1]);
        this.race = values[2];
        this.gender = values[3];
        this.age = values[4];
        this.weight = Integer.parseInt(values[5]);
        this.admissionTypeDd = Integer.parseInt(values[6]);
        this.dischargeDispositionId = Integer.parseInt(values[7]);
        this.admissionSourceId = Integer.parseInt(values[8]);
        this.timeInHospital = Integer.parseInt(values[9]);
        this.payerCode = values[10];
        this.medicalSpecialty = values[11];
        this.numLabProcedures = Integer.parseInt(values[12]);
        this.numProcedures = Integer.parseInt(values[13]);
        this.numMedications = Integer.parseInt(values[14]);
        this.numberOutpatient = Integer.parseInt(values[15]);
        this.numberEmergency = Integer.parseInt(values[16]);
        this.numberInpatient = Integer.parseInt(values[17]);
        this.diag1 = values[18];
        this.diag2 = values[19];
        this.diag3 = values[20];
        this.numberDiagnoses = Integer.parseInt(values[21]);
        this.maxGluSerum = values[22];
        this.A1Cresult = values[23];
        this.metformin = values[24];
        this.repaglinide = values[25];
        this.nateglinide = values[26];
        this.chlorpropamide = values[27];
        this.glimepiride = values[28];
        this.acetohexamide = values[29];
        this.glipizide = values[30];
        this.glyburide = values[31];
        this.tolbutamide = values[32];
        this.pioglitazone = values[33];
        this.rosiglitazone = values[34];
        this.acarbose = values[35];
        this.miglitol = values[36];
        this.troglitazone = values[37];
        this.tolazamide = values[38];
        this.examide = values[39];
        this.citoglipton = values[40];
        this.insulin = values[41];
        this.glyburideMetformin = values[42];
        this.glipizideMetformin = values[43];
        this.glimepiridePioglitazone = values[44];
        this.metforminRosiglitazone = values[45];
        this.metforminPioglitazone = values[46];
        this.change = values[47];
        this.diabetesMed = values[48];
        this.readmitted = values[49];

    }


    public Integer getEncounterId() {
        return encounterId;
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
        return age;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getAdmissionTypeDd() {
        return admissionTypeDd;
    }

    public Integer getDischargeDispositionId() {
        return dischargeDispositionId;
    }

    public Integer getAdmissionSourceId() {
        return admissionSourceId;
    }

    public Integer getTimeInHospital() {
        return timeInHospital;
    }

    public String getPayerCode() {
        return payerCode;
    }

    public String getMedicalSpecialty() {
        return medicalSpecialty;
    }

    public Integer getNumLabProcedures() {
        return numLabProcedures;
    }

    public Integer getNumProcedures() {
        return numProcedures;
    }

    public Integer getNumMedications() {
        return numMedications;
    }

    public Integer getNumberOutpatient() {
        return numberOutpatient;
    }

    public Integer getNumberEmergency() {
        return numberEmergency;
    }

    public Integer getNumberInpatient() {
        return numberInpatient;
    }

    public String getDiag1() {
        return diag1;
    }

    public String getDiag2() {
        return diag2;
    }

    public String getDiag3() {
        return diag3;
    }

    public Integer getNumberDiagnoses() {
        return numberDiagnoses;
    }

    public String getMaxGluSerum() {
        return maxGluSerum;
    }

    public String getA1Cresult() {
        return A1Cresult;
    }

    public String getMetformin() {
        return metformin;
    }

    public String getRepaglinide() {
        return repaglinide;
    }

    public String getNateglinide() {
        return nateglinide;
    }

    public String getChlorpropamide() {
        return chlorpropamide;
    }

    public String getGlimepiride() {
        return glimepiride;
    }

    public String getAcetohexamide() {
        return acetohexamide;
    }

    public String getGlipizide() {
        return glipizide;
    }

    public String getGlyburide() {
        return glyburide;
    }

    public String getTolbutamide() {
        return tolbutamide;
    }

    public String getPioglitazone() {
        return pioglitazone;
    }

    public String getRosiglitazone() {
        return rosiglitazone;
    }

    public String getAcarbose() {
        return acarbose;
    }

    public String getMiglitol() {
        return miglitol;
    }

    public String getTroglitazone() {
        return troglitazone;
    }

    public String getTolazamide() {
        return tolazamide;
    }

    public String getExamide() {
        return examide;
    }

    public String getCitoglipton() {
        return citoglipton;
    }

    public String getInsulin() {
        return insulin;
    }

    public String getGlyburideMetformin() {
        return glyburideMetformin;
    }

    public String getGlipizideMetformin() {
        return glipizideMetformin;
    }

    public String getGlimepiridePioglitazone() {
        return glimepiridePioglitazone;
    }

    public String getMetforminRosiglitazone() {
        return metforminRosiglitazone;
    }

    public String getMetforminPioglitazone() {
        return metforminPioglitazone;
    }

    public String getChange() {
        return change;
    }

    public String getDiabetesMed() {
        return diabetesMed;
    }

    public String getReadmitted() {
        return readmitted;
    }
}
