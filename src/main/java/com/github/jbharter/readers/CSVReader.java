package com.github.jbharter.readers;

import java.io.*;
import java.util.stream.Stream;

public class CSVReader {


    public CSVReader(String filename) {

    }
    public static CSVTable fromFile(File file) throws FileNotFoundException, IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        return new CSVTable(bufferedReader.lines(), bufferedReader.readLine());
    }

    public static void main(String[] args) throws Exception {
        CSVTable csvTable = CSVReader.fromFile(new File("/Users/jharte/Downloads/DSAPI Bundle Definitions - 2.15 - IT.csv"));

        System.out.println(csvTable);
    }

}
