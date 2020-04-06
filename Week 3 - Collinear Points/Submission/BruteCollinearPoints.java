/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BruteCollinearPoints {

    //ArrayList and LinkedList in java.util and Stack in algs4.jar are all allowed. Or you can implement data type yourself.
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();
    private ArrayList<Double> slopeList = new ArrayList<Double>();
    //keeps track of already added slopes

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        //System.out.println(Arrays.toString(points));
        Arrays.sort(points, new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                return o1.compareTo(o2);
            }
        });
        //System.out.println(Arrays.toString(points));


        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                for (int k = 0; k < points.length; k++) {
                    for (int l = 0; l < points.length; l++) {

                        if (points[i] == null || points[j] == null || points[k] == null
                                || points[l] == null)
                            throw new IllegalArgumentException();

                        if (i == j || j == k || k == l || i == k || i == l || j == l) {
                            //System.out.println("Skip -  i: " + i + ", j: " + j + ", k: " + k + ", l: " + l);
                            continue;
                        }

                        //To check whether the 4 points p, q, r, and s are collinear, check whether the three
                        // slopes between p and q, between p and r, and between p and s are all equal.
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                && points[i].slopeTo(points[k]) == points[j].slopeTo(points[l])) {
                            //System.out.println("New Line Segment points: " + points[i].toString() + " "
                            // +points[j].toString() + " " + points[k].toString() +" " + points[l].toString());

                            addLineSegmentIfAbsent(
                                    minPoint(new Point[] {
                                            points[i], points[j], points[k], points[l]
                                    }),
                                    maxPoint(new Point[] {
                                            points[i], points[j], points[k], points[l]
                                    }),
                                    points[i].slopeTo(points[j]));
                        }

                    }
                }
            }
        }
    }

    private Point minPoint(Point[] points) {
        if (points == null || points[0] == null)
            throw new IllegalArgumentException();

        Point minPoint = points[0];
        for (Point p : points) {
            if (minPoint.compareTo(p) > 0)
                minPoint = p;
        }
        return minPoint;
    }

    private Point maxPoint(Point[] points) {
        if (points == null || points[0] == null)
            throw new IllegalArgumentException();

        Point maxPoint = points[0];
        for (Point p : points) {
            if (maxPoint.compareTo(p) < 0)
                maxPoint = p;
        }
        return maxPoint;
    }


    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public List<LineSegment> segments() {
        if (lineSegments == null)
            lineSegments = new ArrayList<>();


        return lineSegments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private void addLineSegmentIfAbsent(Point pointP, Point pointQ, double slope) {
        LineSegment lineSegment = new LineSegment(pointP, pointQ);
        if (!slopeList.contains(slope)) {
            lineSegments.add(lineSegment);
            slopeList.add(slope);
        }
    }
}
