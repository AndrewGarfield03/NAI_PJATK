package com.company;

import java.util.Vector;

public class CustomVector {
    Vector<Double> vector;
    String ClassName;

    public CustomVector(Vector<Double> vector, String className) {
        this.vector = vector;
        ClassName = className;
    }

    public Vector<Double> getVector() {
        return vector;
    }

    public void setVector(Vector<Double> vector) {
        this.vector = vector;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    @Override
    public String toString() {
        return "vector = " + vector + ", ClassName = " + ClassName + "\n";
    }
}
