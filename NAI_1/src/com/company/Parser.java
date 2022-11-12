package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class Parser {

    public static void Parse(String path, ArrayList<CustomVector> arrayList) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        while ((st = br.readLine()) != null) {
            String[] subString = st.split(",");
            String className = subString[subString.length - 1];
            Vector<Double> vec = new Vector<>(subString.length - 1);
            for (int i = 0; i < vec.capacity(); i++) {
                vec.add(Double.parseDouble(subString[i]));
            }
            CustomVector customVector = new CustomVector(vec, className);
            arrayList.add(customVector);
            //System.out.println(customVector);
        }
    }
}
