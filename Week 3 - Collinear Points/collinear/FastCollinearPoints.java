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
import java.util.List;

public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        Point[] otherPoints = new Point[points.length - 1];

        for (int i = 0; i < points.length; i++) {
            for (int j = 0, k = 0; j < points.length; j++) {
                if (j != i) {
                    otherPoints[k] = points[j];
                    k++;
                }
            }
            //nlog(n)
            Arrays.sort(otherPoints, points[i].slopeOrder());

            double slope;
            int countPoints = 1;
            Point collinearMax = points[i];
            Point collinearMin = points[i];
            for (int l = 0; l < otherPoints.length; l++) {
                if (l != i) {
                    slope = points[i].slopeTo(otherPoints[l]);
                    while (l < otherPoints.length && points[i].slopeTo(otherPoints[l]) == slope) {
                        if (otherPoints[l].compareTo(collinearMax) > 0)
                            collinearMax = otherPoints[l];

                        if (otherPoints[l].compareTo(collinearMin) < 0)
                            collinearMin = otherPoints[l];

                        countPoints++;
                        l++;
                    }

                    if (countPoints >= 4) {
                        lineSegments.add(new LineSegment(collinearMin, collinearMax));
                    }

                    countPoints = 1;
                    collinearMin = points[i];
                    collinearMax = points[i];
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public List<LineSegment> segments() {
        return this.lineSegments;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

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


}
