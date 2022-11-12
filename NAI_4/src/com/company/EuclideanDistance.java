package com.company;

import java.util.Map;

public class EuclideanDistance {

    public double calculate(Map<Integer, Double> f1, Map<Integer, Double> f2) {
        double sum = 0;
        for (Integer key : f1.keySet()) {
            Double v1 = f1.get(key);
            Double v2 = f2.get(key);

            if (v1 != null && v2 != null) {
                sum += Math.pow(v1 - v2, 2);
            }
        }

        return Math.sqrt(sum);
    }
}
