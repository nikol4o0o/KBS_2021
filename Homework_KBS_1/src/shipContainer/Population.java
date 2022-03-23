package shipContainer;


import java.util.*;
import java.util.stream.Collectors;

public class Population {

    private ArrayList<Genome> genomes;
    private ArrayList<Container> containers;
    private int weightLimit;

    public Population(int numberOfIndividuals, ArrayList<Container> containers, int weightLimit) {
        setGenomes(null);
        setContainers(containers);
        setWeightLimit(weightLimit);

        while (numberOfIndividuals > 0) {
            genomes.add(new Genome(containers.size()));
            --numberOfIndividuals;
        }
    }

    public ArrayList<Genome> getGenomes() {
        return genomes;
    }

    public void setGenomes(List<Genome> genomes) {
        this.genomes = new ArrayList<>();

        if (genomes == null) {
            return;
        }

        this.genomes.addAll(genomes);
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        this.containers = containers;
    }

    public int getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    public void selection(int cullingLimit) {
        genomes.sort(Comparator.comparingInt(o -> o.haveFitness(containers, weightLimit) * -1));

        List<Genome> l = genomes.subList(0, cullingLimit);

        setGenomes(null);

        genomes.addAll(l);
    }

    public void nextGeneration(int cullingLimit) {
        if (cullingLimit > genomes.size()) {
            System.out.println("ERROR: Culling limit must be smaller than to the number of individuals");
            return;
        }

        List<Genome> nextGenomes = new ArrayList<>();
        selection(cullingLimit);

        Random r = new Random();

        while (cullingLimit * 2 > 0) {
            int p1Idx = 0;
            int p2Idx = 0;

            while (p1Idx == p2Idx) {
                int numIndividuals = genomes.size();
                p1Idx = r.nextInt(numIndividuals);
                p2Idx = r.nextInt(numIndividuals);
            }

            nextGenomes.addAll(Arrays.asList(genomes.get(p1Idx).singlePointCrossover(genomes.get(p2Idx))));

            --cullingLimit;
        }

        setGenomes(nextGenomes);
    }

    public Genome getCurrentBest() {
        genomes.sort(Comparator.comparingInt(o -> o.haveFitness(containers, weightLimit) * -1));
        return genomes.get(0);
    }

   public boolean stopWhenFound(Integer bestFitness) {
        if (bestFitness != null) {
            List<Genome> bestGenes = genomes.stream().filter((g) -> g.haveFitness(containers, weightLimit) == bestFitness).collect(Collectors.toList());

            if (bestGenes.size() != 0) {
                return true;
            }
        }

        int currentFitness = genomes.get(0).haveFitness(containers, weightLimit);
        for (int i = 1; i < genomes.size(); i++) {
            if (currentFitness != genomes.get(i).haveFitness(containers, weightLimit)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n",
                genomes,
                Arrays.toString(genomes.stream().map((g) -> g.haveFitness(containers, weightLimit)).toArray())
        );
    }
}