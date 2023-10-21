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
        root = insert(root, p, true, 0, 0, 1, 1);
    }

    private Node insert(Node node, Point2D p, boolean isVertical, double xmin, double ymin, double xmax, double ymax) {
        if (node == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }

        if (node.point.equals(p)) {
            return node;
        }

        int cmp = isVertical ? Double.compare(p.x(), node.point.x()) : Double.compare(p.y(), node.point.y());

        if (cmp < 0) {
            if (isVertical) {
                node.left = insert(node.left, p, !isVertical, xmin, ymin, node.point.x(), ymax);
            } else {
                node.left = insert(node.left, p, !isVertical, xmin, ymin, xmax, node.point.y());
            }
        } else {
            if (isVertical) {
                node.right = insert(node.right, p, !isVertical, node.point.x(), ymin, xmax, ymax);
            } else {
                node.right = insert(node.right, p, !isVertical, xmin, node.point.y(), xmax, ymax);
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

        int cmp = isVertical ? Double.compare(p.x(), node.point.x()) : Double.compare(p.y(), node.point.y());

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

        double firstDist, secondDist;
        Node firstNode, secondNode;

        if (isVertical) {
            firstNode = node.left;
            secondNode = node.right;
            firstDist = query.x() - node.point.x();
            secondDist = firstNode.rect.distanceTo(query);
        } else {
            firstNode = query.y() < node.point.y() ? node.left : node.right;
            secondNode = firstNode == node.left ? node.right : node.left;
            firstDist = query.y() - node.point.y();
            secondDist = firstNode.rect.distanceTo(query);
        }

        if (firstDist < 0) {
            firstNode = secondNode;
            secondNode = node.right;
        }

        closest = nearest(firstNode, query, closest, !isVertical);

        if (secondDist < closest.distanceTo(query)) {
            closest = nearest(secondNode, query, closest, !isVertical);
        }

        return closest;
    }
}
