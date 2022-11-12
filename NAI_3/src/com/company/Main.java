package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        menu();
    }

    public static void menu() throws IOException{
        System.out.println("1.Run");
        System.out.println("2.Input your own text for classification");
        System.out.println("3.Exit");
        System.out.println("User choice: ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice){
            case 1 -> {
                String path = "trains";
                ArrayList<Perceptron> neurons = scanTrain(createNeurons(path));
                File directory = new File("resources/test");
                File[] files = directory.listFiles();
                for (int i = 0; i < neurons.size(); i++) {
                    ArrayList<Integer> testArr = new ArrayList<>();
                    Parser.Parse((files[i]), testArr);
                    Vector<Double> testVector = new Vector<>(26);
                    Perceptron.fillVector(testVector, testArr);
                    Map<Double, String> tree = new TreeMap<>();
                    for (int j = 0; j < neurons.size(); j++) {
                        tree.put(test(testVector, neurons.get(j).getWeight(), neurons.get(j).getTheta()), neurons.get(j).getLanguageName());
                    }
                    System.out.println("For file " + files[i].getName());
                    System.out.println(Perceptron.resultLanguage(tree));
                }
                menu();
            }
            case 2 -> {
                String path = "trains";
                ArrayList<Perceptron> neurons = scanTrain(createNeurons(path));
                String pathToTest = "resources/test/TestUser.txt";
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                OutputStream outputStream = new FileOutputStream(pathToTest);
                System.out.println("Enter your text: ");
                String data = reader.readLine();
                outputStream.write((data).getBytes());
                ArrayList<Integer> testArr = new ArrayList<>();
                Parser.Parse(new File(pathToTest), testArr);
                Vector<Double> testVector = new Vector<>(26);
                Perceptron.fillVector(testVector, testArr);
                Map<Double, String> tree = new TreeMap<>();
                for (int j = 0; j < neurons.size(); j++) {
                    tree.put(test(testVector, neurons.get(j).getWeight(), neurons.get(j).getTheta()), neurons.get(j).getLanguageName());
                }
                System.out.println(Perceptron.resultLanguage(tree));
                menu();
            }
            case 3 -> {
                System.out.println("Thank you! Bye!");
                System.exit(0);
            }
            default -> {
                System.out.println("Error! Enter number 1-3!");
                menu();
            }
        }
    }

    public static ArrayList<Perceptron> scanTrain(ArrayList<Perceptron> neurons) throws IOException {
        File directory = new File("trains");
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            processFilesFromFolder(new File(String.valueOf(files[i].toPath())), neurons.get(i).getTrainVector());
            Perceptron.fillVector(neurons.get(i).getVector(), neurons.get(i).getTrainVector());
            neurons.get(i).perceptronLanguage(neurons.get(i).getVector(), neurons.get(i).getWeight(), neurons.get(i).getTheta());
        }
        return neurons;
    }

    public static ArrayList<Perceptron> createNeurons(String directoryPath){
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        int countOfNeurons = files.length;

        ArrayList<Perceptron> neurons = new ArrayList<>();
        for (int i = 0; i < countOfNeurons; i++) {
            neurons.add(new Perceptron(files[i].getName()));
        }
        return neurons;
    }


    public static double test(Vector<Double> testVec, double[] trainVector, double theta) {
        double num1 = 0;
        for (int i = 0; i < 26; i++) {
            num1 += testVec.get(i) * trainVector[i];
       }
        num1 -= theta;
        //double output = 1 / (1 + Math.exp(-num1));
        return num1;
    }

    public static void processFilesFromFolder(File folder, ArrayList<Integer> trainVectors) throws IOException {
        File[] folderEntries = folder.listFiles();
        for (File entry : folderEntries) {
            if (entry.isDirectory()) {
                processFilesFromFolder(entry, trainVectors);
                continue;
            }
            Parser.Parse(entry, trainVectors);
        }
    }


}
