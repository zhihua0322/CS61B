package bearmaps;
import java.util.List;

public class KDTree implements PointSet{
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node leftBottom;
        private Node rightTop;

        public Node(Point p, boolean o) {
            this.p = p;
            this.orientation = o;
            this.leftBottom = null;
            this.rightTop = null;
        }
    }
    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }
    }
    private Node add(Point p, Node n, boolean o) {
        if (n == null) {
            return new Node(p, o);
        }
        if (p.equals(n.p)) {
            return n;
        }
        int cmp = comparePoints(p, n.p, o);
        if (cmp > 0) {
            n.rightTop = add(p, n.rightTop, !o);
        } else {
            n.leftBottom = add(p, n.leftBottom, !o);
        }
        return n;
    }
    private int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(a.getX(), b.getX());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }
    @Override
    public Point nearest(double x, double y) {
        return nearest(root, new Point(x,y), root.p);
    }

    private Point nearest(Node n, Point goal, Point best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, goal) < Point.distance(best, goal)) {
            best = n.p;
        }
        int cmp = comparePoints(goal, n.p, n.orientation);
        Node goodSide, badSide;
        if (cmp < 0) {
            goodSide = n.leftBottom;
            badSide = n.rightTop;
        } else {
            goodSide = n.rightTop;
            badSide = n.leftBottom;
        }
        best = nearest(goodSide, goal, best);
        if (needPruning(n, goal, best)) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }
    private boolean needPruning(Node n, Point goal, Point best) {
        double distToBest = Point.distance(best, goal);
        double distToBad;
        if (n.orientation == HORIZONTAL) {
            distToBad = Point.distance(new Point(goal.getX(), n.p.getY()), goal);
        } else {
            distToBad = Point.distance(new Point(n.p.getX(), goal.getY()), goal);
        }
        return distToBad < distToBest;
    }
}
