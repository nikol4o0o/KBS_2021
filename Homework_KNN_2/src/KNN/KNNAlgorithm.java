package KNN;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;



    public class KNNAlgorithm
    {

        private final int K;
        //Matrix from our data
        private List<List<Double>> matriX;
        //Its vector from the drugs
        private List<String> medicine;

        public KNNAlgorithm(int k) {
            this.K = Math.max(k, 1);
        }
        //Function to find the Euclidean Distance
        public static Double findEuclideanDistance(List<Double> s1, List<Double> s2) {
            int size = s1.size();
            double s = 0;
            int i = 0;

            while(i < size) {
                s += Math.pow(s1.get(i) - s2.get(i), 2);
                i++;
            }

            return Math.sqrt(s);
        }

        public int getK()
        {
            return K;
        }

        //Get fit
        public void fit(List<List<Double>> X, List<String> y) {
            this.matriX = X;
            this.medicine = y;
        }
        //Function from the functional method, which implements the k-NN algorithm
        private String predictHelperFunction(List<Double> x) {
            // Find the K nearest
            List<String> nearestN = matriX.stream()
                    .sorted(Comparator.comparingDouble(s -> findEuclideanDistance(s, x)))
                    .limit(K)
                    .map(s -> medicine.get(matriX.indexOf(s)))
                    .collect(Collectors.toList());


            // Vote, find the nearest and sort
            List<String> distinct = nearestN.stream()
                    .distinct()
                    .sorted(Comparator.comparingInt(s -> Collections.frequency(nearestN, s)))
                    .collect(Collectors.toList());

            return distinct.get(distinct.size() - 1);
        }

        //Function to return list from the drugs predicted
        public List<String> predict(List<List<Double>> X) {
            return X.stream().map(this::predictHelperFunction).collect(Collectors.toList());
        }
    }


