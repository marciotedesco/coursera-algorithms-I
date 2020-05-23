/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {

    Set<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        if (!pointSet.contains(p))
            pointSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        return pointSet.contains(p);

    }

    // draw all points to standard draw
    public void draw() {
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        for (Point2D point : pointSet) {
            point.draw();
        }
        StdDraw.show();
    }

    // it should support nearest() and range() in time proportional to the number of points in the set.
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();

        List<Point2D> list = new LinkedList<>();
        for (Point2D point : pointSet) {
            if (point.x() <= rect.xmax() && point.x() >= rect.xmin()
                    && point.y() <= rect.ymax() && point.y() >= rect.ymin())
                list.add(point);
        }

        return list;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        if (pointSet.isEmpty())
            return null;

        Point2D nearest = null;
        double nearestDistance = 2; //cannot be more than 2 as it is a 1x1 x,y coordinates square

        for (Point2D point : pointSet) {
            if (nearest == null) {
                nearest = point;
                nearestDistance = nearest.distanceTo(p);
            }
            else if (point.distanceTo(p) < nearestDistance) {
                nearestDistance = point.distanceTo(p);
                nearest = point;
            }
        }
        return nearest;

    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
