/**********************************************************************
 *  readme.txt template                                                   
 *  Kd-tree
**********************************************************************/

Name 1:    
kt 1:   Arnar Ægisson

Name 2:    
kt 2:   Eggert Hólm Pálsson

/**********************************************************************
 *  Briefly describe the Node data type you used to implement the
 *  2d-tree data structure.
 **********************************************************************/
Each Node stores a point and a corresponding rectangle. The tree is 
constructed such that at each level of the tree, nodes are split into 
vertical and horizontal subtrees alternately, depending on the orientation 
of the rectangle. The left and right references allow for efficient insertion, 
search, and nearest neighbor operations in the 2D-tree data structure.
/**********************************************************************
 *  Describe your method for range search in a kd-tree.
 **********************************************************************/
The KD-tree range search method efficiently finds all points within a 
specified rectangle by recursively checking nodes based on their associated 
rectangles. If a node's rectangle intersects the specified rectangle, it 
explores its children. Pruned branches result in an efficient search. 
The results are collected and returned.

/**********************************************************************
 *  Describe your method for nearest neighbor search in a kd-tree.
 **********************************************************************/
The method for nearest neighbor search in a KD-tree involves recursively 
traversing the tree to find the closest point to a given query point. 
At each node, it checks the distance between the point given and the 
current node's point and compares it to the distance to the best known
point found so far. It then determines which subtree to explore next 
based on the current node's orientation and its associated rectangle. 
This process continues, recursively narrowing down the search space, 
until the nearest neighbor is found.

/**********************************************************************
 *  Give the total memory usage in bytes (using tilde notation and 
 *  the standard 64-bit memory cost model) of your 2d-tree data
 *  structure as a function of the number of points N. Justify your
 *  answer below.
 *
 *  Include the memory for all referenced objects (deep memory),
 *  including memory for the nodes, points, and rectangles.
 **********************************************************************/

bytes per Point2D: 32 bytes

bytes per RectHV: 256 bytes

bytes per KdTree of N points (using tilde notation):   ~350N
[include the memory for any referenced Node, Point2D and RectHV objects]


/**********************************************************************
 *  Give the expected running time in seconds (using tilde notation)
 *  to build a 2d-tree on N random points in the unit square.
 *  Use empirical evidence by creating a table of different values of N
 *  and the timing results. (Do not count the time to generate the N 
 *  points or to read them in from standard input.)
 **********************************************************************/
points   time
1000     0.004s
10k      0.011s
100k     0.097s
1m       1.179s
10m     22.282s

/**********************************************************************
 *  How many nearest neighbor calculations can your brute-force
 *  implementation perform per second for input100K.txt (100,000 points)
 *  and input1M.txt (1 million points), where the query points are
 *  random points in the unit square? Explain how you determined the
 *  operations per second. (Do not count the time to read in the points
 *  or to build the 2d-tree.)
 *
 *  Repeat the question but with the 2d-tree implementation.
 **********************************************************************/

                     calls to nearest() per second
                     brute force           2d-tree

input100K.txt
Find nearest Time PointSet: 2.039 seconds for 1000 searches.
Find nearest Time PointSet: 6.168 seconds for 2000 searches.
Find nearest Time PointSet: 10.841 seconds for 4000 searches.
Find nearest Time PointSet: 17.843 seconds for 8000 searches.

Find nearest Time KdTree: 1.582 seconds for 1000 searches.
Find nearest Time KdTree: 5.992 seconds for 2000 searches.
Find nearest Time KdTree: 10.61 seconds for 4000 searches.
Find nearest Time KdTree: 16.601 seconds for 8000 searches.

input1M.txt
Find nearest Time PointSet: 28.587 seconds for 1000 searches.
Find nearest Time PointSet: 76.164 seconds for 2000 searches.
Find nearest Time PointSet: 167.593 seconds for 4000 searches.
Find nearest Time PointSet: 312.681 seconds for 8000 searches.

Find nearest Time KdTree: 23.177 seconds for 1000 searches.
Find nearest Time KdTree: 71.615 seconds for 2000 searches.
Find nearest Time KdTree: 175.011 seconds for 4000 searches.
Find nearest Time KdTree: 305.059 seconds for 8000 searches.


/**********************************************************************
 *  Known bugs / limitations.
 **********************************************************************/
K-D trees can become unbalanced, leading to reduced query performance.
Rebalancing a K-D tree can be complex and time-consuming.
It can be higly dependant og data distribution and can use a lot of memory
especially for large datasets.

/**********************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and d�mat�mar, but do
 *  include any help from people (including course staff, 
 *  classmates, and friends) and attribute them by name.
 **********************************************************************/
Discussions between me and Eggert, got a hint from Arnar Bjarni Arnarson 
from a question on Piazza when we where stuck.

/**********************************************************************
 *  Describe any serious problems you encountered.                    
 **********************************************************************/
We had trouble in gradescope, our first implementation assumed all points
to have x/y coordinates between 0 and 1.
That misunderstanding was corrected then everything worked better.

