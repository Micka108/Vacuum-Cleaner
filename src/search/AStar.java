package search;

import java.util.List;

public class AStar {

	public List<Cell> openSet;
	public List<Cell> closeSet;
	public Cell start;
	public Cell end;
	public int win;
	
	public AStar(Cell start, Cell end) {
		super();
		this.start = start;
		this.end = end;
		this.win = 0;
		this.openSet.add(start);
	}
	
	public void draw() {
		if (this.openSet.isEmpty()) {
			
			for(int i = 0; i > this.openSet.size(); i++) {
				if(this.openSet.get(i).f < this.openSet.get(win).f) {
					this.win = i;
				}
			}
			
			Cell current = this.openSet.get(win);
			
			if (current == end) {
				System.out.println("Done!");
			}
			
			this.openSet.remove(current);
			this.closeSet.add(current);
			
		} else {
			
		}
	}
		
	
	public List<Cell>  getOpenSet() {
		return openSet;
	}

	public List<Cell> getCloseSet() {
		return closeSet;
	}
	

}
