/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoshin.alex.hadoop;

import shoshin.alex.utils.RecordsCounter;
import java.io.*;
import java.util.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import shoshin.alex.utils.MapSupply;
import shoshin.alex.utils.Printer;


/**
 *
 * @author Alexander_Shoshin
 */
public class HdfsUsage {
    private static final String fsDefaultFs = "fs.defaultFS";
    private static final int logIdPlace = 2;
    private static final int topCount = 100;
    
    public static void main(String[] args) throws IOException {
        String host = "10.20.9.37";
        String port = "8020";
        String inputFilesPath = "/training/dataset/ipinyou";
        String outputFilePath = "/training/hadoop/task2/result.txt";
        
        FileSystem fs = getHDFSConnection(host, port);
        Map<String, Integer> mostFrequentRecords = getTopRecordsCount(fs, inputFilesPath);
        saveToHDFS(fs, outputFilePath, mostFrequentRecords);
        fs.close();
    }
    
    private static FileSystem getHDFSConnection(String host, String port) throws IOException {
        Configuration conf = new Configuration();
        conf.set(fsDefaultFs, "hdfs://" + host + ":" + port);
        return FileSystem.get(conf);
    }
    
    private static Map<String, Integer> getTopRecordsCount(FileSystem fs, String inputFilesPath) throws IOException {
        Map<String, Integer> recordsCountById = new HashMap<String, Integer>();
        Path path = new Path(inputFilesPath);
        for (FileStatus fileStatus: fs.listStatus(path)) {
            if (fs.exists(fileStatus.getPath())) {
                RecordsCounter.countRecordsFromBZip2Stream(fs.open(fileStatus.getPath()), recordsCountById, logIdPlace);
            }
        }
        return getMaxValueRecords(recordsCountById, topCount);
    }
    
    private static Map<String, Integer> getMaxValueRecords(Map<String, Integer> records, int count) {
        List<String> maxValueRecords = MapSupply.getMaxValueKeys(records, count);
        Collections.reverse(maxValueRecords);
        return MapSupply.substring(records, maxValueRecords);
    }
    
    private static void saveToHDFS(FileSystem fs, String outputFilePath, Map<String, Integer> mostFrequentRecords) throws IOException {
        Printer.print(mostFrequentRecords, fs.create(new Path(outputFilePath)));
    }
}