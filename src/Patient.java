public class Patient {

//    Integer patientNbr;
    Integer gender;
    Integer age_category;
    Double avg_num_inpatient;
    Double avg_num_procedures;
    Double avg_num_meds;
    Double avg_num_outpatient;
    Double avg_num_emergency;
    Double avg_num_lab_procedures;
    Integer tot_a1c_elevated;
    Double tot_readmissions;


    public Patient(String[] values){
//       this.patientNbr = Integer.parseInt(values[0]);
       this.avg_num_emergency = Double.parseDouble(values[1]);
       this.avg_num_outpatient = Double.parseDouble(values[4]);
       this.tot_readmissions = Double.parseDouble(values[3]);
       this.avg_num_inpatient = Double.parseDouble(values[9]);
       this.avg_num_lab_procedures = Double.parseDouble(values[5]);
       this.tot_a1c_elevated = Integer.parseInt(values[6]);
       this.avg_num_procedures = Double.parseDouble(values[7]);
       this.avg_num_meds = Double.parseDouble(values[8]);
       this.gender = Integer.parseInt(values[10]);
       this.age_category = Integer.parseInt(values[11]);

    }

//    public Integer getPatientNbr() {
//        return patientNbr;
//    }

    public  Double[] getProfile(){
        Double[] profile =  new Double[]{(double) this.getAge_category(),
                this.getAvg_num_lab_procedures(), this.getAvg_num_procedures(),
                this.getAvg_num_outpatient(), this.getAvg_num_inpatient(),
                this.getAvg_num_emergency(), (double) this.getTot_a1c_elevated(),
                this.getAvg_num_meds(), (double) this.getGender(), (double) this.getAge_category()};
        return profile;
    }

    public Integer  getGender() {
        return gender;
    }

    public Integer getAgeGrp() {
        return age_category;
    }

    public Double getAvg_num_inpatient() {
        return avg_num_inpatient;
    }

    public Double getAvg_num_procedures() {
        return avg_num_procedures;
    }

    public Integer getAge_category() {
        return age_category;
    }

    public Double getAvg_num_lab_procedures() {
        return avg_num_lab_procedures;
    }

    public Integer getTot_a1c_elevated() {
        return tot_a1c_elevated;
    }

    public Double getTot_readmissions() {
        return tot_readmissions;
    }

    public Double getAvg_num_meds() {
        return avg_num_meds;
    }

    public Double getAvg_num_outpatient() {
        return avg_num_outpatient;
    }

    public  Double getAvg_num_emergency() {
        return avg_num_emergency;
    }
}
