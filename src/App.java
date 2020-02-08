import threads.AgentSensors;
import threads.Environnement;
import visual.Window;

public class App {
	
	static AgentSensors ap;
	
    public static void main(String[] args) throws Exception {
        System.out.println("Starting");
        Environnement env = new Environnement(); //creating and starting the Environnement Thread
        env.start();
        //ap.heuristic();
        //Application.launch(Window.class, args);
    }
}