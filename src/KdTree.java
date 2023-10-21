
/*************************************************************************
 *************************************************************************/

import java.util.Arrays;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D point, RectHV rect) {
            this.point = point;
            this.rect = rect;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        root = insert(root, p, true, new RectHV(0, 0, 1, 1));
    }

    private Node insert(Node node, Point2D p, boolean isVertical, RectHV rect) {
        if (node == null) {
            size++;
            return new Node(p, rect);
        }

        if (node.point.equals(p)) {
            return node;
        }

        int cmp;
        if (isVertical) {
            cmp = Double.compare(p.x(), node.point.x());
            if (cmp < 0) {
                node.left = insert(node.left, p, !isVertical, new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax()));
            } else {
                node.right = insert(node.right, p, !isVertical, new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax()));
            }
        } else {
            cmp = Double.compare(p.y(), node.point.y());
            if (cmp < 0) {
                node.left = insert(node.left, p, !isVertical, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y()));
            } else {
                node.right = insert(node.right, p, !isVertical, new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax()));
            }
        }

        return node;
    }

    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D p, boolean isVertical) {
        if (node == null) {
            return false;
        }

        if (node.point.equals(p)) {
            return true;
        }

        int cmp;
        if (isVertical) {
            cmp = Double.compare(p.x(), node.point.x());
        } else {
            cmp = Double.compare(p.y(), node.point.y());
        }

        if (cmp < 0) {
            return contains(node.left, p, !isVertical);
        } else {
            return contains(node.right, p, !isVertical);
        }
    }

    public void draw() {
    draw(root, true);
}

private void draw(Node node, boolean isVertical) {
    if (node != null) {
        // Draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();

        // Draw the dividing line based on orientation
        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }

        // Recursively draw left and right subtrees
        draw(node.left, !isVertical);
        draw(node.right, !isVertical);
    }
}

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> pointsInRange = new Queue<>();
        range(root, rect, pointsInRange);
        return pointsInRange;
    }
    
    private void range(Node node, RectHV rect, Queue<Point2D> pointsInRange) {
        if (node != null) {
            if (rect.contains(node.point)) {
                pointsInRange.enqueue(node.point);
            }
            
            if (node.left != null && rect.intersects(node.left.rect)) {
                range(node.left, rect, pointsInRange);
            }
            
            if (node.right != null && rect.intersects(node.right.rect)) {
                range(node.right, rect, pointsInRange);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
        
        return nearest(root, p, root.point, true);
    }
    
    private Point2D nearest(Node node, Point2D query, Point2D closest, boolean isVertical) {
        if (node == null) {
            return closest;
        }
    
        double closestDist = closest.distanceTo(query);
        double currDist = node.point.distanceTo(query);
    
        if (currDist < closestDist) {
            closest = node.point;
        }
    
        if (isVertical) {
            if (query.x() < node.point.x()) {
                closest = nearest(node.left, query, closest, !isVertical);
                if (node.right != null && node.right.rect.distanceTo(query) < closest.distanceTo(query)) {
                    closest = nearest(node.right, query, closest, !isVertical);
                }
            } else {
                closest = nearest(node.right, query, closest, !isVertical);
                if (node.left != null && node.left.rect.distanceTo(query) < closest.distanceTo(query)) {
                    closest = nearest(node.left, query, closest, !isVertical);
                }
            }
        } else {
            if (query.y() < node.point.y()) {
                closest = nearest(node.left, query, closest, !isVertical);
                if (node.right != null && node.right.rect.distanceTo(query) < closest.distanceTo(query)) {
                    closest = nearest(node.right, query, closest, !isVertical);
                }
            } else {
                closest = nearest(node.right, query, closest, !isVertical);
                if (node.left != null && node.left.rect.distanceTo(query) < closest.distanceTo(query)) {
                    closest = nearest(node.left, query, closest, !isVertical);
                }
            }
        }
    
        return closest;
    }
    

    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
        In in = new In();
        Out out = new Out();
        int nrOfRecangles = in.readInt();
        int nrOfPointsCont = in.readInt();
        int nrOfPointsNear = in.readInt();
        RectHV[] rectangles = new RectHV[nrOfRecangles];
        Point2D[] pointsCont = new Point2D[nrOfPointsCont];
        Point2D[] pointsNear = new Point2D[nrOfPointsNear];
        for (int i = 0; i < nrOfRecangles; i++) {
            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsCont; i++) {
            pointsCont[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsNear; i++) {
            pointsNear[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        KdTree set = new KdTree();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));
        }
        for (int i = 0; i < nrOfRecangles; i++) {
            // Query on rectangle i, sort the result, and print
            Iterable<Point2D> ptset = set.range(rectangles[i]);
            int ptcount = 0;
            for (Point2D p : ptset)
                ptcount++;
            Point2D[] ptarr = new Point2D[ptcount];
            int j = 0;
            for (Point2D p : ptset) {
                ptarr[j] = p;
                j++;
            }
            Arrays.sort(ptarr);
            out.println("Inside rectangle " + (i + 1) + ":");
            for (j = 0; j < ptcount; j++)
                out.println(ptarr[j]);
        }
        out.println("Contain test:");
        for (int i = 0; i < nrOfPointsCont; i++) {
            out.println((i + 1) + ": " + set.contains(pointsCont[i]));
        }

        out.println("Nearest test:");
        for (int i = 0; i < nrOfPointsNear; i++) {
            out.println((i + 1) + ": " + set.nearest(pointsNear[i]));
        }

        out.println();
    }
}
