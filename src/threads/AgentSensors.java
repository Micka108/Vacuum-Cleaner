package threads;

import java.lang.Math;
import java.util.ArrayList;

import search.Actions;

public class AgentSensors {
    public int x;
    public int y;
    public Environnement env; 

    public AgentSensors(int x, int y, Environnement env) throws InterruptedException{
        this.x = x;
        this.y = y;
        this.env = env;
	}
	
	public void updatePosition(int x, int y){
		this.x = x;
		this.y = y;
	}

    // Manhattan distance
    public int heuristic(int x, int y, int xGoal, int yGoal) {
    	int dx = Math.abs(x - xGoal);
    	int dy = Math.abs(y - yGoal);
		return dx + dy;
    }
    
    //methode pour savoir toutes les actions disponibles selon la case actuelle
    public ArrayList<Actions> availableActions(){
		ArrayList<Actions> actions = new ArrayList<Actions>();
		//Do not get out of boundaries
		if(this.x != 0){
			actions.add(Actions.MoveLeft);
		}
		if(this.y != 0){
			actions.add(Actions.MoveTop);
		}
		if(this.x != 4){
			actions.add(Actions.MoveRight);
		}
		if(this.y != 4){
			actions.add(Actions.MoveDown);
		}
		//check grid state to know if Sucking action or Picking action is needed
		int[][] grid = this.env.getGrid();
		if(grid[this.x][this.y] == 2 || grid[this.x][this.y] == 3){
			actions.add(Actions.Pick);
		}
		if(grid[this.x][this.y] == 1 || grid[this.x][this.y] == 3){
			actions.add(Actions.Suck);
		}
		return actions;
	}
    
}

