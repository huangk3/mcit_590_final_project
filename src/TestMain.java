import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class TestMain {
    public static void main(String[] args) {
        DataAnalysis test = new DataAnalysis();
        PatientDataReader testPatientLoader = new PatientDataReader();
        HashMap<Integer,Patient> patientData = testPatientLoader.inputFileReader("data/patient_grouped_sample.csv");
        System.out.println(patientData.size());
       HashMap<Integer, Double> scores = new HashMap<>();
//        Instant start = Instant.now();
        for (Integer patient : patientData.keySet()) {
            if (patient != 135) {
                scores.put(patient, test.calculateSimilarity(135, patient, patientData, "euclidean"));
            }
        }
        mapValueComparator valueComparator = new mapValueComparator(scores);
        TreeMap<Integer, Double> sortedDistance = new TreeMap<>(valueComparator);

        sortedDistance.putAll(scores);
    System.out.println("Original" + scores);
    System.out.println(("Sorted" + sortedDistance));

    System.out.println("Patient Profile 135" + Arrays.toString(patientData.get(135).getProfile()));
    System.out.println("Patient Profile 378"+ Arrays.toString(patientData.get(378).getProfile()));
//        Instant finish = Instant.now();
//        long timeElapsed = Duration.between(start, finish).toMillis();
//
//        System.out.println("Total processed:" + scores.size());
//        System.out.println("Total elapsed time: " + timeElapsed );
//
//        for (int i=0 ; i<10; i++){
//            System.out.println(scores.get(i));
//        }

//Double cosineDist = test.cosineSimilarity(patientData.get(135).getProfile(), patientData.get(378).getProfile());
//System.out.println("Cosine Dist ="+ cosineDist);
    }
}
