import java.util.ArrayList;

public class DataAnalysis {

    /**
     * This method provides arrayList of unique patient numbers in given encounter date
     * @param encounters ArrayList of encounter data
     * @return get list of unique patient numbers
     */
    public ArrayList<Integer> getPatientList(ArrayList<ClinicalEncounter> encounters){
        ArrayList<Integer> test = new ArrayList<>();
        return test;
    }

    /**
     *This method returns encounters for given patient
     * @param encounters Array list of all encounter
     * @param patientNumber PatientNumber to filter out the encounters
     * @return ArrayList of selected encounters
     */
    public ArrayList<ClinicalEncounter> selectEncounters(ArrayList<ClinicalEncounter> encounters, Integer patientNumber){
    ArrayList<ClinicalEncounter> test2 = new ArrayList<>() ;
    return  test2;
    }

    /**
     * This method output given patient's utilization profile
     * @param encounters Arraylist of encounters for given patient
     * @param patientNumber PatientNumber
     * @return patient's utilization profile
     */
    public Patient getPatientProfile(ArrayList<ClinicalEncounter> encounters, Integer patientNumber){
        Patient test3 = new Patient();
        return test3;
    }

    /**
     * This method will calculate the similarity between two patients' utilization profiles
     * @param patientA Utilization profile of second patient in the comparison
     * @param patientB Utilization profile of first patient in the comparison
     * @param method method to be used for similarity computation e.g. Euclidean, Cosine etc.
     * @return Similarity score
     */
    public Double calculateSimilarity(Patient patientA, Patient patientB, String method){
        return 0.0;
    }

    /**
     * Return the list of patient numbers which are within a certain threshold of similarity score compared
     * to a given member
     * @param patientZero Patient profile to compare to
     * @param similarityThreshold Minimum similarity score threshold
     * @return List of patients within a certain threshold of similarity
     */
    public ArrayList<Integer> getNeighbours(Patient patientZero, double similarityThreshold){
        ArrayList<Integer> test4 = new ArrayList<>();
        return  test4;
    }

}