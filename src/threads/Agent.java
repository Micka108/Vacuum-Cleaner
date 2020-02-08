package threads;


import java.util.ArrayList;
import java.util.List;

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
	
	private Node BFS(Node start, ArrayList<Node> openSet, ArrayList<Node> closelSet) {
		
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
				if (child )
			}
		}
	}
	
	private Node[] expand(Node current) {
		
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
    		ArrayList<Node> closelSet = new ArrayList<Node>();
        }
    }

    
}