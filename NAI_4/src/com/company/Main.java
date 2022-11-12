package com.company;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        Reader reader = new Reader();
        //KMeans kMeans = new KMeans();
        EuclideanDistance distance = new EuclideanDistance();
        Scanner sc = new Scanner(System.in);
       System.out.println("Enter the filename...");
       String fileName = sc.next();
        fileName = "Training.txt";

        List<Record> records = reader.read(fileName);

        System.out.println("Enter the number of clusters(K)...");
        int k = sc.nextInt();
        System.out.println("Enter maximum iterations");
        int maxIterations = sc.nextInt();

        Map<Centroid, List<Record>> clusters = KMeans.fit(records, k, distance, maxIterations);
        // Printing the cluster configuration
        System.out.println("\nDetails Clustering of Data");
        for (Map.Entry<Centroid, List<Record>> cluster : clusters.entrySet()) {
            System.out.println(cluster.getKey().toString());
            List<Record> value = cluster.getValue();
            for (Record rec : value){

                System.out.println(rec.toString());
            }
        }

        System.out.println("Done");
    }


}
