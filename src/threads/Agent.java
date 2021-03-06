package threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import search.Actions;
import search.Node;
import visual.Window;
import search.Desire;
import search.Learning;
import javafx.application.Platform;

public class Agent extends Thread {

    //Agent's Beliefs : its position and the state of the grid
    public AgentSensors sensors;
    //Agent's Effectors
    private AgentEffectors effectors;
    //Agent's Desire :
    //SLEEP if the grid is clean
    //CLEAN to start working and launch exploration, to create our intentions list : Actions to give to its Effectors
    public Desire desire;
    //Boolean to know if the agent must restart an exploration
    private boolean explore;
    //Agent's intentions
    private Stack<Actions> intentions;

    //Constructor
	public Agent(int x, int y, Environnement env) throws InterruptedException{
        super();
        this.explore = true;
        //Sensors
        this.sensors = new AgentSensors(x, y, env);
        //Effectors
        this.effectors = new AgentEffectors(env, this);
    }
    
    //BFS exploration
	private Node BFS(Node start, ArrayList<Node> openSet, ArrayList<Node> closeSet) {
        openSet.add(start);
        //Looping until the goal is achieved or the openSet in empty 
		while (openSet.isEmpty() == false) {
			Node current = openSet.get(0);
			openSet.remove(0);
			if (goal(current.gridState) == true) {
				return current;
            }
			ArrayList<Node> children = new ArrayList<Node>();
            children = expand(current);
			for (Node child: children) {
                //prevent loops in the tree
				if (openSet.contains(child) == false && closeSet.contains(child) == false) {
					openSet.add(child);
				}
			}
			closeSet.add(current);
		}
		return null;
	}
	

    //A* exploration : same than BFS method, only sorting the openSet depending on heuristic
	private Node Astar(Node start, ArrayList<Node> openSet, ArrayList<Node> closeSet) {
		openSet.add(start);
		while (openSet.isEmpty() == false) {
            //sort OpenSet depending on heuristic
            Collections.sort(openSet, (node1, node2) -> node1.f - node2.f);

			Node current = openSet.get(0);
			openSet.remove(0);
			if (goal(current.gridState) == true) {
				return current;
            }
			ArrayList<Node> children = new ArrayList<Node>();
            children = expandAStar(current);
			for (Node child: children) {
				if (openSet.contains(child) == false && closeSet.contains(child) == false) {
					openSet.add(child);
				}
			}
			closeSet.add(current);
		}
		return null;
		
	}
    
    //Basic expand method: creates children Nodes depending on the state of the grid for the Parent Node,
    // the Agent position, and the possible actions in this state.
	private ArrayList<Node> expand(Node current) {
        ArrayList<Actions> actions = this.sensors.availableActions(current.x, current.y, Environnement.deepCopyOfGrid(current.gridState));
        ArrayList<Node> children = new ArrayList<Node>();
        Node temp = null;
        int[][] tempGrid;
        for(Actions a: actions){
            if(a == Actions.MoveTop){
                temp = new Node(current.x, current.y -1, Environnement.deepCopyOfGrid(current.gridState), current, 0, 0, a);
            } else if(a == Actions.MoveDown){
                temp = new Node(current.x, current.y +1, Environnement.deepCopyOfGrid(current.gridState), current, 0, 0, a);
            } else if(a == Actions.MoveRight){
                temp = new Node(current.x +1, current.y, Environnement.deepCopyOfGrid(current.gridState), current, 0, 0, a);
            } else if(a == Actions.MoveLeft){
                temp = new Node(current.x -1, current.y, Environnement.deepCopyOfGrid(current.gridState), current, 0, 0, a);
            } else if(a == Actions.Suck){
                tempGrid = Environnement.deepCopyOfGrid(current.gridState);
                tempGrid[current.x][current.y] = 0;
                temp = new Node(current.x, current.y, tempGrid, current, 0, 0, a);
            } else if(a == Actions.Pick){
                tempGrid = Environnement.deepCopyOfGrid(current.gridState);
                tempGrid[current.x][current.y] -= 2;
                temp = new Node(current.x, current.y, tempGrid, current, 0, 0, a);
            }
            children.add(temp);
        }
        return children;
    }
    
