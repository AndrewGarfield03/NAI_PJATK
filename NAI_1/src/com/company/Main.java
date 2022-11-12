package com.company;

import org.w3c.dom.ls.LSOutput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        menu(3);
    }

    public static void sortVectors(ArrayList<CustomVector> list) {
        list.sort(Comparator.comparing(CustomVector::getClassName));
    }

    public static void sortDistances(ArrayList<CustomDistance> list) {
        list.sort(Comparator.comparing(CustomDistance::getDistance));
    }

    public static ArrayList<String> calculateDistance(ArrayList<CustomVector> trainVectors, ArrayList<CustomVector> testVectors, int dimension, int k) {
        ArrayList<String> majorityClassNames = new ArrayList<>();
        for (int i = 0; i < testVectors.size(); i++) {
            System.out.println((i + 1) + " value:");
            ArrayList<CustomDistance> distanceArray = new ArrayList<>();
            for (int j = 0; j < trainVectors.size(); j++) {
                double sum = 0;
                for (int l = 0; l < dimension; l++) {
                    double point1 = trainVectors.get(j).getVector().get(l);
                    double point2 = testVectors.get(i).getVector().get(l);
                    sum += Math.pow(point1 - point2, 2);
                }
                distanceArray.add(new CustomDistance(Math.sqrt(sum), trainVectors.get(j).getClassName()));
            }
            sortDistances(distanceArray);
            ArrayList<CustomDistance> kNearestVectors = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                kNearestVectors.add(j, distanceArray.get(j));
                //System.out.println(kNearestVectors.get(j)); // showing distance and classname of the nearest vectors
            }
            majorityClassNames.add(findMajorityClass(kNearestVectors));
        }
        return majorityClassNames;
    }

    public static String findMajorityClass(ArrayList<CustomDistance> kNearestVectors) {
        Map<String, Integer> hm = new HashMap<>();
        String majorityClass = new String();
        int numOfOccur = 0;

        for (CustomDistance cs : kNearestVectors) {
            Integer j = hm.get(cs.getClassName());
            hm.put(cs.getClassName(), (j == null) ? 1 : j + 1);
        }

        System.out.println(hm.entrySet());
        for (Map.Entry<String, Integer> val : hm.entrySet()) {
            double sum1 = 0;
            double sum2 = 0;
            if(val.getValue() > numOfOccur){
                numOfOccur = val.getValue();
                majorityClass = val.getKey();
            }else if(val.getValue() == numOfOccur && !(val.getKey().equals(majorityClass))){
                for (int i = 0; i < kNearestVectors.size(); i++) {
                    if(val.getKey().equals(kNearestVectors.get(i).getClassName())){
                        sum1 += kNearestVectors.get(i).getDistance();
                    }
                    if(majorityClass.equals(kNearestVectors.get(i).getClassName())){
                        sum2 += kNearestVectors.get(i).getDistance();
                    }
                }
                if(sum1 < sum2){
                    majorityClass = val.getKey();
                }
            }
        }

        System.out.println("Majority class is " + majorityClass);
        return majorityClass;
    }


    public static void testAccuracy(ArrayList<CustomVector> test, ArrayList<String> myResults) {
        int correctTests = 0;
        int wrongTests = 0;
        for (int i = 0; i < test.size(); i++) {
            if (test.get(i).getClassName().equals(myResults.get(i))) {
                correctTests++;
            } else {
                wrongTests++;
            }
        }
        double accuracy = (correctTests / Double.parseDouble(String.valueOf(test.size()))) * 100;
        System.out.println("Correct " + correctTests + "/" + test.size());
        System.out.println("Wrong " + wrongTests + "/" + test.size());
        System.out.println("Accuracy: " + Math.round(accuracy) + "%");
    }

    public static void menu(int k) throws IOException {
        String pathToTrainFile = "resources\\train.txt";
        String pathToTestFile = "resources\\test.txt";
        System.out.println("Menu");
        if (k == 3) {
            System.out.println("1.Enter K(3 as default):");
        } else {
            System.out.println("1.Enter K(" + k + " current value):");
        }
        System.out.println("2.Enter path to file with training data");
        System.out.println("3.Enter path to file with test data");
        System.out.println("4.Add new data to training file");
        System.out.println("5.Add new data to test file");
        System.out.println("6.Start program");
        System.out.println("7.Check majority for user vector");
        System.out.println("8.Exit");
        System.out.print("User choice: ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("Enter new value for K: ");
                k = sc.nextInt();
                System.out.println("Now K = " + k);
                menu(k);
            }
            case 2 -> {
                System.out.println("Enter new path to train file: ");
                pathToTrainFile = sc.next();
                System.out.println("Now path to file with training data: " + pathToTrainFile);
                menu(k);
            }
            case 3 -> {
                System.out.println("Enter new path to test file: ");
                pathToTestFile = sc.next();
                System.out.println("Now path to file with test data: " + pathToTestFile);
                menu(k);
            }
            case 4 -> {
                System.out.println("Enter in one line data like num1,num2,num3,num4,className: ");
                String newTrainData = sc.next();
                Writer output;
                output = new BufferedWriter(new FileWriter(pathToTrainFile, true));
                output.append("\n" + newTrainData);
                output.close();
                menu(k);
            }
            case 5 -> {
                System.out.println("Enter in one line data like num1,num2,num3,num4,className");
                String newTestData = sc.next();
                Writer output;
                output = new BufferedWriter(new FileWriter(pathToTestFile, true));
                output.append("\n" + newTestData);
                output.close();
                menu(k);
            }
            case 6 -> {
                ArrayList<CustomVector> trainVectors = new ArrayList<>();
                ArrayList<CustomVector> testVectors = new ArrayList<>();
                Parser.Parse(pathToTrainFile, trainVectors);
                Parser.Parse(pathToTestFile, testVectors);
                //sortVectors(trainVectors);
                //sortVectors(testVectors);
                ArrayList<String> majorityClassNames = calculateDistance(trainVectors, testVectors, trainVectors.get(0).getVector().size(), k);
                //Collections.sort(majorityClassNames);
                testAccuracy(testVectors, majorityClassNames);
                menu(k);
            }
            case 7 -> {
                ArrayList<CustomVector> trainVectors = new ArrayList<>();
                ArrayList<CustomVector> testVectors = new ArrayList<>();
                Parser.Parse(pathToTrainFile, trainVectors);
                System.out.println("Enter your vector:");
                String userVector = sc.next();
                String[] subString = userVector.split(",");
                String className = subString[subString.length - 1];
                Vector<Double> vec = new Vector<>(subString.length - 1);
                for (int i = 0; i < vec.capacity(); i++) {
                    vec.add(Double.parseDouble(subString[i]));
                }
                CustomVector userCustomVector = new CustomVector(vec, className);
                testVectors.add(userCustomVector);
                System.out.println("Enter K:");
                int userK = sc.nextInt();
                ArrayList<String> majorityClassNames = calculateDistance(trainVectors, testVectors, trainVectors.get(0).getVector().size(), userK);
                //testAccuracy(testVectors, majorityClassNames);
                menu(k);
            }
            case 8 -> {
                System.out.println("Bye bye!");
                System.exit(0);
            }
            default -> {
                System.out.println("Error! Enter number 1-8!");
                menu(k);
            }
        }
    }

}
