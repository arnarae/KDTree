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
        root = insertHelper(root, p, true, new RectHV(0, 0, 1, 1));
    }

    private Node insertHelper(Node node, Point2D p, boolean isVertical, RectHV rect) {
        if (node == null) {
            size++;
            return new Node(p, rect);
        }

        if (node.point.equals(p)) {
            return node;
        }

        int cmp;
        RectHV nextRect;

        if (isVertical) {
            cmp = Double.compare(p.x(), node.point.x());
            if (cmp < 0) {
                nextRect = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
                node.left = insertHelper(node.left, p, !isVertical, nextRect);
            } else {
                nextRect = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                node.right = insertHelper(node.right, p, !isVertical, nextRect);
            }
        } else {
            cmp = Double.compare(p.y(), node.point.y());
            if (cmp < 0) {
                nextRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
                node.left = insertHelper(node.left, p, !isVertical, nextRect);
            } else {
                nextRect = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
                node.right = insertHelper(node.right, p, !isVertical, nextRect);
            }
        }
        
        return node;
    }


    public boolean contains(Point2D p) {
        return containsHelper(root, p, true);
    }

    private boolean containsHelper(Node node, Point2D p, boolean isVertical) {
        if (node == null) {
            return false;
        }

        if (node.point.equals(p)) {
            return true;
        }

        int cmp = isVertical ? Double.compare(p.x(), node.point.x()) : Double.compare(p.y(), node.point.y());

        if (cmp < 0) {
            return containsHelper(node.left, p, !isVertical);
        } else {
            return containsHelper(node.right, p, !isVertical);
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
    
        double closestDist = closest.distanceSquaredTo(query);
        double currDist = node.point.distanceSquaredTo(query);
    
        if (currDist < closestDist) {
            closest = node.point;
        }
    
        RectHV rectToCheck = null; // Initialize a variable to check if the node's rect is null
        if (node.rect != null) {
            rectToCheck = node.rect;
        }
    
        if (isVertical) {
            if (query.x() < node.point.x()) {
                closest = nearest(node.left, query, closest, !isVertical);
                if (node.right != null && rectToCheck != null && node.right.rect.distanceSquaredTo(query) < closest.distanceSquaredTo(query)) {
                    closest = nearest(node.right, query, closest, !isVertical);
                }
            } else {
                closest = nearest(node.right, query, closest, !isVertical);
                if (node.left != null && rectToCheck != null && node.left.rect.distanceSquaredTo(query) < closest.distanceSquaredTo(query)) {
                    closest = nearest(node.left, query, closest, !isVertical);
                }
            }
        } else {
            if (query.y() < node.point.y()) {
                closest = nearest(node.left, query, closest, !isVertical);
                if (node.right != null && rectToCheck != null && node.right.rect.distanceSquaredTo(query) < closest.distandistanceSquaredToeTo(query)) {
                    closest = nearest(node.right, query, closest, !isVertical);
                }
            } else {
                closest = nearest(node.right, query, closest, !isVertical);
                if (node.left != null && rectToCheck != null && node.left.rect.distanceSquaredTo(query) < closest.distanceSquaredTo(query)) {
                    closest = nearest(node.left, query, closest, !isVertical);
                }
            }
        }
    
        return closest;
    }
}
    
