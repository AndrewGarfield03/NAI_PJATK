package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Parser {

    public static void Parse(File file, ArrayList<Integer> arrayList) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] subString = line.split("");
            for (int i = 0; i < subString.length; i++) {
                char character = subString[i].toLowerCase().charAt(0);
                int index = character;
                if(index >= 97 && index <= 122){
                    arrayList.add(index);
                }
            }
        }
    }
}
