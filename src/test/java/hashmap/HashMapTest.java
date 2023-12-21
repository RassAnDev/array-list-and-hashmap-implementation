package hashmap;

import org.arraylistandhashmap.hashmap.HashMap;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class HashMapTest {

    private HashMap<Integer, String> hashMap;

    @BeforeEach
    public void setUp() {
        hashMap = new HashMap<>();
    }

    @Test
    public void testEmptyHashMap() {
        assertTrue(hashMap.isEmpty());
        assertEquals(0, hashMap.size());
        assertNull(hashMap.get(1));
    }

    @Test
    public void testPutAndGet() {
        hashMap.put(1, "one");
        hashMap.put(2, "two");
        hashMap.put(3, "three");

        assertFalse(hashMap.isEmpty());
        assertEquals(3, hashMap.size());

        assertEquals("one", hashMap.get(1));
        assertEquals("two", hashMap.get(2));
        assertEquals("three", hashMap.get(3));
        assertNotEquals("one", hashMap.get(3));
        assertNull(hashMap.get(4));
    }

    @Test
    public void testGetOrDefault() {
        hashMap.put(1, "one");

        assertEquals("default", hashMap.getOrDefault(3, "default"));
    }

    @Test
    public void testContainsKey() {
        hashMap.put(1, "one");

        assertTrue(hashMap.containsKey(1));
        assertFalse(hashMap.containsKey(2));
    }

    @Test
    public void testContainsValue() {
        hashMap.put(1, "one");

        assertTrue(hashMap.containsValue("one"));
        assertFalse(hashMap.containsValue("two"));
    }

    @Test
    public void testRemove() {
        hashMap.put(1, "one");
        hashMap.put(2, "two");
        hashMap.put(3, "three");

        hashMap.remove(2);

        assertFalse(hashMap.isEmpty());
        assertEquals(2, hashMap.size());

        assertEquals("one", hashMap.get(1));
        assertNull(hashMap.get(2));
        assertEquals("three", hashMap.get(3));
    }

    @Test
    public void testClear() {
        hashMap.put(1, "one");
        hashMap.put(2, "two");
        hashMap.put(3, "three");

        hashMap.clear();

        assertEquals(0, hashMap.size());
        assertTrue(hashMap.isEmpty());
    }

    @Test
    public void testKeySet() {
        hashMap.put(1, "one");
        hashMap.put(2, "two");
        hashMap.put(3, "three");

        Set<Integer> expectedSet = Set.of(1, 2, 3);
        Set<Integer> actualSet = hashMap.keySet();

        assertTrue(expectedSet.containsAll(actualSet));
    }

    @Test
    public void resize() {
        for (int i = 0; i < 1000; i++) {
            hashMap.put(i, String.valueOf(i));
        }

        assertFalse(hashMap.isEmpty());
        assertEquals(1000, hashMap.size());

        for (int i = 0; i < 1000; i++) {
            assertEquals(String.valueOf(i), hashMap.get(i));
        }
    }
}
