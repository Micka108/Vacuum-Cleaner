package threads;

import java.util.ArrayList;

import search.Node;

public class Agent extends Thread {

    private AgentSensors sensors;
    public ArrayList<Node> perents;

	public Agent(int x, int y) throws InterruptedException{
        super();
        //Sensors
        AgentSensors sensors = new AgentSensors(x, y, new Environnement());
    }

    @Override
    public void run() {
        while(true){
        }
    }

}