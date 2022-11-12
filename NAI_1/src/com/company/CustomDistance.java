package com.company;

public class CustomDistance {
    double distance;
    String className;

    public CustomDistance(double distance, String className) {
        this.distance = distance;
        this.className = className;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "distance= " + distance + ", className= " + className;
    }
}
