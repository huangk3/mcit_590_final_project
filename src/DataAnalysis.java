import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class DataAnalysis {

    /**
     * This method provides arrayList of unique patient numbers in given encounter date
     * @param encounters ArrayList of encounter data
     * @return get list of unique patient numbers
     */
//    public ArrayList<Integer> getPatientList(ArrayList<ClinicalEncounter> encounters){
//        ArrayList<Integer> test = new ArrayList<>();
//        return test;
//    }

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
     * @param  patients Arraylist of patients for given patient
     * @param patientNumber PatientNumber
     * @return patient's utilization profile
     */
    public Double[] getPatientProfile(ArrayList<Patient> patients, Integer patientNumber){
        return patients.get(patientNumber).getProfile();
    }

    /**
     * This method will calculate the similarity between two patients' utilization profiles
     * @param indexPatient Utilization profile of second patient in the comparison
     * @param otherPatient Utilization profile of first patient in the comparison
     * @param method method to be used for similarity computation e.g. Euclidean, Cosine etc.
     * @return Similarity score
     */
    public Double calculateSimilarity(Integer indexPatient, Integer otherPatient, HashMap<Integer, Patient> patients, String method){
        Double [] indexProfile = patients.get(indexPatient).getProfile();
        Double [] otherProfile = patients.get(otherPatient).getProfile();
        if (method.equals("euclidean")){
          return euclideanDistance(indexProfile, otherProfile);
        }

        if (method.equals("cosine")){
           return  cosineSimilarity(indexProfile,otherProfile);
       }
        return null;
    }



    public Double euclideanDistance(Double[] indexProfile, Double[] otherProfile){
        return norm(elementWiseSubstraction(indexProfile,otherProfile));
    }

    public Double cosineSimilarity(Double[] indexProfile, Double[] otherProfile){

      double numer =  sumOfElementWiseMul(indexProfile, otherProfile);
      double denom = norm(indexProfile)*norm(otherProfile);

      return numer/denom;
    }

    public double norm(Double[] profile){
        double ss = 0.0;
        for(int j=0; j<profile.length; j++){
            ss+=profile[j]*profile[j];
        }
        return  Math.sqrt(ss);
    }

    public Double[] elementWiseSubstraction(Double[] indexProfile, Double[] otherProfile){
        Double[] ssElmSub = new Double[indexProfile.length];
        for (int k=0; k<indexProfile.length; k++){
            ssElmSub[k] = indexProfile[k]-otherProfile[k];
        }
        return ssElmSub;
    }

    public double sumOfElementWiseMul(Double[] indexProfile, Double[] otherProfile){
        double ssElmMul = 0.0;
        for (int k=0; k<indexProfile.length; k++){
            ssElmMul += indexProfile[k]*otherProfile[k];
        }
        return ssElmMul;
    }


    public HashMap<Integer, Double> getDistanceAllPatients(Integer indexPatient, HashMap<Integer, Patient> patientData){
        HashMap<Integer, Double> distances = new HashMap<>();
        for (Integer patientNumber : patientData.keySet()){
            if (patientNumber != indexPatient){
                distances.put(patientNumber, calculateSimilarity(indexPatient, patientNumber, patientData, "euclidean") );
            }
                   }
        return distances;
    }

    /**
     * Return the list of patient numbers which are within a certain threshold of similarity score compared
     * to a given member
     * @param indexPatient Patient profile to compare to
     * @param similarityThreshold Minimum similarity score threshold
     * @return List of patients within a certain threshold of similarity
     */
    public ArrayList<Integer> getNeighbours(Patient indexPatient, HashMap<Integer, Double> distances,
                                            double similarityThreshold){
        ArrayList<Integer> neigbours = new ArrayList<>();
        mapValueComparator compareVals = new mapValueComparator(distances);
        TreeMap<Integer, Double> sorted = new TreeMap<>(compareVals);


        for (Integer patientNumber : sorted.keySet()){
            if (sorted.get(patientNumber)<=similarityThreshold){
                neigbours.add(patientNumber);
            }else{
                break;
            }
        }
        return  neigbours;
    }

}