import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class DataAnalysis {


    /**
     * This method will calculate the similarity between two patients' utilization profiles
     * @param indexPatient Utilization profile of second patient in the comparison
     * @param otherPatient Utilization profile of first patient in the comparison
     * @param range Array of difference between minimum and maximum values for each component in patient profile
     * @param method method to be used for similarity computation e.g. Euclidean, Cosine etc.
     * @return Similarity score
     */
    public Double calculateSimilarity(Integer indexPatient, Integer otherPatient,
                                      HashMap<Integer, Patient> patients,
                                      Double[] range,
                                      String method){
        Double [] indexProfile = patients.get(indexPatient).getProfile();
        Double [] otherProfile = patients.get(otherPatient).getProfile();

        if (method.equals("euclidean")){
          return euclideanDistance(indexProfile, otherProfile,range);
        }

        if (method.equals("cosine")){
           return  cosineSimilarity(indexProfile,otherProfile);
       }
        return null;
    }

    /**
     * This method provides euclidean distance between two utilization profile
     * @param indexProfile Utilization profile of index patient
     * @param otherProfile Utilization profile of other patient
     * @param range Array of difference between minimum and maximum values for each component in patient profile
     * @return euclidean distance
     */

    public Double euclideanDistance(Double[] indexProfile, Double[] otherProfile, Double[] range){
        Double [] diff = elementWiseSubstraction(indexProfile,otherProfile);
        return stdNorm(diff,range);
    }

    /**
     * This method provides cosine similarity between two patients
     * @param indexProfile Utilization profile of index patient
     * @param otherProfile Utilization profile of other patient
     * @return cosine similarity
     */
    public Double cosineSimilarity(Double[] indexProfile, Double[] otherProfile){
      double numer =  sumOfElementWiseMul(indexProfile, otherProfile);
      double denom = norm(indexProfile)*norm(otherProfile);
      return numer/denom;
    }



    /**
     * This method provides array of minimum and maximum values of each element in patient profile in entire data
     * @param patients HashMap with patient number as key and utilization profile as value
     * @return 2D Array of minimum and maximum values
     */
   public Double[][] minMaxProfileVals(HashMap<Integer, Patient> patients){
        Double [][] minMaxVals = new Double[2][9];
//        Double [] maxVals = new Double[9];
        for (Integer patNum : patients.keySet()){
            Double[] profile = patients.get(patNum).getProfile();
            for (int i =0; i<profile.length; i++){
                if(minMaxVals[0][i]==null){
                    minMaxVals[0][i]=profile[i];
                }if(minMaxVals[1][i]==null){
                    minMaxVals[1][i]=profile[i];
                }
                if (profile[i]<=minMaxVals[0][i]){
                    minMaxVals[0][i]=profile[i];
                }
                if (profile[i]>=minMaxVals[1][i]){
                    minMaxVals[1][i]=profile[i];
                }
            }
        }
        return minMaxVals;
   }

    /**
     * Get the average of values in the profile
     * @param profile patient utilization profile
     * @return average value of values in utilization profile
     */
    public double mean(Double[] profile){
        double tot=0.0;
        for (double val : profile){
            tot+=val;
        }
        return tot/profile.length;
    }

    /**
     * Provides standard deviation for given array of doubles
     * @param profile patient utilization profile
     * @return standard deviation
     */
    public double stdDev(Double[] profile){
        double meanVal = mean(profile);
        double variance = 0.0;
        for (double val : profile){
            variance+=Math.pow(val-meanVal,2);
        }
        return Math.sqrt(variance/profile.length);
    }

    /**
     * Provide normalized values for given array of doubles/floats
     * @param profile patient utilization profile
     * @return array of normalized values
     */

    public Double[] normalize(Double[] profile){
        double meanVal = mean(profile);
        double sd = stdDev(profile);
        Double[] noralizedArray = new Double[profile.length];

        for(int i=0; i<profile.length; i++){
            noralizedArray[i]= (profile[i]-meanVal)/sd;
        }
        return noralizedArray;
    }

    /**
     * This helper method provides squared values of given array
     * @param vector array of doubles
     * @return array of squared values
     */
    public  Double[] square(Double[] vector){
        Double[] squares = new Double[vector.length];
        for (int j=0; j<vector.length; j++){
            squares[j] = vector[j]*vector[j];
        }
        return squares;
    }

    /**
     * This method provides a standardized norm for given array of differences
     * @param vector array of differences between two patient profiles
     * @param range array of differences between maximum and minimum values for each element in patient profile
     * @return standardized norm
     */

    public double stdNorm(Double[] vector, Double[] range){
        double ss = 0.0;
        Double[] rangeSqr = square(range);
        Double[] squares = square(vector);

        for(int k=0; k<vector.length; k++){
            ss+=squares[k]/rangeSqr[k];
        }
        return  Math.sqrt(ss)/Math.sqrt(vector.length);
    }

    /**
     * This method provide norm of given array
     * @param  vector input array of doubles
     * @return norm of given vector
     */

    public double norm(Double[] vector){
        double ss = 0.0;
        for(int j=0; j<vector.length; j++){
            ss+=vector[j]*vector[j];
        }
        return  Math.sqrt(ss);
    }

    /**
     * This helper method provides element wise differences between two arrays
     * @param indexProfile first input array
     * @param otherProfile second input array
     * @return array of difference between each element of input arrays
     */
    public Double[] elementWiseSubstraction(Double[] indexProfile, Double[] otherProfile){
        Double[] ssElmSub = new Double[indexProfile.length];
        for (int k=0; k<indexProfile.length; k++){
            ssElmSub[k] = indexProfile[k]-otherProfile[k];
        }
        return ssElmSub;
    }

    /**
     * This helper method provides sum of element wise multiplication of each element in input arrays
     * @param indexProfile first input array
     * @param otherProfile second input array
     * @return sum of element wise multiplication of each element of input arrays
     */
    public double sumOfElementWiseMul(Double[] indexProfile, Double[] otherProfile){
        double ssElmMul = 0.0;
        for (int k=0; k<indexProfile.length; k++){
            ssElmMul += indexProfile[k]*otherProfile[k];
        }
        return ssElmMul;
    }

    /**
     * This wrapper method calculates distance between index patient and all other patients in the data
     * @param indexPatient patient number of index patient
     * @param patientData HashMap of patient numbers and their utilization profiles
     * @param method distance calculation methods - {"euclidean", "cosine"}
     * @return HashMap of patient number and their distance from index patient
     */

    public HashMap<Integer, Double> getDistanceAllPatients(Integer indexPatient,
                                                           HashMap<Integer, Patient> patientData,
                                                           String method){
        HashMap<Integer, Double> distances = new HashMap<>();
        Double[][] minMaxVals = minMaxProfileVals(patientData);
        Double[] range = elementWiseSubstraction(minMaxVals[0], minMaxVals[1]);
        for (Integer patientNumber : patientData.keySet()){
        distances.put(patientNumber, calculateSimilarity(indexPatient, patientNumber, patientData, range, method) );
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
            if (distances.get(patientNumber)<=similarityThreshold){
                neighbors.add(patientNumber);
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