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
public class HdfsUsageSample {
    private static final int LOG_ID_PLACE = 2;
    private static final int TOP_COUNT = 100;
    
    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.load(HdfsUsageSample.class.getClassLoader().getResourceAsStream("hadoop.properties"));
        String host = props.getProperty("host");
        String port = props.getProperty("port");
        String inputFilesPath = props.getProperty("dataset.folder.path");
        String outputFilePath = props.getProperty("output.folder.path");
        
        FileSystem fs = getHDFSConnection(host, port);
        Map<String, Integer> mostFrequentRecords = getTopRecordsCount(fs, inputFilesPath);
        saveToHDFS(fs, outputFilePath, mostFrequentRecords);
        fs.close();
    }
    
    private static FileSystem getHDFSConnection(String host, String port) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://" + host + ":" + port);
        return FileSystem.get(conf);
    }
    
    private static Map<String, Integer> getTopRecordsCount(FileSystem fs, String inputFilesPath) throws IOException {
        Map<String, Integer> recordsCountById = new HashMap<String, Integer>();
        Path path = new Path(inputFilesPath);
        for (FileStatus fileStatus: fs.listStatus(path)) {
            if (fs.exists(fileStatus.getPath())) {
                RecordsCounter.countRecordsFromBZip2Stream(fs.open(fileStatus.getPath()), recordsCountById, LOG_ID_PLACE);
            }
        }
        return getMaxValueRecords(recordsCountById, TOP_COUNT);
    }
    
    private static Map<String, Integer> getMaxValueRecords(Map<String, Integer> records, int count) {
        List<String> maxValueKeys = MapSupply.getMaxValueKeys(records, count);
        Collections.reverse(maxValueKeys);
        return MapSupply.substring(records, maxValueKeys);
    }
    
    private static void saveToHDFS(FileSystem fs, String outputFilePath, Map<String, Integer> mostFrequentRecords) throws IOException {
        Printer.printToStream(mostFrequentRecords, fs.create(new Path(outputFilePath)));
    }
}