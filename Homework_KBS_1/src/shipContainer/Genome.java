package shipContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genome {

    private static final Random RANDOM = new Random();

    private ArrayList<Integer> zeroOneList;

    public Genome(int length) {
        this.zeroOneList = new ArrayList<>();
        while (length > 0) {
            if (RANDOM.nextBoolean()) {
                zeroOneList.add(1);
            } else {
                zeroOneList.add(0);
            }
            length--;
        }
    }

    // Only for testing
    public Genome(ArrayList<Integer> zeroOneList) {
        this.zeroOneList = zeroOneList;
    }

    public Genome(List<Integer> dnaP1, List<Integer> dnaP2) {
        // this is used for the single point crossover
        this.zeroOneList = new ArrayList<>();
        this.addGenomeData(dnaP1);
        this.addGenomeData(dnaP2);
    }

    public int haveFitness(ArrayList<Container> containers, int weightLimit) {
        if (containers.size() != zeroOneList.size()) {
            throw new IllegalArgumentException("Containers and zeroOneList should be with the same lenght!");
        }

        int value = 0;
        int weight = 0;

        for (int i = 0; i < containers.size(); i++) {
            if (zeroOneList.get(i) == 1) {
                weight += containers.get(i).getWeight();
                value += containers.get(i).getValue();

                if (weight > weightLimit) {
                    return 0;
                }
            }
        }

        return value;
    }

    public Genome[] singlePointCrossover(Genome other) {
        if (other == null) {
            throw new IllegalArgumentException("The other genome cannot be null");
        }

        Genome[] resultCrossover = new Genome[] { this, other };
        int crossOverIndex = RANDOM.nextInt(this.zeroOneList.size());

        resultCrossover[0] = new Genome(this.zeroOneList.subList(0, crossOverIndex), other.zeroOneList.subList(crossOverIndex, other.zeroOneList.size()));
        resultCrossover[1] = new Genome(other.zeroOneList.subList(0, crossOverIndex), this.zeroOneList.subList(crossOverIndex, this.zeroOneList.size()));

        return resultCrossover;
    }

    public void mutate() {
        if (RANDOM.nextInt(100) > 90) {
            return;
        }

        for (int i = 0; i < zeroOneList.size(); i++) {
            if (RANDOM.nextBoolean()) {
                if (zeroOneList.get(i) == 0) {
                    zeroOneList.set(i, 1);
                } else {
                    zeroOneList.set(i, 0);
                }
            }
        }
    }

    private void addGenomeData(List<Integer> d) {
        this.zeroOneList.addAll(d);
    }

    @Override
    public String toString() {
        return zeroOneList.toString();
    }
}

