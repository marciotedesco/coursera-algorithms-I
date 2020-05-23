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
    }

    enum Orientation {
        HORIZONTAL,
        VERTICAL;
    }

    Node root;
    int size;

    // construct an empty set of points
    public KdTree() {
        root = new Node();
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = put(root, Orientation.VERTICAL, null, p);
    }

    private Node put(Node x, Orientation orientation, Node parentNode, Point2D p) {
        // Change key’s value to val if key in subtree rooted at x.
        // Otherwise, add new node to subtree associating key with val. if (x == null) return new Node(key, val, 1);

        if (x == null || x.p == null) {
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


        if (cmp < 0)//|| (cmp == 0 && Point2D.X_ORDER.compare(p, x.p) != 0))
            x.lb = put(x.lb, orientation == Orientation.HORIZONTAL ? Orientation.VERTICAL :
                             Orientation.HORIZONTAL, x, p);

        else if (cmp > 0)//|| (cmp == 0 && Point2D.Y_ORDER.compare(p, x.p) != 0))
            x.rt = put(x.rt, orientation == Orientation.HORIZONTAL ? Orientation.VERTICAL :
                             Orientation.HORIZONTAL, x, p);
        else x.p = p;

        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node currentNode = root;
        while (currentNode != null && currentNode.p != p) {
            if (currentNode.p == p)
                break;
            else if (currentNode.p.compareTo(p) < 0) {
                currentNode = currentNode.lb;
            }
            else if (currentNode.p.compareTo(p) > 0) {
                currentNode = currentNode.rt;
            }
        }
        if (currentNode != null)
            System.out.println(currentNode.toString());

        return currentNode != null;
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

    public void draw(Node node, Orientation orientation) {

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
        return null;

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return null;
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
