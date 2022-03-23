package shipContainer;

import java.util.ArrayList;

public class Main {

    public static void testProblem1(int numberOfInidividuals, int e) {
        int cullingLimit = numberOfInidividuals / 2;

        ArrayList<Container> containers = Container.getContainersFromDirectPath("/Users/nikolakirilov/IdeaProjects/Homework_KBS_1/file2.txt");
        int bestValue = 2200;
        int weightLimit = 8813;

        Population p = new Population(numberOfInidividuals, containers, weightLimit);

        for (int i = 0; i < e; i++) {
            System.out.println(p);
            p.nextGeneration(cullingLimit);

            if(p.stopWhenFound(bestValue)) {
                System.out.printf("\n\n\nEpochs needed: %d\n", i);
                System.out.println("Generation:");
                System.out.println(p);
                System.out.printf("Answer: %s\n", p.getCurrentBest());
                System.out.printf("[ %s ] \n",String.join(",", containers.toString()));

                return;
            }
        }

        System.out.printf("Value found: %s", p.getCurrentBest());
        System.out.printf("Couldn't reach an optimal value for %d epochs", e);
        System.out.println("Last generation:");
        System.out.println(p);
    }
    public static void testProblemTwo(int numberOfInidividuals, int epochs) {
        int cullingLimit = numberOfInidividuals / 2;

        ArrayList<Container> containers = Container.getContainersFromDirectPath("/Users/nikolakirilov/IdeaProjects/Homework_KBS_1/file.txt");
        int bestValue = 2200;
        int weightLimit = 8813;

        Population p = new Population(numberOfInidividuals, containers, weightLimit);

        for (int i = 0; i < epochs; i++) {
            System.out.println(p);
            p.nextGeneration(cullingLimit);

            if(p.stopWhenFound(bestValue)) {
                System.out.printf("\n\n\nEpochs needed: %d\n", i);
                System.out.println("Generation:");
                System.out.println(p);
                System.out.printf("Answer: %s\n", p.getCurrentBest());
                System.out.printf("[ %s ] \n",String.join(",", containers.toString()));

                return;
            }
        }

        System.out.printf("Value found: %s", p.getCurrentBest());
        System.out.printf("Couldn't reach an optimal value for %d epochs", epochs);
        System.out.println("Last generation:");
        System.out.println(p);
    }




    public static void main(String[] args) {
    //only for testing purposes
        System.out.println("Test 1");
        testProblem1(15, 15); // should find only 1s
        System.out.println("Test 1");
        System.out.println("Test 2");
        testProblemTwo(20,20);
        System.out.println("Test 2");
    }

}
