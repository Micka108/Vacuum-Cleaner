package threads;

import java.util.ArrayList;
import java.util.List;

import search.Actions;
import search.Node;

public class Agent extends Thread {

    private AgentSensors sensors;
    private boolean informed;

	public Agent(int x, int y) throws InterruptedException{
        super();
        this.informed = false;
        //Sensors
        AgentSensors sensors = new AgentSensors(x, y, new Environnement());
    }
	
	private Node BFS(Node start, ArrayList<Node> openSet, ArrayList<Node> closeSet) {
		
		openSet.add(start);
		
		while (openSet.isEmpty() == false) {
			Node current = openSet.get(0);
			openSet.remove(0);
			if (goal(current.gridState) == true) {
				return current;
			}
			ArrayList<Node> children = new ArrayList<Node>();
			children = expand(current);
			for (Node child: children) {
				if (openSet.contains(child) == false || closeSet.contains(child) == false) {
					openSet.add(child);
				}
			}
			closeSet.add(current);
		}
		return null;
	}
	

	private Node Astar() {
		
		
		
		return null;
		
	}
	
	private ArrayList<Node> expand(Node current) {
        ArrayList<Actions> actions = this.sensors.availableActions(current.x, current.y, current.gridState);
        ArrayList<Node> children = new ArrayList<Node>();
        Node temp;
        int[][] tempGrid;
        for(Actions a: actions){
            switch(a){
                case MoveTop:
                    temp = new Node(current.x, current.y -1, current.gridState, current, 0, 0, a);
                    children.add(temp);
                case MoveDown:
                    temp = new Node(current.x, current.y +1, current.gridState, current, 0, 0, a);
                    children.add(temp);
                case MoveRight:
                    temp = new Node(current.x +1, current.y, current.gridState, current, 0, 0, a);
                    children.add(temp);
                case MoveLeft:
                    temp = new Node(current.x -1, current.y, current.gridState, current, 0, 0, a);
                    children.add(temp);
                case Suck:
                    tempGrid = current.gridState;
                    tempGrid[current.x][current.y] = 0;
                    temp = new Node(current.x, current.y, tempGrid, current, 0, 0, a);
                    children.add(temp);
                case Pick:
                    tempGrid = current.gridState;
                    tempGrid[current.x][current.y] -= 2;
                    temp = new Node(current.x, current.y, tempGrid, current, 0, 0, a);
                    children.add(temp);
            }
        }
        return children;
	}
	
	private boolean goal(int[][] gridState) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (gridState[i][j] != 0) {
                    return false;
                }
            }
        }
		return true;
	}

    @Override
    public void run() {
        while(true){
    		ArrayList<Node> openSet = new ArrayList<Node>();
    		ArrayList<Node> closeSet = new ArrayList<Node>();
    		
    		
        }
    }

    
}