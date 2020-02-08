package search;


public class Node {
	public int f;
	public int g;
	public int h;
	public int x;
	public int y;
	public int[][] gridState;
    public Node parent;
    
    public Node(int x, int y, int[][] gridState, Node parent, int g, int h){
        this.x = x;
        this.y = y;
        this.gridState = gridState;
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

}
