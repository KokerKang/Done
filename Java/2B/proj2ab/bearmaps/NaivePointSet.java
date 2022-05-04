package bearmaps;

import java.util.ArrayList;
import java.util.List;

/**Naive Point set class.
 * @author Henry Kang*/
public class NaivePointSet implements PointSet {
    /** Root points set.*/
    private List<Point> myPoints;

    /** NaivePoint Set.
     * @param points Points*/
    public NaivePointSet(List<Point> points) {
        myPoints = new ArrayList<>();
        for (Point point : points) {
            myPoints.add(point);
        }
    }

    /** Override nearest point.
     * @param y y point
     * @param x x point
     * @return nearest points.*/
    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        Point bestPoint = myPoints.get(0);
        double best = Point.distance(myPoints.get(0), target);
        for (Point i : myPoints) {
            if (Point.distance(i, target) < best) {
                best = Point.distance(i, target);
                bestPoint = i;
            }
        }
        return bestPoint;
    }

    /** Main method.
     * @param args main*/
    public static void main(String[] args) {
    }
}
