
package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class KMeans {

    private static final Random random = new Random();

    public static Map<Centroid, List<Record>> fit(List<Record> records,    //number of iterations
                                                  int k,
                                                  EuclideanDistance distance,
                                                  int maxIterations) {

        List<Centroid> centroids = randomCentroids(records, k);  //calculate centroids for clusters
        Map<Centroid, List<Record>> clusters = new HashMap<>();
        Map<Centroid, List<Record>> lastState = new HashMap<>();

        // iterate for a pre-defined number of times
        for (int i = 0; i < maxIterations; i++) {
            System.out.println("\nIteration " + i);
            boolean isLastIteration = i == maxIterations - 1;

            // in each iteration we should find the nearest centroid for each record
            for (Record record : records) {
                Centroid centroid = nearestCentroid(record, centroids, distance);  //return the nearest centroid for current record
                assignToCluster(clusters, record, centroid);
            }
            //Calculate WCSS
            calculateWCSS(clusters, distance);

            // if the assignments do not change, then the algorithm terminates
            if(clusters.equals(lastState)){
                System.out.println("The assignments do not change, then the algorithm terminates");
            }
            boolean shouldTerminate = isLastIteration || clusters.equals(lastState);
            lastState = clusters;
            if (shouldTerminate) {
                break;
            }

            // at the end of each iteration we should relocate the centroids
            centroids = relocateCentroids(clusters);
            clusters = new HashMap<>();
        }

        return lastState;
    }
    private static double calculateWCSS(Map<Centroid, List<Record>> clusters, EuclideanDistance distance){    //1-With each iteration: For each cluster - the sum of squares of distances from members to the cluster center.
        double wcss = 0;
        for (Map.Entry<Centroid, List<Record>> cluster : clusters.entrySet()) {

            List<Record> value = cluster.getValue();
            double sse = 0.0;
            for (Record record : value){
                sse += Math.pow(distance.calculate(record.getFeatures(), cluster.getKey().getCoordinates()), 2);
            }
            System.out.println("-----------------CLUSTER_" + cluster.getKey().getClusterNum() + "-----------------");
            System.out.println("The sum of squares of distances: " + sse);
            wcss += sse;
        }
        System.out.println("WCSS: " + wcss); //Within Cluster Sum Square
        return wcss;
    }
    private static Centroid nearestCentroid(Record record, List<Centroid> centroids, EuclideanDistance distance) { //define nearest centroid
        double minimumDistance = Double.MAX_VALUE;
        Centroid nearest = null;

        for (Centroid centroid : centroids) {
            double currentDistance = distance.calculate(record.getFeatures(), centroid.getCoordinates());

            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                nearest = centroid;
            }
        }

        return nearest;
    }

    private static void assignToCluster(Map<Centroid, List<Record>> clusters, //assign record to cluster
                                        Record record,
                                        Centroid centroid) {
        clusters.compute(centroid, (key, list) -> {
            if (list == null) {
                list = new ArrayList<>();
            }

            list.add(record);
            return list;
        });
    }

    private static List<Centroid> relocateCentroids(Map<Centroid, List<Record>> clusters) { //recalculate centroids
        return clusters.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(toList());
    }

    private static Centroid average(Centroid centroid, List<Record> records) { //calculate average for new centroid
        if (records == null || records.isEmpty()) {
            return centroid;
        }

        Map<Integer, Double> average = centroid.getCoordinates();
        records.stream().flatMap(e -> e.getFeatures().keySet().stream())
                .forEach(k -> average.put(k, 0.0));

        for (Record record : records) {
            record.getFeatures().forEach(
                    (k, v) -> average.compute(k, (k1, currentValue) -> v + currentValue)
            );
        }

        average.forEach((k, v) -> average.put(k, v / records.size()));

        return new Centroid(centroid.getClusterNum(), average);
    }

    private static List<Centroid> randomCentroids(List<Record> records, int k) {  //
        List<Centroid> centroids = new ArrayList<>();
        Map<Integer, Double> maxs = new HashMap<>();
        Map<Integer, Double> mins = new HashMap<>();

        for (Record record : records) {
            record.getFeatures().forEach((key, value) -> {
                // compares the value with the current max and choose the bigger value between them
                maxs.compute(key, (k1, max) -> max == null || value > max ? value : max);

                // compare the value with the current min and choose the smaller value between them
                mins.compute(key, (k1, min) -> min == null || value < min ? value : min);
            });
        }

        Set<Integer> attributes = records.stream()
                .flatMap(e -> e.getFeatures().keySet().stream())
                .collect(toSet());
        for (int i = 0; i < k; i++) {           //creates random centroid based on dataset
            Map<Integer, Double> coordinates = new HashMap<>();
            for (Integer attribute : attributes) {
                double max = maxs.get(attribute);
                double min = mins.get(attribute);
                coordinates.put(attribute, random.nextDouble() * (max - min) + min);
            }

            centroids.add(new Centroid(i, coordinates));
        }

        return centroids;
    }
}
