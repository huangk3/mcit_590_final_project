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


    public HashMap<Integer, Double> getDistanceAllPatients(Integer indexPatient, HashMap<Integer, Patient> patientData, String method){
        HashMap<Integer, Double> distances = new HashMap<>();
        for (Integer patientNumber : patientData.keySet()){
        distances.put(patientNumber, calculateSimilarity(indexPatient, patientNumber, patientData, method) );
                   }
        return distances;
    }

    /**
     * Return the list of patient numbers which are within a certain threshold of similarity score compared
     * to a given member
     * @param similarityThreshold Minimum similarity score threshold
     * @return List of patients within a certain threshold of similarity
     */
    public ArrayList<Integer> getNeighbours(HashMap<Integer, Double> distances,
                                            double similarityThreshold){
        ArrayList<Integer> neighbors = new ArrayList<>();
        mapValueComparator compareVals = new mapValueComparator(distances);
        TreeMap<Integer, Double> sorted = new TreeMap<>(compareVals);
        sorted.putAll(distances);

        for (Integer patientNumber : sorted.keySet()){
            if (sorted.get(patientNumber)<=similarityThreshold){
                neighbors.add(patientNumber);
            }else{
                break;
            }
        }
        return  neighbors;
    }

    /**
     * This method get the patient profile data for front end display
     * @param patients Input is HashMap of patient identifier and patient instances
     * @return Arraylist of string arrays with patient profile information
     */
    public  ArrayList<String[]> getPatientForDisplay(HashMap<Integer,Patient> patients){
        ArrayList<String[]> patientForDisplay = new ArrayList<>();
      for (Integer patientNum : patients.keySet()){
          Patient curP = patients.get(patientNum);
          String[] row = {curP.getPatientNbr().toString(), curP.getRace(), curP.getGenderString(),
                  curP.getAgeCatString(), Double.toString(curP.getTotalProcedures()),
                  Double.toString(curP.getTotalMedications()), Double.toString(curP.getTotalLab()),
                  Double.toString(curP.getTotalOutpatientVisits()), Double.toString(curP.getTotalInpVisits()),
                  Double.toString(curP.getTotalEmergencyVisits())};
          patientForDisplay.add(row);
      }
      return patientForDisplay;
    }

    /**
     * Overloaded method to return the list of patient profiles and distances
     * @param patients HashMap of patient identifier and patient profile
     * @param distances HashMap of patient identifier and similarity metric value
     * @param neighbors List of patients/neighbors satisfying criteria
     * @return Arraylist of String arrays with patient information and distance measure
     */
    public  ArrayList<String[]> getPatientForDisplay(HashMap<Integer,Patient> patients,
                                                       HashMap<Integer, Double> distances,
                                                       ArrayList<Integer> neighbors){
        ArrayList<String[]> patientForDisplay = new ArrayList<>();
        for (Integer patientNum : neighbors){
            Patient curP = patients.get(patientNum);
            String[] row = {curP.getPatientNbr().toString(), curP.getRace(), curP.getGenderString(),
                    curP.getAgeCatString(), Double.toString(curP.getTotalProcedures()),
                    Double.toString(curP.getTotalMedications()), Double.toString(curP.getTotalLab()),
                    Double.toString(curP.getTotalOutpatientVisits()), Double.toString(curP.getTotalInpVisits()),
                    Double.toString(curP.getTotalEmergencyVisits()), Double.toString(distances.get(patientNum))};
            patientForDisplay.add(row);
        }
        return patientForDisplay;
    }

    /**
     * Get the distribution of race among patient and its neighbours
     * @param neighbors list of patient numbers
     * @param patients Hashmap of patient identifier and patient profile
     * @return HashMap of race and proportion of patient with that race
     */
    public HashMap<String, Double>  getRaceDistribution(ArrayList<Integer> neighbors,
                                                        HashMap<Integer, Patient> patients,
                                                        String grouper){
        HashMap<String, Double> distByGrp = new HashMap<>();
        for (Integer patientNum : neighbors){
            String grp = "";
            if (grouper.equals("race")){
                 grp = patients.get(patientNum).getRace();
            }else if (grouper.equals("gender")){
                 grp = patients.get(patientNum).getGenderString();
            }

            if (distByGrp.containsKey(grp)){
                distByGrp.put(grp, distByGrp.get(grp)+1.0);
            }else{
                distByGrp.put(grp,1.0);
            }
        }

        for (String race: distByGrp.keySet()){
            distByGrp.put(race,(distByGrp.get(race)/neighbors.size())*100);
        }
        return distByGrp;
    }


    /**
     * This method provides data needed for the scatter plot on front-end
     * @param xVar X axis variable chosen by user
     * @param yVar Y axis variable chosen by user
     * @param neighborProfile Arraylist of Strings arrays which contain neighbor profiles and distance measure
     * @return Array list of String arrays with value for X axis variable, value for Y axis variable , distance
     */
    public ArrayList<String[]> getScatterPlotData(String xVar, String yVar,
                                                  ArrayList<String[]> neighborProfile){
        ArrayList<String[]> scatterOutData = new ArrayList<>();
        HashMap<String, Integer> varIndexMap = new HashMap<>();
        varIndexMap.put("Race",1);
        varIndexMap.put("Gender",2);
        varIndexMap.put("Age Category",3);
        varIndexMap.put("Total Procedures",4);
        varIndexMap.put("Total Medications",5);
        varIndexMap.put("Total Lab",6);
        varIndexMap.put("Total Outpatient visits",7);
        varIndexMap.put("Total Inpatient visits",8);
        varIndexMap.put("Total Emergency room visits",9);

        for(String[] profile:neighborProfile){
            String[] scatterData = {profile[varIndexMap.get(xVar)], profile[varIndexMap.get(yVar)], profile[10]};
            scatterOutData.add(scatterData);
        }

    return scatterOutData;

    }

}