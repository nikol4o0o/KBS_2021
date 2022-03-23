package KNN;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final String DELIMITER = ",";

    private static final Map<String, Double> genderBinaryEncoder = new HashMap<>() {{
        put("F", 1.0);
        put("M", 0.0);
    }};

    private static final Map<String, Integer> bpOneHotEncoder = new HashMap<>() {{
        put("HIGH", 2);
        put("NORMAL", 1);
        put("LOW", 0);
    }};

    private static final Map<String, Double> cholesterolBinaryEncoder = new HashMap<>() {{
        put("HIGH", 1.0);
        put("NORMAL", 0.0);
    }};

    public static void trainTestSplit(List<List<Double>> samples,
                                      List<String> medicine,
                                      List<List<Double>> X_train,
                                      List<List<Double>> X_test,
                                      List<String> y_train,
                                      List<String> y_test
    ) {
        int size = samples.size();
        int limit = (int) Math.round(size * 0.8);

        List<Integer> range = IntStream.range(0, size).boxed().collect(Collectors.toList());
        Collections.shuffle(range);

        for (int i = 0; i <= limit; i++) {
            X_train.add( samples.get(range.get(i)) );
            y_train.add( medicine.get(range.get(i)) );
        }

        for (int i = limit + 1; i < size; i++) {
            X_test.add( samples.get(range.get(i)) );
            y_test.add( medicine.get(range.get(i)) );
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<List<String>> X = new ArrayList<>();
        List<String> columns = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("drug45.csv"))) {
            String line;
            columns = Collections.singletonList(br.readLine());

            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMITER);
                X.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(columns);
        System.out.println(X);

        List<Double> years = X.stream()
                .map(s -> Double.parseDouble(s.get(0)))
                .collect(Collectors.toList());

        List<Double> sex = X.stream()
                .map(s -> genderBinaryEncoder.get(s.get(1)))
                .collect(Collectors.toList());

        List<List<Double>> bps = X.stream()
                .map(s -> {
                    List<Double> zeros = new ArrayList<>(Collections.nCopies(3, 0.0));
                    zeros.set(bpOneHotEncoder.get(s.get(2)), 1.0);
                    return zeros;
                })
                .collect(Collectors.toList());

        List<Double> cholesterol = X.stream()
                .map(s -> cholesterolBinaryEncoder.get(s.get(3)))
                .collect(Collectors.toList());

        List<Double> NatoK = X.stream()
                .map(s -> Double.parseDouble(s.get(4)))
                .collect(Collectors.toList());

        List<String> medicine = X.stream()
                .map(s -> s.get(5))
                .collect(Collectors.toList());



        int size = bps.size();
        for (int i = 0; i < size; i++) {
            List<Double> d = bps.get(i);

            d.add(years.get(i));
            d.add(sex.get(i));
            d.add(cholesterol.get(i));
            d.add(NatoK.get(i));

            bps.set(i, d);
        }

        System.out.println(bps);

        List<List<Double>> matriX_train = new ArrayList<>();
        List<List<Double>> matriX_test = new ArrayList<>();

        List<String> medicine_train = new ArrayList<>();
        List<String> medicine_test = new ArrayList<>();

        trainTestSplit(bps, medicine, matriX_train, matriX_test, medicine_train, medicine_test);

        KNNAlgorithm model = new KNNAlgorithm(1);
        model.fit(matriX_train, medicine_train);

        List<String> medicine_pred = model.predict(matriX_test);

        int correct = 0;
        size = medicine_pred.size();
        int i = 0;

        while(i < size) {
            if(medicine_pred.get(i).equals(medicine_test.get(i))) {
                ++correct;

            }
            i++;
        }
        System.out.printf("K e: %d\n", model.getK());
        System.out.printf("Тествани екземпляри: %s\n", matriX_test);
        System.out.printf("Предсказани: %s\n", medicine_pred);
        System.out.printf("Взети за тестване:%s\n", medicine_test);

        System.out.printf("Познат брой: %d\n",correct);
        System.out.printf("Тестван брой %d\n",size);
        System.out.printf("Accuracy(Познат брой/Общ брой тествани): %f", (double) correct / (double) size);
    }
}
