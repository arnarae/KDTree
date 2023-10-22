import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;

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
    // Adds a point to tree, uses the helper to do it recursively
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
                double xmax = Math.max(node.point.x(), rect.xmin());
                nextRect = new RectHV(rect.xmin(), rect.ymin(), xmax, rect.ymax());
                node.left = insertHelper(node.left, p, !isVertical, nextRect);
            } else {
                double xmin = Math.min(node.point.x(), rect.xmax());
                nextRect = new RectHV(xmin, rect.ymin(), rect.xmax(), rect.ymax());
                node.right = insertHelper(node.right, p, !isVertical, nextRect);
            }
        } else {
            cmp = Double.compare(p.y(), node.point.y());
            if (cmp < 0) {
                double ymax = Math.max(node.point.y(), rect.ymin());
                nextRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), ymax);
                node.left = insertHelper(node.left, p, !isVertical, nextRect);
            } else {
                double ymin = Math.min(node.point.y(), rect.ymax());
                nextRect = new RectHV(rect.xmin(), ymin, rect.xmax(), rect.ymax());
                node.right = insertHelper(node.right, p, !isVertical, nextRect);
            }
        }        
        return node;
    }
    

    // Checks if a point exists in the tree, uses the helper to do it recursively
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
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.point.draw();
            if (isVertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
            }
            draw(node.left, !isVertical);
            draw(node.right, !isVertical);
        }
    }
    // makes an ittarable out of the tree using Queue
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
    // Finds the neares point to given point in the tree, uses the helper to do it recursively, returns null if tree is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
    
        return nearestHelper(root, p, root.point, true);
    }
    
    private Point2D nearestHelper(Node node, Point2D query, Point2D closest, boolean isVertical) {
        if (node == null) {
            return closest;
        }
    
        double closestDist = closest.distanceSquaredTo(query);
        double currDist = node.point.distanceSquaredTo(query);
    
        if (currDist < closestDist) {
            closest = node.point;
        }
    
        RectHV rectToCheck = node.rect;
    
        if (isVertical) {
            if (query.x() < node.point.x()) {
                closest = nearestHelper(node.left, query, closest, !isVertical);
    
                if (node.right != null && rectToCheck.distanceSquaredTo(query) < closest.distanceSquaredTo(query)) {
                    closest = nearestHelper(node.right, query, closest, !isVertical);
                }
            } else {
                closest = nearestHelper(node.right, query, closest, !isVertical);
    
                if (node.left != null && rectToCheck.distanceSquaredTo(query) < closest.distanceSquaredTo(query)) {
                    closest = nearestHelper(node.left, query, closest, !isVertical);
                }
            }
        } else {
            if (query.y() < node.point.y()) {
                closest = nearestHelper(node.left, query, closest, !isVertical);
    
                if (node.right != null && rectToCheck.distanceSquaredTo(query) < closest.distanceSquaredTo(query)) {
                    closest = nearestHelper(node.right, query, closest, !isVertical);
                }
            } else {
                closest = nearestHelper(node.right, query, closest, !isVertical);
    
                if (node.left != null && rectToCheck.distanceSquaredTo(query) < closest.distanceSquaredTo(query)) {
                    closest = nearestHelper(node.left, query, closest, !isVertical);
                }
            }
        }
        
        return closest;
    }
}
    
