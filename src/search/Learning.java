package search;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Platform;

import threads.Environnement;

import visual.Window;

public class Learning {
    private int steps;
    private int maxSteps;
    private int startScore;
    private Environnement env;

    public Learning(Environnement env) {
        this.steps = 0;
        this.env = env;
        this.startScore = this.env.getScore();
    }

    public boolean nextStep() {
        this.steps++;
        if (this.steps == this.maxSteps) {
            int scoreDiff = this.env.getScore() - this.startScore;
            try{
                this.addNewScore(this.maxSteps, scoreDiff);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            // plus tard changer pour meilleur moyenne (avec 50% de changer, 50% de chance
            // de +1 -1)
            Random rand = new Random();
            this.maxSteps = rand.nextInt(7)+1; // between 3 and 7
            this.steps = 0;
            this.startScore = this.env.getScore();
            return true;
        }
        return false;
    }

    //We reset the learner each time we start an exploration (i.e. when we get out of state SLEEP)
    //We also reset it each time a element is picked/sucked.
    public void reset() {
        int scoreDiff = this.env.getScore() - this.startScore;
        if(this.steps != 0){
            try{
                this.addNewScore(this.steps, scoreDiff);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        this.steps = 0;
        this.startScore = this.env.getScore();
    }

    private int mean(JSONArray values) {
        int result = 0;
        for (int i = 0; i < values.size(); i++) {
            result += (int) values.get(i);
        }
        return result / values.size();
    }

    private void addNewScore(int steps, int score) throws Exception {
        Platform.runLater(() -> {
            Window.printDirection("With " + steps + " steps, score changed : " + score);
        });
        FileReader reader = new FileReader("src/search/learning.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        JSONArray jsonArray = (JSONArray) jsonObject.get(Integer.toString(steps));
        jsonArray.add(score);
        reader.close();
        FileWriter file = new FileWriter("src/search/learning.json");
        file.write(jsonObject.toJSONString());
        file.flush();
    }

    private int getBestMean() throws Exception {
        int bestMean = -9999;
        int bestSteps = 0;
        FileReader reader = new FileReader("src/search/learning.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        for(int i = 3; i<8; i++){
            JSONArray jsonArray = (JSONArray) jsonObject.get(Integer.toString(i));
            int mean = this.mean(jsonArray);
            if(mean > bestMean){
                bestMean = mean;
                bestSteps = i;
            }
        }
        return bestSteps;
    }

}