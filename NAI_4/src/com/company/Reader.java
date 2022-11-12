package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reader {

    List<Record> read(String fileName) throws NumberFormatException, IOException {

        File file = new File(fileName);
        List<Record> records = new ArrayList<>();
        try {
            BufferedReader readFile = new BufferedReader(new FileReader(file));
            String line;
            while ((line = readFile.readLine()) != null) {

                String[] split = line.split(",");
                //double[] feature = new double[split.length - 1];
                //numberOfFeatures = split.length-1;
                Map<Integer, Double> features = new HashMap();
                String description = split[split.length - 1];
                for (int i = 0; i < split.length - 1; i++) {
                    Double feature = Double.parseDouble(split[i]);
                    features.put(i, feature);
                }
                records.add(new Record(description,features));
                //label.add(labels);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return records;
    }
}
