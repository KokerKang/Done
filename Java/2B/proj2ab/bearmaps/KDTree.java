package bearmaps;

import java.util.List;

/** KDTree Class.
 * @author Henry Kang
 * */
public class KDTree implements PointSet {
    /** Root Node.*/
    private Node root;

    /** Node Class.*/
    private class Node {

        /** Node has Point class. */
        private Point point;

        /** Left Node.*/
        private Node left;

        /**Right Node.*/
        private Node right;

        /** Height for Node.*/
        private int height;

        /** Constructor Node.
         * @param p point
         * @param h height
         * @param leftNode leftNode
         * @param rightNode rightNode*/
        Node(Point p, int h, Node leftNode, Node rightNode) {
            point = new Point(p.getX(), p.getY());
            height = h;
            left = leftNode;
            right = rightNode;
        }
    }

    /** Add Node.
     * @param h height
     * @param parent parent node
     * @param p which is put point.
     * @return added node.*/
    private Node add(Point p, Node parent, int h) {
        if (parent == null) {
            return new Node(p, h, null, null);
        }
        if (p.equals(parent.point)) {
            return parent;
        }
        if (h % 2 == 0) {
            if (Double.compare(parent.point.getX(), p.getX()) > 0) {
                parent.left = add(p, parent.left, h + 1);
            } else {
                parent.right = add(p, parent.right, h + 1);
            }
        } else {
            if (Double.compare(parent.point.getY(), p.getY()) > 0) {
                parent.left = add(p, parent.left, h + 1);
            } else {
                parent.right = add(p, parent.right, h + 1);
            }
        }
        return parent;
    }

    /** Point.
     * @param points added points*/
    public KDTree(List<Point> points) {
        for (Point point : points) {
            root = add(point, root, 0);
        }
    }

    /**Overrided nearest fuction.
     * @param x x-point.
     * @param y y-point.
     * @return nearest point.*/
    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearestHelper(root, goal, root.point);
    }

    /** nearest helper fuction.
     * @param best best Point.
     * @param goal goal point.
     * @param n entire node.
     * @return nearest Point.*/
    private Point nearestHelper(Node n, Point goal, Point best) {
        Node goodSide;
        Node badSide;
        if (n == null) {
            return best;
        }
        if (Point.distance(n.point, goal) < Point.distance(best, goal)) {
            best = n.point;
        }
        if (n.height % 2 == 0) {
            if (Double.compare(goal.getX(), n.point.getX()) < 0) {
                goodSide = n.left;
                badSide = n.right;
            } else {
                goodSide = n.right;
                badSide = n.left;
            }
        } else {
            if (Double.compare(goal.getY(), n.point.getY()) < 0) {
                goodSide = n.left;
                badSide = n.right;
            } else {
                goodSide = n.right;
                badSide = n.left;
            }
        }
        best = nearestHelper(goodSide, goal, best);

        if (n.height % 2 == 0) {
            if (checkBadX(goal, best)) {
                best = nearestHelper(badSide, goal, best);
            }
        } else {
            if (checkBadY(goal, best)) {
                best = nearestHelper(badSide, goal, best);
            }
        }

        return best;
    }

    /** Check bad point. horizontal.
     * @param goal goal point
     * @param best best point.
     * @return it's bad or not.*/
    private boolean checkBadX(Point goal, Point best) {
        double temp1 = Point.distance(goal,
                new Point(best.getX(), goal.getY()));
        double temp2 = Point.distance(goal, best);
        return temp1 < temp2;
    }

    /** Check bad point. verticle.
     * @param goal goal point
     * @param best best point.
     * @return it's bad or not.*/
    private boolean checkBadY(Point goal, Point best) {
        double temp1 = Point.distance(goal,
                new Point(goal.getX(), best.getY()));
        double temp2 = Point.distance(goal, best);
        return temp1 < temp2;
    }

    /**Main method.
     * @param args main.*/
    public static void main(String[] args) {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);

        KDTree check = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }
}
