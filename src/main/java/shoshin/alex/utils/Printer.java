/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoshin.alex.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.fs.FSDataOutputStream;

/**
 *
 * @author Alexander_Shoshin
 */
public class Printer {
    public static <K, V> void print(Map<K, V> map, OutputStream output) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(output))) {
            print(map, bufferedWriter);
        }
    }
    
    private static <K, V> void print(Map<K, V> map, BufferedWriter output) throws IOException {
        for (K key: map.keySet()) {
            output.write(String.format("%1$s - %2$s", key, map.get(key)));
            output.newLine();
        }
    }
}
