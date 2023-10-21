import edu.princeton.cs.algs4.RectHV;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
        if (isVertical) {
        cmp = Double.compare(p.x(), node.point.x());
        if (cmp < 0) {
            nextRect = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
            node.left = insert(node.left, p, !isVertical, nextRect);
        } else {
            nextRect = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            node.right = insert(node.right, p, !isVertical, nextRect);
        }
    } else {
        cmp = Double.compare(p.y(), node.point.y());
        if (cmp < 0) {
            nextRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
            node.left = insert(node.left, p, !isVertical, nextRect);
        } else {
            nextRect = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
            node.right = insert(node.right, p, !isVertical, nextRect);
        }
    }

    return node;
}
By ensuring that the coordinates for RectHV are non-negative, the provided insert method should work without errors, even for points with negative coordinates.






}
