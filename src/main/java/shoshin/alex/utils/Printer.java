package shoshin.alex.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 *
 * @author Alexander_Shoshin
 */
public class Printer {
    public static <K, V> void printToStream(Map<K, V> map, OutputStream output) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(output))) {
            printToStream(map, bufferedWriter);
        }
    }
    
    private static <K, V> void printToStream(Map<K, V> map, BufferedWriter output) throws IOException {
        for (K key: map.keySet()) {
            output.write(String.format("%1$s - %2$s", key, map.get(key)));
            output.newLine();
        }
    }
}