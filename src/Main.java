import edu.princeton.cs.algs4.Point2D;

public class Main {
    public static void main(String[] args) throws Exception{
        //String[] test_path = {"src\\SomeInputs\\SomeInputs\\circle100.txt"};
        //KdTreeVisualizer.main(test_path);
        //RangeSearchVisualizer.main(test_path);
        //NearestNeighborVisualizer.main(test_path);
        KdTree new_tree = new KdTree();
        Point2D new_point = new Point2D(102943.422, 960019.467);
        System.out.println(new_tree.contains(new_point));
        System.out.println(new_tree.size());
        System.out.println(new_tree.isEmpty());
        new_point = new Point2D(492470.049, -488525.6);
        new_tree.insert(new_point);
        new_tree.insert(new_point);
        System.out.println(new_tree.size());
        System.out.println(new_tree.isEmpty());
        System.out.println(new_tree.isEmpty());
        new_point = new Point2D(492470.049, -488525.6);
        new_tree.insert(new_point);
        new_point = new Point2D(745907.748, 578577.283);
        new_tree.insert(new_point);
        System.out.println(new_tree.size());

    }
}