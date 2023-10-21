import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class NearestNeighborVisualizer {

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        int pointCount = 0;

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
            pointCount++;
        }

        while (true) {
            // Check if the KdTree is empty before nearest neighbor search
            if (pointCount > 0) {
                // the location (x, y) of the mouse
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                Point2D query = new Point2D(x, y);

                // draw all of the points
                StdDraw.clear();
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(.01);
                brute.draw();

                // draw in red the nearest neighbor according to the brute-force algorithm
                StdDraw.setPenRadius(.03);
                StdDraw.setPenColor(StdDraw.RED);
                brute.nearest(query).draw();
                StdDraw.setPenRadius(.02);

                // draw in blue the nearest neighbor according to the kd-tree algorithm
                StdDraw.setPenColor(StdDraw.BLUE);
                kdtree.nearest(query).draw();
                StdDraw.show(0);
                StdDraw.show(40);
            } else {
                // Handle the case when the KdTree is empty
                StdDraw.clear();
                StdDraw.text(0.5, 0.5, "KdTree is empty");
                StdDraw.show(0);
                StdDraw.show(40);
            }
        }
    }
}
