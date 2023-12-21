package arraylist;

import org.arraylistandhashmap.arraylist.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ArrayListTest {

    private ArrayList<Integer> arrayList;

    @BeforeEach
    public void setUp() {
        arrayList = new ArrayList<>();
    }

    @Test
    public void testEmptyArrayList() {
        assertTrue(arrayList.isEmpty());
        assertEquals(0, arrayList.size());
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(3));
    }

    @Test
    public void testAdd() {
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);

        assertEquals(3, arrayList.size());
        assertFalse(arrayList.isEmpty());

        arrayList.add(0, 4);

        assertEquals(4, arrayList.get(0));
    }

    @Test
    public void testAddAll() {
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);

        ArrayList<Integer> listForAdd = new ArrayList<>();
        listForAdd.add(4);
        listForAdd.add(5);
        listForAdd.add(6);

        assertTrue(arrayList.addAll(listForAdd));
        assertEquals(6, arrayList.size());
        assertEquals(1, arrayList.get(0));
        assertEquals(6, arrayList.get(5));
    }

    @Test
    public void testContains() {
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);

        assertTrue(arrayList.contains(3));
        assertFalse(arrayList.contains(4));
    }

    @Test
    public void testSet() {
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);

        arrayList.set(1, 7);

        assertEquals(7, arrayList.get(1));
        assertNotEquals(2, arrayList.get(1));
    }

    @Test
    public void testRemove() {
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);

        assertTrue(arrayList.remove(Integer.valueOf(2)));
        assertEquals(2, arrayList.size());

        assertEquals(3, arrayList.remove(1));
        assertEquals(1, arrayList.size());

        assertEquals(1, arrayList.get(0));
    }

    @Test
    public void testRemoveAll() {
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);

        ArrayList<Integer> listForRemove = new ArrayList<>();
        listForRemove.add(2);
        listForRemove.add(3);

        assertTrue(arrayList.removeAll(listForRemove));

        assertEquals(1, arrayList.size());
        assertEquals(1, arrayList.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(2));
    }

    @Test
    public void testRetainAll() {
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);

        ArrayList<Integer> listForRetain = new ArrayList<>();
        listForRetain.add(2);
        listForRetain.add(3);

        assertTrue(arrayList.retainAll(listForRetain));

        assertEquals(2, arrayList.size());
        assertEquals(2, arrayList.get(0));
        assertEquals(3, arrayList.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> arrayList.get(2));

    }

    @Test
    public void testClear() {
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);

        arrayList.clear();

        assertEquals(0, arrayList.size());
    }

    @Test
    public void testQuickSort() {
        arrayList.add(-1);
        arrayList.add(28);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(15);

        arrayList.quickSort();

        assertEquals(-1, arrayList.get(0));
        assertEquals(15, arrayList.get(3));
        assertEquals(28, arrayList.get(4));
    }

    @Test
    public void testIterator() {
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);

        Iterator<Integer> it = arrayList.iterator();

        assertEquals(1, it.next());
        assertEquals(2, it.next());

        it.remove();

        assertEquals(4, arrayList.size());
        assertEquals(3, it.next());
    }
}
