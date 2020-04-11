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
    private ArrayList<Double> slopes = new ArrayList<>();
    private ArrayList<Point> collinearPoints = new ArrayList<>();
    private ArrayList<Point> tempCollinearPoints = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException();
        }

        //sort in natural order ;to help detect duplicates
        Arrays.sort(points);

        Point[] otherPoints = new Point[points.length - 1];

        for (int i = 0; i < points.length; i++) {

            for (int j = 0, k = 0; j < points.length; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException("No null entries allowed");

                if (j < points.length - 1 && points[j].compareTo(points[j + 1]) == 0)
                    throw new IllegalArgumentException("No duplicate points allowed");

                if (j != i) {
                    otherPoints[k] = points[j];
                    k++;
                }
            }


            //nlog(n)
            Arrays.sort(otherPoints, points[i].slopeOrder());
            //debug(points[i], otherPoints);

            double slope;
            Point collinearMax;
            Point collinearMin;
            int l = 0;
            while (l < otherPoints.length) {

                slope = points[i].slopeTo(otherPoints[l]);
                //System.out.println("Slope: " + slope);
                tempCollinearPoints.add(points[i]);

                //check for collinear points in sequence on the array
                while (l < otherPoints.length
                        && points[i].slopeTo(otherPoints[l]) == slope) {
                    tempCollinearPoints.add(otherPoints[l]);
                    l++;
                }

                // if there are 4 or more collinear points, we found a line segment
                if (tempCollinearPoints.size() >= 4) {
                    //initialize collinear segment
                    collinearMin = tempCollinearPoints.get(0);
                    collinearMax = tempCollinearPoints.get(0);

                    //obtain max & min points that will form the segment and add collinearPoints to the list
                    for (Point p : tempCollinearPoints) {
                        if (p.compareTo(collinearMax) > 0)
                            collinearMax = p;

                        if (p.compareTo(collinearMin) < 0)
                            collinearMin = p;
                    }

                    //check if segment doesnt exist already
                    if (!isLineSegmentExistent(collinearMin, slope)) {

                        lineSegments.add(new LineSegment(collinearMin, collinearMax));
                        collinearPoints.add(collinearMin);
                        slopes.add(slope);
                        //collinearPoints.add(collinearMax);
                    }

                    //lineSegments.add(new LineSegment(collinearMin, collinearMax));
                }

                tempCollinearPoints.clear();
            }
        }

    }

    private boolean isLineSegmentExistent(Point collinearMin, double slope) {
        for (int i = 0; i < collinearPoints.size(); i++) {
            if (collinearPoints.get(i).compareTo(collinearMin) == 0)
                if (slope == slopes.get(i))
                    return true;
        }

        return false;
    }

    private void debug(Point point, Point[] otherPoints) {
        System.out.println("Reference point: " + point.toString());
        for (Point p : otherPoints) {
            System.out.println("Point " + p.toString() + ": " + point.slopeTo(p));
        }
    }

    //O(n) -> worst case
    private boolean isPointOnLine(List<Point> list, Point p) {
        for (Point q : list)
            if (p.compareTo(q) == 0)
                return true;

        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        if (lineSegments == null)
            return null;

        LineSegment[] segmentsResult = new LineSegment[lineSegments.size()];

        for (int i = 0; i < segmentsResult.length; i++) {
            segmentsResult[i] = lineSegments.get(i);
        }

        return segmentsResult;
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

}
