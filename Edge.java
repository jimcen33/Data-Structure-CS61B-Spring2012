/*This is an edge representation for part 2.
 *It stores the origin and destination vertex with corresponding weight.
 */
  
public class Edge {
	Object origin;
	Object dest;
	int weight;

/*Constructor of Edge.
 *@param o is the origin of the edge.
 *@param d is the destination of the edge.
 *@param w is the weight of this edge.
 */
    public Edge(Object o, Object d, int w){
    	origin=o;
    	dest=d;
    	weight=w;
    }