package shoshin.alex.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class RecordsCounter {
    private static final String FIELD_SEPARATOR = "\\s";
    private static final String NULL = "null";
    
    public static void countRecordsFromBZip2Stream(InputStream inputStream, Map<String, Integer> recordsCount, int groupBy) throws IOException {
        try (InputStream input = new BZip2CompressorInputStream(inputStream)) {
            countRecordsFromStream(input, recordsCount, groupBy);
        }
    }
    
    public static void countRecordsFromStream(InputStream inputStream, Map<String, Integer> recordsCount, int groupBy) throws IOException {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(inputStream))) {
            countRecords(input, recordsCount, groupBy);
        }
    }
    
    private static void countRecords(BufferedReader input, Map<String, Integer> recordsCount, int groupBy) throws IOException {
        String record;
        while ((record = input.readLine()) != null) {
            countRecord(record, recordsCount, groupBy);
        }
    }
    
    private static void countRecord(String record, Map<String, Integer> recordsCount, int keyPosition) {
        String[] fields = record.split(FIELD_SEPARATOR);
        String key = fields[keyPosition];
        
        if (key.equals(NULL)) {
            return;
        }
        if (recordsCount.containsKey(key)) {
            recordsCount.put(key, recordsCount.get(key) + 1);
        } else {
            recordsCount.put(key, 1);
        }
    }
}