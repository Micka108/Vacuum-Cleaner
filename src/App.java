import threads.Agent;
import threads.AgentSensors;
import threads.Environnement;

public class App {
	
	static AgentSensors ap;
	
    public static void main(String[] args) throws Exception {
        System.out.println("Starting");
        Environnement env = new Environnement(); //creating and starting the Environnement Thread
        Agent agent = new Agent(4, 4, env);
        agent.start();
        env.start();
    }
}