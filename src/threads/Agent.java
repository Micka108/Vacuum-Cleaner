package threads;

public class Agent extends Thread {

    private Object sensors;

	public Agent() throws InterruptedException{
        super();
        
        
       
        
        //Sensor
        AgentSensors sensors = new AgentSensors(2, 2, new Environnement());
    }

    @Override
    public void run() {
        while(true){
        }
    }

}