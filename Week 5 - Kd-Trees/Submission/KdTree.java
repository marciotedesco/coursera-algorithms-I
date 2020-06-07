/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node() {
        }

        public Node(Point2D p) {
            this.p = p;
        }

        public Node(Point2D p, RectHV rectHV) {
            this.p = p;
            this.rect = rectHV;
        }

        public String toString() {
            return "Node{" +
                    "p=" + p +
                    ", rect=" + rect +
                    ", lb=" + lb +
                    ", rt=" + rt +
                    '}';
        }
    }

    private enum Orientation {
        HORIZONTAL,
        VERTICAL;
    }

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = new Node();
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point2D is null");

        root = put(root, Orientation.VERTICAL, null, p);
    }

    private Node put(Node x, Orientation orientation, Node parentNode, Point2D p) {
        // Change key’s value to val if key in subtree rooted at x.
        // Otherwise, add new node to subtree associating key with val. if (x == null) return new Node(key, val, 1);

        if (x == null || x.p == null) {
            size++;
            if (parentNode == null)
                return new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0));
            else {
                if (orientation == Orientation.HORIZONTAL) {
                    //previous orientation was vertical, needs to compare x
                    if (p.x() < parentNode.p.x()) {
                        return new Node(p, new RectHV(parentNode.rect.xmin(),
                                                      parentNode.rect.ymin(),
                                                      parentNode.p.x(),
                                                      parentNode.rect.ymax()));
                    }
                    else {
                        return new Node(p, new RectHV(parentNode.p.x(),
                                                      parentNode.rect.ymin(),
                                                      parentNode.rect.xmax(),
                                                      parentNode.rect.ymax()));
                    }
                }
                else {
                    //previous orientation was horizontal, needs to compare y
                    if (p.y() < parentNode.p.y()) {
                        return new Node(p, new RectHV(parentNode.rect.xmin(),
                                                      parentNode.rect.ymin(),
                                                      parentNode.rect.xmax(),
                                                      parentNode.p.y()));
                    }
                    else {
                        return new Node(p, new RectHV(parentNode.rect.xmin(),
                                                      parentNode.p.y(),
                                                      parentNode.rect.xmax(),
                                                      parentNode.rect.ymax()));
                    }
                }
            }
        }

        int cmp = orientation == Orientation.HORIZONTAL ? Point2D.Y_ORDER.compare(p, x.p) :
                  Point2D.X_ORDER.compare(p, x.p);


        if (cmp < 0)
            x.lb = put(x.lb, orientation == Orientation.HORIZONTAL ? Orientation.VERTICAL :
                             Orientation.HORIZONTAL, x, p);

        else if (cmp > 0)
            x.rt = put(x.rt, orientation == Orientation.HORIZONTAL ? Orientation.VERTICAL :
                             Orientation.HORIZONTAL, x, p);
        else if (cmp == 0 && Point2D.Y_ORDER.compare(p, x.p) == 0
                && Point2D.X_ORDER.compare(p, x.p) == 0) {
            x.p = p;
        }
        else { //cmp == 0
            if (orientation == Orientation.HORIZONTAL)
                if (Point2D.Y_ORDER.compare(p, x.p) < 0) {
                    x.lb = put(x.lb, Orientation.VERTICAL, x, p);
                }
                else {
                    x.rt = put(x.rt, Orientation.VERTICAL, x, p);
                }
            else { // orientation is vertical
                if (Point2D.X_ORDER.compare(p, x.p) < 0) {
                    x.lb = put(x.lb, Orientation.HORIZONTAL, x, p);
                }
                else {
                    x.rt = put(x.rt, Orientation.HORIZONTAL, x, p);
                }
            }
        }

        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Poin2D is null");

        return contains(root, Orientation.VERTICAL, p);
    }

    private boolean contains(Node x, Orientation orientation, Point2D s) {
        if (x == null || x.p == null) {
            return false;
        }

        int cmp = orientation == Orientation.HORIZONTAL ? Point2D.Y_ORDER.compare(s, x.p) :
                  Point2D.X_ORDER.compare(s, x.p);


        if (cmp == 0 && Point2D.Y_ORDER.compare(s, x.p) == 0
                && Point2D.X_ORDER.compare(s, x.p) == 0) {
            return true;
        }
        else if (cmp < 0)
            return contains(x.lb, orientation == Orientation.HORIZONTAL ? Orientation.VERTICAL :
                                  Orientation.HORIZONTAL, s);

        else if (cmp > 0)
            return contains(x.rt, orientation == Orientation.HORIZONTAL ? Orientation.VERTICAL :
                                  Orientation.HORIZONTAL, s);
        else {
            if (orientation == Orientation.HORIZONTAL)
                if (Point2D.Y_ORDER.compare(s, x.p) < 0) {
                    return contains(x.lb, Orientation.VERTICAL, s);
                }
                else {
                    return contains(x.rt, Orientation.VERTICAL, s);
                }
            else { // orientation is vertical
                if (Point2D.X_ORDER.compare(s, x.p) < 0) {
                    return contains(x.lb, Orientation.HORIZONTAL, s);
                }
                else {
                    return contains(x.rt, Orientation.HORIZONTAL, s);
                }
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius();
        StdDraw.line(0.0, 0.0, 0.0, 1.0);
        StdDraw.line(0.0, 0.0, 1.0, 0.0);
        StdDraw.line(1.0, 0.0, 1.0, 1.0);
        StdDraw.line(0.0, 1.0, 1.0, 1.0);

        StdDraw.setPenColor(Color.RED);
        draw(root, Orientation.VERTICAL);
    }

    private void draw(Node node, Orientation orientation) {

        if (node == null)
            return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();

        if (orientation == Orientation.VERTICAL) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        draw(node.lb, orientation == Orientation.HORIZONTAL ? Orientation.VERTICAL :
                      Orientation.HORIZONTAL);
        draw(node.rt, orientation == Orientation.HORIZONTAL ? Orientation.VERTICAL :
                      Orientation.HORIZONTAL);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("node and rect should be non-null arguments");
        }
        if (root == null || this.isEmpty())
            return null;

        return range(root, rect);
    }

    private List<Point2D> range(Node node, RectHV rect) {

        ArrayList<Point2D> result = new ArrayList<>();

        if (node != null) {
            if (node.rect.intersects(rect)) {
                if (rect.contains(node.p)) {
                    //inside the retangle
                    result.add(node.p);
                }

            }

            result.addAll(range(node.lb, rect));

            result.addAll(range(node.rt, rect));
        }


        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Point2D is null");

        if (root == null || this.isEmpty())
            return null;

        return nearest(root, p, null, Integer.MIN_VALUE);
    }

    private Point2D nearest(Node x, Point2D p, Point2D closest, double distanceToClosest) {
        if (x == null)
            return closest;

        if (closest == null) {
            closest = x.p;
            distanceToClosest = x.p.distanceSquaredTo(p);
        }
        else {
            double distanceToCurrent = x.p.distanceSquaredTo(p);

            if (distanceToCurrent < distanceToClosest) {
                distanceToClosest = distanceToCurrent;
                closest = x.p;
            }
        }

        Point2D candidate1 = nearest(x.lb, p, closest, distanceToClosest);

        Point2D candidate2 = nearest(x.rt, p, closest, distanceToClosest);

        if (candidate1.distanceSquaredTo(p) < candidate2.distanceSquaredTo(p)) {
            return candidate1;
        }
        else
            return candidate2;

    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);
        List<Point2D> points = new ArrayList<>();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            points.add(new Point2D(x, y));
        }

        KdTree kdTree = new KdTree();
        for (Point2D p : points) {
            kdTree.insert(p);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-0.1, 1.1);
        StdDraw.setYscale(-0.1, 1.1);
        kdTree.draw();
        StdDraw.show();
    }

}
