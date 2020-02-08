package threads;

public class Agent extends Thread {

    private AgentSensors sensors;

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