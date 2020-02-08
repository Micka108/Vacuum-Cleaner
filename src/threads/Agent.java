package threads;

public class Agent extends Thread {

    private AgentSensors sensors;
    private boolean informed;

	public Agent(int x, int y) throws InterruptedException{
        super();
        this.informed = false;
        //Sensors
        AgentSensors sensors = new AgentSensors(x, y, new Environnement());
    }

    @Override
    public void run() {
        while(true){
        }
    }

}