    //Same than previous method, only taking heuristic in account
    private ArrayList<Node> expandAStar(Node current) {
        ArrayList<Actions> actions = this.sensors.availableActions(current.x, current.y, Environnement.deepCopyOfGrid(current.gridState));
        ArrayList<Node> children = new ArrayList<Node>();
        Node temp = null;
        int[][] tempGrid;
        for(Actions a: actions){
            if(a == Actions.MoveTop){
                temp = new Node(current.x, current.y -1, Environnement.deepCopyOfGrid(current.gridState), current, current.g+1, this.sensors.closestElement(current.x, current.y-1, current.gridState), a);
            } else if(a == Actions.MoveDown){
                temp = new Node(current.x, current.y +1, Environnement.deepCopyOfGrid(current.gridState), current, current.g+1, this.sensors.closestElement(current.x, current.y+1, current.gridState), a);
            } else if(a == Actions.MoveRight){
                temp = new Node(current.x +1, current.y, Environnement.deepCopyOfGrid(current.gridState), current, current.g+1, this.sensors.closestElement(current.x+1, current.y, current.gridState), a);
            } else if(a == Actions.MoveLeft){
                temp = new Node(current.x -1, current.y, Environnement.deepCopyOfGrid(current.gridState), current, current.g+1, this.sensors.closestElement(current.x-1, current.y, current.gridState), a);
            } else if(a == Actions.Suck){
                tempGrid = Environnement.deepCopyOfGrid(current.gridState);
                tempGrid[current.x][current.y] = 0;
                temp = new Node(current.x, current.y, tempGrid, current, current.g+1,  this.sensors.closestElement(current.x, current.y, tempGrid), a);
            } else if(a == Actions.Pick){
                tempGrid = Environnement.deepCopyOfGrid(current.gridState);
                tempGrid[current.x][current.y] -= 2;
                temp = new Node(current.x, current.y, tempGrid, current, current.g+1,  this.sensors.closestElement(current.x, current.y, tempGrid), a);
            }
            children.add(temp);
        }
        return children;
	}
    
    //Check if the Grid given by the Node is our goal : a clean Grid
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
    
    //Takes all explored Nodes and the goal Node to rebuild the track of Actions
    //Return the result as a Stack (LIFO)
    private Stack<Actions> rebuildActions(Node goal, ArrayList<Node> closedSet){
        Stack<Actions> intentions = new Stack<Actions>();
        intentions.push(goal.action);
        Node current = goal.parent;
        while(current.parent != null){
            intentions.push(current.action);
            current = current.parent;
        }
        return intentions;
    }


    @Override
    public void run() {
        while(true){
            //Observe the Environnement and change his Desire
            this.desire = this.sensors.observe();
            if(this.desire == Desire.SLEEP){
                //this.explore = false;
                this.effectors.doAction(Actions.Sleep);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            else if(this.desire == Desire.CLEAN && this.explore){
                if(Window.getLearning()){
                    this.effectors.learner.reset();
                }
                //Explore() : choose between BFS and A* and retrieve a list of Nodes
                //to create a list of Actions to send to its Effectors
                ArrayList<Node> openSet = new ArrayList<Node>();
                ArrayList<Node> closeSet = new ArrayList<Node>();
                Node goal;

                if(!Window.getExplore()){
                    //BFS
                    Node start = new Node(this.sensors.x, this.sensors.y, this.sensors.env.getGrid(), null, 0, 0, null);
                    goal = this.BFS(start, openSet, closeSet);
                }
                else{
                    //A*
                    Node start = new Node(this.sensors.x, this.sensors.y, this.sensors.env.getGrid(), null, 0, this.sensors.closestElement(this.sensors.x, this.sensors.y, this.sensors.env.getGrid()), null);
                    goal = this.Astar(start, openSet, closeSet);
                }
                this.explore = false;
                this.intentions = this.rebuildActions(goal, closeSet);
                //Window.printDirection("Intentions :"+intentions.toString());
                    
                while(!this.intentions.isEmpty() && !this.explore){
                    Actions action = this.intentions.pop();
                    this.explore = this.effectors.doAction(action);
                    //Cooldown after an Action
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }    
}

