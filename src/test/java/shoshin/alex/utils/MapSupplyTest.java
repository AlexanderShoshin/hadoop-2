package shoshin.alex.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Alexander_Shoshin
 */
public class MapSupplyTest {
    private Map<String, Integer> targetMap;
    
    @Before
    public void initTargetMap() {
        targetMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            targetMap.put("key" + i, i);
        }
    }
    
    @Test
    public void should_find_max_values_keys() {
        List<String> maxKeys = MapSupply.getMaxValueKeys(targetMap, 3);
        Collections.sort(maxKeys);
        Assert.assertArrayEquals(new String[]{"key7", "key8", "key9"}, maxKeys.toArray(new String[0]));
    }
    
    @Test
    public void should_substring_map() {
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("key1", 1);
        expectedMap.put("key5", 5);
        expectedMap.put("key4", 4);
        List<String> keysToSubstring = new LinkedList<>();
        Collections.addAll(keysToSubstring, expectedMap.keySet().toArray(new String[0]));
        Map<String, Integer> subMap = MapSupply.substring(targetMap, keysToSubstring);
        Assert.assertEquals(expectedMap, subMap);
    }
}