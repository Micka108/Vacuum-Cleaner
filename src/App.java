import threads.Agent;
import threads.Environnement;

public class App {
    public static void main(String[] args) throws Exception {
        //Creating the Environnement Thread and the Agent Thread
        Environnement env = new Environnement();
        Agent agent = new Agent(4, 4, env);
        //Starting the Threads
        agent.start();
        env.start();
    }
}