package search;

public class Position {
	public int x;
	public int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void posValidation() {
		if (this.x > 5 || this.x < 0) {
			System.err.println("Error: We exit the grid through x");
		}
		if (this.y > 5 || this.y < 0) {
			System.err.println("Error: We exit the grid through y");
		}
	}		
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int[] getPos() {
		int[] pos = {x, y};
		return pos;
	}
	
}
