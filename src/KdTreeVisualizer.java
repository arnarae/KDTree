import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class KdTreeVisualizer {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java KdTreeVisualizer <inputFilePath>");
            return;
        }

        String inputFilePath = args[0];
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();

        try {
            In in = new In(inputFilePath);
            while (!in.isEmpty()) {
                double x = in.readDouble();
                double y = in.readDouble();
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.show();
                }
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }

        // Keep the window open to view the KdTree
        while (true) {
            StdDraw.pause(50);
        }
    }
}
