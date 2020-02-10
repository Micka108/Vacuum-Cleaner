import threads.Agent;
import threads.Environnement;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting");
        Environnement env = new Environnement(); //creating and starting the Environnement Thread
        Agent agent = new Agent(4, 4, env);
        agent.start();
        env.start();
    }
}