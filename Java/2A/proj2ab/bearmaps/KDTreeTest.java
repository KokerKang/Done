package bearmaps;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**KD tree test.
 * @author Henry Kang.*/
public class KDTreeTest {
    private  static Random r = new Random(500);

    private static KDTree buildLectureTree() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);



        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));

        return kd;
    }



    @Test
    public void testException() {
        Point p1 = new Point(1, -1);
        Point p2 = new Point(0, 0);
        Point p3 = new Point(6, 7);


        Point goal = new Point(0, 7);

        KDTree kd = new KDTree(List.of(p1, p2, p3));
        NaivePointSet np = new NaivePointSet(List.of(p1, p2, p3));
        Point kdExpected = kd.nearest(goal.getX(), goal.getY());
        Point npExpected = np.nearest(goal.getX(), goal.getY());
        assertEquals(kdExpected, npExpected);
        int i = 0;
    }

    @Test
    public void testSanity() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        Point goal = new Point(0, 7);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        NaivePointSet np = new NaivePointSet(
                List.of(p1, p2, p3, p4, p5, p6, p7));
        Point kdExpected = kd.nearest(goal.getX(), goal.getY());
        Point npExpected = np.nearest(goal.getX(), goal.getY());
        assertEquals(kdExpected, npExpected);
    }
    @Test
    public void testNearestDemoSlides() {
        KDTree kd = buildLectureTree();
        Point goal = kd.nearest(0, 7);
        Point actual = new Point(1, 5);
        assertEquals(goal, actual);
    }

    private Point randomPoint() {
        double x = r.nextDouble();
        double y = r.nextDouble();
        return new Point(x, y);
    }

    /** return N random Points. */
    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(randomPoint());
        }
        return points;
    }
    @Test
    public void testWith1000Points() {
        List<Point> points1000 = randomPoints(100);
        NaivePointSet nps = new NaivePointSet(points1000);
        KDTree kd = new KDTree(points1000);
        List<Point> queries200 = randomPoints(200);
        for (Point p : queries200) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }

    }


    private static void printTimingTable(
            List<Integer> ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n",
                "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("---------------------------"
                + "---------------------------------\n");
        for (int i = 0; i < ns.size(); i += 1) {
            int N = ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n",
                    N, time, opCount, timePerOp);
        }
    }


    @Test
    public void timeForCons() {
        double timeInSeconds;
        List<Integer> ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();
        for (int i = 31250; i < 2000001; i = i * 2) {
            List<Point> pointsN = randomPoints(i);
            Stopwatch sw = new Stopwatch();
            for (Point p : pointsN) {
                KDTree np = new KDTree(List.of(p));
            }
            timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
            opCounts.add(i);
            ns.add(i);
        }
        System.out.println("Timing table for Naive Nearest");
        printTimingTable(ns, times, opCounts);
    }
    @Test
    public void timeForNaive() {
        double timeInSeconds;
        List<Point> queries = randomPoints(1000000);
        List<Integer> ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();
        for (int i = 125; i < 1001; i = i * 2) {
            List<Point> pointsN = randomPoints(i);
            NaivePointSet checkList = new NaivePointSet(pointsN);
            Stopwatch sw = new Stopwatch();
            for (Point p : queries) {
                checkList.nearest(p.getX(), p.getY());
            }
            timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
            opCounts.add(1000000);
            ns.add(i);
        }
        System.out.println("Timing table for Naive Nearest");
        printTimingTable(ns, times, opCounts);
    }

    @Test
    public void timeForKD() {
        double timeInSeconds;

        List<Integer> ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();
        List<Point> queries = randomPoints(100000);
        List<Point> pointsN = randomPoints(32500);
        KDTree checkList = new KDTree(pointsN);
        Stopwatch sw = new Stopwatch();
        for (Point p : queries) {
            checkList.nearest(p.getX(), p.getY());
        }
        timeInSeconds = sw.elapsedTime();
        times.add(timeInSeconds);
        opCounts.add(32500);
        ns.add(100000);
        System.out.println("Timing table for KDTree Nearest");
        printTimingTable(ns, times, opCounts);
    }
    @Test
    public void timeForKD2() {
        double timeInSeconds;

        List<Integer> ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        for (int i = 10; i < 100000; i = i * 10) {
            List<Point> queries = randomPoints(i);
            List<Point> pointsN = randomPoints(100000);
            KDTree checkList = new KDTree(pointsN);
            Stopwatch sw = new Stopwatch();
            for (Point p : queries) {
                checkList.nearest(p.getX(), p.getY());
            }
            timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
            opCounts.add(100000);
            ns.add(i);
        }

        List<Point> queries = randomPoints(100000);
        List<Point> pointsN = randomPoints(32500);
        KDTree checkList = new KDTree(pointsN);
        Stopwatch sw = new Stopwatch();
        for (Point p : queries) {
            checkList.nearest(p.getX(), p.getY());
        }
        timeInSeconds = sw.elapsedTime();
        times.add(timeInSeconds);
        opCounts.add(32500);
        ns.add(100000);
        System.out.println("Timing table for KDTree Nearest");
        printTimingTable(ns, times, opCounts);
    }
}

