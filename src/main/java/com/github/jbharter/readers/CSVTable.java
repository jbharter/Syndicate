package com.github.jbharter.readers;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class CSVTable {

    private CopyOnWriteArrayList<CopyOnWriteArrayList<String>> data;

    public CSVTable() {
        this.data = new CopyOnWriteArrayList<>();
    }

    public CSVTable(Stream<String> stringStream, String headers) {
        this.data = new CopyOnWriteArrayList<>();
        for (String s : headers.split(",")) {
            getRow(0).add(s);
        }
        stringStream
                .parallel()
                .map(each -> Arrays.asList(each.split(",")))
                .map(CopyOnWriteArrayList::new)
                .forEach(each -> data.add(each));

    }

    public List<String> getHeaders() {
        return data.get(0);
    }

    public List<String> getRow(int index) {
        if (data.size() > index) {
        } else if (data.size() == index){
            data.add(new CopyOnWriteArrayList<>());
        }
        return data.get(index);

    }


}
