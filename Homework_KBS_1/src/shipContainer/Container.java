package shipContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Container
{

    private final String name;
    private final int value;
    private final int weight;

    public Container()
    {
        this.name = "";
        this.value = 1;
        this.weight = 1;
    }

    public Container(String name, int value, int weight) {
        this.name = name;
        this.value = value;
        this.weight = weight;

    }

    public static ArrayList<Container> getContainersFromDirectPath(String fileName)
    {
        File file = new File(fileName);
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return new ArrayList<>(1);
        }
        ArrayList<Container> containers = new ArrayList<>(1);
        while(scanner.hasNextLine())
        {
            String[] stringBuffer = scanner.nextLine().split("#");
            String nameBuffer = stringBuffer[0];
            int valueBuffer = Integer.parseInt(stringBuffer[1]);
            int weightBuffer = Integer.parseInt(stringBuffer[2]);
            Container newContainer = new Container(nameBuffer, valueBuffer, weightBuffer);
            containers.add(newContainer);
        }

        return containers;
    }

    public static ArrayList<Container> getContainersFromFile()
    {
        File file = new File("/Users/nikolakirilov/IdeaProjects/Homework_KBS_1/file.txt");
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return new ArrayList<>(1);
        }
        ArrayList<Container> containers = new ArrayList<>(1);
        while(scanner.hasNextLine())
        {
            String[] stringBuffer = scanner.nextLine().split(" ");
            String nameBuffer = stringBuffer[0];
            int valueBuffer = Integer.parseInt(stringBuffer[1]);
            int weightBuffer = Integer.parseInt(stringBuffer[2]);
            Container newContainer = new Container(nameBuffer, valueBuffer, weightBuffer);
            containers.add(newContainer);
        }

        return containers;
    }


    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        //for testing purposes only
        Container container = new Container();
        ArrayList<Container> newContainer = new ArrayList<>();
        ArrayList<Container> newContainer1 = new ArrayList<>();
       newContainer = container.getContainersFromFile();
       newContainer1 = container.getContainersFromDirectPath("/Users/nikolakirilov/IdeaProjects/Homework_KBS_1/file2.txt");

        System.out.println("Succesh");
    }
}
