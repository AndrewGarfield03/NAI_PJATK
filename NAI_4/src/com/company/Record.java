package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Record {
    private final String description;

    private Map<Integer, Double> features = new HashMap();

    public Record(String description, Map<Integer, Double> features) {
        this.description = description;
        this.features = features;

    }

    public Map<Integer, Double> getFeatures() {
        return this.features;
    }

    @Override
    public String toString() {
        String str = "";
        for (Map.Entry<Integer, Double> recValue : this.features.entrySet()){
            str += (recValue.getValue() + "\t \t");
        }
        str += this.description;
        return str;
    }

    public String getDescription() {
        return this.description;
    }
    // constructor, getter, toString, equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(this.description, record.description) &&
                Objects.equals(this.features, record.features);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.description, this.features);
    }
}
