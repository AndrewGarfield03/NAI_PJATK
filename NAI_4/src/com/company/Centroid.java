package com.company;

import java.util.HashMap;
import java.util.Map;

public class Centroid {

    private Map<Integer, Double> coordinates = new HashMap();

    private int clusterNum;

    @Override
    public String toString() {
        return "-----------------CLUSTER_" + this.clusterNum + "-----------------";
    }

    public Centroid(int clusterNum, Map<Integer, Double> coordinates) {
        this.clusterNum = clusterNum;
        this.coordinates = coordinates;
    }


    public Map<Integer, Double> getCoordinates() {
        return this.coordinates;
    }

    public int getClusterNum() {
        return this.clusterNum;
    }
}
