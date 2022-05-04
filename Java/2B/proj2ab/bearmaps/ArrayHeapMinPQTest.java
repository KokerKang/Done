package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {


    @Test
    public void testAdd() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        for (int i = 8; i > 0; i--) {
            test.add(i, i);
        }
        assertEquals(8, test.size());
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        for (int i = 8; i > 0; i--) {
            test.add(i, i);
        }
        assertEquals(1, (int) test.removeSmallest());
        assertEquals(7, test.size());
        assertEquals(2, (int) test.getSmallest());
    }

    @Test
    public void testGetSmallest2() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        for (int i = 8; i > 0; i--) {
            test.add(i, i);
        }

        assertEquals(1, (int) test.removeSmallest());
        assertEquals(2, (int) test.removeSmallest());
        assertEquals(3, (int) test.removeSmallest());
        assertEquals(4, (int) test.removeSmallest());
        assertEquals(5, (int) test.removeSmallest());
        assertEquals(6, (int) test.removeSmallest());
        assertEquals(7, (int) test.removeSmallest());
        assertEquals(8, (int) test.removeSmallest());
    }
    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        for (int i = 8; i > 0; i--) {
            test.add(i, i);
        }
        assertTrue(test.contains(1));
        assertTrue(test.contains(2));
        assertTrue(test.contains(3));
        assertTrue(test.contains(4));
        assertTrue(test.contains(5));
        assertFalse(test.contains(9));
        assertFalse(test.contains(15));
        assertFalse(test.contains(91));
        assertFalse(test.contains(11));

    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();
        test.add("aaa", 0.1);
        test.add("bbb", 0.2);
        test.add("ccc", 0.3);
        test.changePriority("aaa", 0.4);
        assertEquals("bbb", test.getSmallest());

    }
    private static Random r = new Random();

    @Test
    public void testRandom() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> naiveTest = new NaiveMinPQ<>();
        for (int i = 0; i < 100000; i++) {
            int k = r.nextInt(10000000);
            if (!(test.contains(k))) {
                test.add(k, k);
                naiveTest.add(k, k);
            }
        }
        for (int i = 0; i < test.size(); i++) {
            assertEquals(test.removeSmallest(), naiveTest.removeSmallest());
        }
    }

    @Test
    public void testAddingTime() {

        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();

        Stopwatch sw = new Stopwatch();
        for (int i = 100000; i > 0; i--) {
            test.add(i, i);
        }
        double timeInSeconds = sw.elapsedTime();
        System.out.println("Time to add in queue is "
                + timeInSeconds + " amount of " + test.size() + " .");

    }
    @Test
    public void testRemovingTime() {

        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        for (int i = 100000; i > 0; i--) {
            test.add(i, i);
        }
        int size = test.size();
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < test.size(); i++) {
            test.removeSmallest();
        }
        double timeInSeconds = sw.elapsedTime();
        System.out.println("Time to remove in queue is "
                + timeInSeconds + " amount of " + size + " .");

    }

}

