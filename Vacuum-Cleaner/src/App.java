import threads.Environnement;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting");
        Environnement env = new Environnement(); //creating and starting the Environnement Thread
        env.start();
    }
}