package threads;

import java.util.*;

public class Environnement extends Thread {
    private int[][] grid; // values 0: empty, 1: dust, 2: jewel, 3:both
    private int score;
    private int actions;
    private int dusts;
    private int suckedDusts;
    private int jewels;
    private int pickedJewels;
    private int suckedJewels;
    private List<String> events;

    public Environnement() {
        super();
        System.out.println("Creating the Environnement");
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
    }

    private void generate() {
        double prob = Math.random();
        if (prob >= 0.10 && this.isGridFullOfDust() == false) {
            Random rand = new Random();
            int x;
            int y;
            do {
                x = rand.nextInt(5);
                y = rand.nextInt(5);
            } while (this.grid[x][y] == 1 || this.grid[x][y] == 3);
            this.changeGridState(x, y, "newDust");
        } else if (prob < 0.10 && this.isGridFullOfJewels() == false) {
            Random rand = new Random();
            int x;
            int y;
            do {
                x = rand.nextInt(5);
                y = rand.nextInt(5);
            } while (this.grid[x][y] == 2 || this.grid[x][y] == 3);
            this.changeGridState(x, y, "newJewel");
        }
    }

    public void updateScore() {
        this.score = (this.actions * -1) + (this.suckedDusts * 5) + (this.dusts * -1) + (this.pickedJewels * 10)
                + (this.suckedJewels * -50);
        //update score visual
        return;
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
                if (this.grid[i][j] != 1 || this.grid[i][j] != 3) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isGridFullOfJewels() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (this.grid[i][j] != 2 || this.grid[i][j] != 3) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public void changeGridState(int x, int y, String keyword) {
        // keywords : newDust -> +1, if dust already here does nothing
        // newJewel -> +2, if jewel already does nothing
        // suck -> check value, set it to 0, check if jewel was sucked.
        // pick -> check value, -2 if jewel was here, otherwise nothing
        // TO ADD : moves from the robot
        // ******************************************************************************************
        if (keyword == "newDust" && (this.grid[x][y] != 1 || this.grid[x][y] != 3)) {
            this.dusts++;
            this.grid[x][y] += 1;
            System.out.println("New dust at "+x+";"+y);
        } else if (keyword == "newJewel" && (this.grid[x][y] != 2 || this.grid[x][y] != 3)) {
            this.jewels++;
            this.grid[x][y] += 2;
            System.out.println("New jewel at "+x+";"+y);
        } else if (keyword == "suck") {
            this.actions++;
            if (this.grid[x][y] == 3) {
                this.suckedDusts++;
                this.suckedJewels++;
                this.grid[x][y] = 0;
            } else if (this.grid[x][y] == 2) {
                this.suckedJewels++;
                this.grid[x][y] = 0;
            } else if (this.grid[x][y] == 1) {
                this.suckedDusts++;
                this.grid[x][y] = 0;
            }
        } else if (keyword == "pick") {
            this.actions++;
            if (this.grid[x][y] == 2 || this.grid[x][y] == 3) {
                this.pickedJewels++;
                this.grid[x][y] -= 2;
            }
        }
        this.updateScore();
    }

    @Override
    public void run() {
        while(true){
            this.generate();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}