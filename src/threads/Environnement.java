package threads;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import visual.Direction;
import visual.Window;
import javafx.stage.Stage;
import javafx.application.Platform;
import search.Actions;;

public class Environnement extends Thread {
    private Stage stage ;
	private int[][] grid	; // values 0: empty, 1: dust, 2: jewel, 3:both
    private int score;
    private int actions;
    private int dusts;
    private int suckedDusts;
    private int jewels;
    private int pickedJewels;
    private int suckedJewels;
    private List<String> events;
    private Window window;

    public Environnement() throws InterruptedException {
        super();
        this.grid = new int[5][5];
        for (int[] row : this.grid) {
            Arrays.fill(row, 0);
        }
        this.score = 0;
        this.actions = 0;
        this.dusts = 0;
        this.suckedDusts = 0;
        this.jewels = 0;
        this.pickedJewels = 0;
        this.suckedJewels = 0;
        
        this.events = new ArrayList<String>();
        this.execWindow();
        Window.printDirection("Creating the Environnement");
    }
    
    private void execWindow() throws InterruptedException {
    	this.window = ((Window) this.window);
        new Thread(() -> Application.launch(Window.class)).start();
        Window.awaitFXToolkit();
        Window.initRobot(4, 4);
    }
    
    private void generate() throws InterruptedException {
        double prob = Math.random();
        if (prob >= 0.10 && this.isGridFullOfDust() == false) {
            Random rand = new Random();
            int x;
            int y;
            do {
                x = rand.nextInt(5);
                y = rand.nextInt(5);
            } while (this.grid[x][y] == 1 || this.grid[x][y] == 3);
            this.changeGridState(x, y, Actions.NewDust);
        } else if (prob < 0.10 && this.isGridFullOfJewels() == false) {
            Random rand = new Random();
            int x;
            int y;
            do {
                x = rand.nextInt(5);
                y = rand.nextInt(5);
            } while (this.grid[x][y] == 2 || this.grid[x][y] == 3);
            this.changeGridState(x, y, Actions.NewJewel);
        }
    }

    public void updateScore() {
        this.score = (this.actions * -1) + (this.suckedDusts * 5) + (this.dusts * -1) + (this.pickedJewels * 10)
                + (this.suckedJewels * -50);
        int[] scores = {this.actions, this.dusts, this.suckedDusts, this.jewels, this.suckedJewels, this.pickedJewels, this.score};
        Window.setScore(scores);
    }

    public boolean isGridEmpty(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.grid[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isGridFullOfDust() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.grid[i][j] == 0 || this.grid[i][j] == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isGridFullOfJewels() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.grid[i][j] == 0 || this.grid[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public void changeGridState(int x, int y, Actions keyword) throws InterruptedException {
        // keywords : newDust -> +1, if dust already here does nothing
        // newJewel -> +2, if jewel already does nothing
        // suck -> check value, set it to 0, check if jewel was sucked.
        // pick -> check value, -2 if jewel was here, otherwise nothing
        // TO ADD : moves from the robot
        // ******************************************************************************************
        if (keyword == Actions.NewDust && (this.grid[x][y] != 1 || this.grid[x][y] != 3)) {
            this.dusts++;
            this.grid[x][y] += 1;
            Window.printDirection("New dust at ("+x+";"+y+")");
            Platform.runLater(
                () -> {
                    Window.addDirt(x, y);
                }
            );
        } else if (keyword == Actions.NewJewel && (this.grid[x][y] != 2 || this.grid[x][y] != 3)) {
            this.jewels++;
            this.grid[x][y] += 2;
            Window.printDirection("New jewel at ("+x+";"+y+")");
            Platform.runLater(() -> {Window.addJewel(x, y);});
        } else if (keyword == Actions.Suck) {
            this.actions++;
            if (this.grid[x][y] == 3) {
                this.suckedDusts++;
                this.suckedJewels++;
                this.grid[x][y] = 0;
                Platform.runLater(
                    () -> {
                        Window.removeDirt(x, y);
                        Window.removeJewel(x, y);
                    }
                );
            } else if (this.grid[x][y] == 2) {
                this.suckedJewels++;  	
                this.grid[x][y] = 0;
                Platform.runLater(
                    () -> {
                        Window.removeJewel(x, y);
                    }
                );
            } else if (this.grid[x][y] == 1) {
                this.suckedDusts++;
                this.grid[x][y] = 0;
                Platform.runLater(
                    () -> {
                        Window.removeDirt(x, y);
                    }
                );
            }
        } else if (keyword == Actions.Pick) {
            this.actions++;
            if (this.grid[x][y] == 2 || this.grid[x][y] == 3) {
                this.pickedJewels++;
                this.grid[x][y] -= 2;
                Platform.runLater(
                    () -> {
                        Window.removeJewel(x, y);
                    }
                );
            }
        }
        this.updateScore();
    }

    @Override
    public void run() {
        while(true){
            try {
                this.generate();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
                //e1.printStackTrace();
                ;
			}
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}