package search;

import java.util.List;

public class Cell {
	public int g;
	public int h;
	public int f;
	public int v;
	public List<Cell> neighbors;
	private int x;
	private int y;
	
	public Cell(int v, int x, int y) {
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.v = v;
        this.x = y;
        this.y = y;
	}
	
	public void getNeighbors(Cell[][] grid) {
		this.neighbors.add(grid[x+1][y]);
		this.neighbors.add(grid[x-1][y]);
		this.neighbors.add(grid[x][y+1]);
		this.neighbors.add(grid[x][y-1]);
	}
	
}