import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;

public class Main {
    public static void main(String[] args) throws Exception{
        String input100k = "src\\SomeInputs\\SomeInputs\\input100k.txt";
        String input1M = "src\\SomeInputs\\SomeInputs\\input1M.txt";
        //KdTreeVisualizer.main(test_path);
        //RangeSearchVisualizer.main(test_path);
        //NearestNeighborVisualizer.main(test_path);
        //KdTree new_tree = new KdTree();
        // Point2D new_point = new Point2D(102943.422, 960019.467);
        // System.out.println(new_tree.contains(new_point));
        // System.out.println(new_tree.size());
        // System.out.println(new_tree.isEmpty());
        // new_tree.insert(new_point);
        // new_tree.insert(new_point);
        // System.out.println(new_tree.size());
        // System.out.println(new_tree.isEmpty());
        // System.out.println(new_tree.isEmpty());
        // new_point = new Point2D(492470.049, -488525.6);
        // new_tree.insert(new_point);
        // new_point = new Point2D(745907.748, 578577.283);
        // new_tree.insert(new_point);
        // System.out.println(new_tree.size());
        // new_point = new Point2D(725907.748, 578577.283);
        // System.out.println(new_tree.nearest(new_point));
        // new_point = new Point2D(725907.748, 572577.283);
        // System.out.println(new_tree.nearest(new_point));
        int numberOfPoints = 10000000;
        int numberOfNearestSearches = 1000;
        In in = new In(input1M); // Provide the file path as an argument to the In constructor.
        List<String> input100kStrings = new ArrayList<>();

        while (!in.isEmpty()) {
            String line = in.readLine();
            input100kStrings.add(line);
        }

        // Create a KD-tree
        KdTree kdTree = new KdTree();
        PointSET pointSet = new PointSET();

        // Generate and insert random points
        
        for (String line : input100kStrings) {
            String[] lineSplit = line.split(" ");
            double x = Double.parseDouble(lineSplit[0]);
            double y = Double.parseDouble(lineSplit[1]);
            Point2D point = new Point2D(x, y);
            pointSet.insert(point);
            kdTree.insert(point);
        }
        
        // Query a random point to measure nearest neighbor search time
        //Point2D queryPoint = new Point2D(Math.random(), Math.random());
        
        Random random = new Random();
        Stopwatch timer = new Stopwatch();
        for (int i = 0; i < numberOfNearestSearches; i++) {
            double randomX = random.nextDouble();
            double randomY = random.nextDouble();
            Point2D randomPoint = new Point2D(randomX, randomY);
            Point2D nearestPoint = pointSet.nearest(randomPoint);           
        }
        double elapsedTime = timer.elapsedTime();
        Stopwatch timer2 = new Stopwatch();
        for (int i = 0; i < numberOfNearestSearches; i++) {
            double randomX = random.nextDouble();
            double randomY = random.nextDouble();
            Point2D randomPoint = new Point2D(randomX, randomY);
            Point2D nearestPoint = pointSet.nearest(randomPoint);           
        }
        double elapsedTime2 = timer2.elapsedTime();
        // Output results
        
        System.out.println("Find nearest Time PointSet: " + elapsedTime + " seconds for " + numberOfNearestSearches + " searches.");
        System.out.println("Find nearest Time KdTree: " + elapsedTime2 + " seconds for " + numberOfNearestSearches + " searches.");
        //System.out.println("KD-Tree Nearest Neighbor Search Time: " + elapsedTime + " seconds");
        //System.out.println("Query Point: " + queryPoint);
        //System.out.println("Nearest Neighbor: " + nearest);



    }
}