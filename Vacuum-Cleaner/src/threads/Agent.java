package threads;

public class Agent extends Thread {

    private Object sensors;

	public Agent(){
        super();
        
        // Sensor
        this.sensors = {
                environment:new EnvironmentSensor(),
                position:new PositionSensor()
        }
    }

    @Override
    public void run() {
        while(true){
        }
    }

}