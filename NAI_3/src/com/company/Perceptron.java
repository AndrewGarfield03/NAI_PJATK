package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Perceptron {
    private String languageName;
    private ArrayList<Integer> trainVector = new ArrayList<>();
    private Vector<Double> vector = new Vector<>(26);
    private double theta = 1;
    private double[] weight = new double[26];

    public Perceptron(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageName() {
        return languageName;
    }

    public ArrayList<Integer> getTrainVector() {
        return trainVector;
    }

    public Vector<Double> getVector() {
        return vector;
    }

    public double getTheta() {
        return theta;
    }

    public double[] getWeight() {
        return weight;
    }

    public static void fillVector(Vector<Double> vector, ArrayList<Integer> trainVectors) {
        for (int i = 97; i < 123; i++) {
            double a = Collections.frequency(trainVectors, i);
            double proportion = (double) a / trainVectors.size();
            vector.add(proportion);
        }
    }

    public static int calculateOutput(Vector<Double> input, double[] weight, int dimension, double theta) {
        double res = 0;
        for (int l = 0; l < dimension; l++) {
            double point1 = input.get(l);
            double point2 = weight[l];
            res += point1 * point2;
        }
        return (res >= theta) ? 1 : 0;
    }

    public void perceptronLanguage(Vector<Double> vector, double[] weights, double theta) {
        double diff;
        int iteration;
        double output;
        for (int i = 0; i < 26; i++) {
            weights[i] = 0;
        }
        double learningRate = 0.1;
        iteration = 0;
        do {
            iteration++;
            for (int j = 0; j < vector.size(); j++) {
                output = calculateOutput(vector, weights, 26, theta);
                diff = 1 - output;
                for (int i = 0; i < 26; i++) {
                    weights[i] += learningRate * diff * vector.get(i);
                }
                theta -= diff * learningRate;
            }
        } while (iteration < 25);
    }

    public static String resultLanguage(Map<Double, String> map) {
        List<Double> byVal = new ArrayList<>(map.keySet());
        Collections.sort(byVal);
        return "Result: " + map.get(byVal.get(byVal.size() - 1));
    }
}
