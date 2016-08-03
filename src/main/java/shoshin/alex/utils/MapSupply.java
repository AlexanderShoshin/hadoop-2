package shoshin.alex.utils;

import java.util.*;

public class MapSupply {
    public static <K> List<K> getMaxValueKeys(Map<K, Integer> data, int maxValuesCount) {
        List<K> maxValueKeys = new LinkedList<>();
        data.forEach((id, count) -> {
            int pos;
            for (pos = 0; pos < maxValueKeys.size(); pos++) {
                if (count <= data.get(maxValueKeys.get(pos))) {
                    break;
                }
            }
            maxValueKeys.add(pos, id);
            if (maxValueKeys.size() > maxValuesCount) {
                maxValueKeys.remove(0);
            }
        });
        return maxValueKeys;
    }

    public static <K, V> Map<K, V> substring(Map<K, V> map, List<K> keys) {
        Map<K, V> subMap = new LinkedHashMap<>();
        for (K key: keys) {
            if (map.containsKey(key)) {
                subMap.put(key, map.get(key));
            }
        }
        return subMap;
    }
}