
/****************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    
 *  Dependencies:
 *  Author:
 *  Date:
 *
 *  Data structure for maintaining a set of 2-D points, 
 *    including rectangle and nearest-neighbor queries
 *
 *************************************************************************/

import java.util.Arrays;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

public class PointSET {
    // construct an empty set of points
    private SET<Point2D> pointSet;
    public PointSET() {
        pointSet = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return (size() == 0);
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!contains(p)){
            pointSet.add(p);
        }
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        for (Point2D point : pointSet){
            StdDraw.point(point.x(), point.y());
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> pointsInRect = new SET<Point2D>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                pointsInRect.add(p);
            }
        }
        return pointsInRect;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()){
            return null;
        } else {
            Point2D nearestPoint = null;
            double minDistance = Double.POSITIVE_INFINITY;

            for (Point2D point : pointSet) {
                double newDistance = p.distanceTo(point);
                if (newDistance < minDistance) {
                    minDistance = newDistance;
                    nearestPoint = point;
                }
            }
            return nearestPoint;
        }
    }

    public static void main(String[] args) {
    }

}
