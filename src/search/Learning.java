package search;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.application.Platform;

import threads.Environnement;

import visual.Window;

public class Learning {
    private int steps;
    private int maxSteps;
    private int startScore;
    private Environnement env;

    public Learning(Environnement env) {
        //Setting up the learning values
        this.steps = 0;
        this.env = env;
        this.startScore = this.env.getScore();
        this.newMaxSteps();
    }

    //Increase the current steps done, and if the target is reached,
    //saves the iteration and start the next one
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
            this.newMaxSteps();
            this.steps = 0;
            this.startScore = this.env.getScore();
            return true;
        }
        return false;
    }

    private void newMaxSteps(){
        double prob = Math.random();
        //we take the best value of steps, calculated on previous iterations.
        //but half of the time we either reduce or increase the value to try new configurations
        try{
            if(prob >= 0.3){
                if(prob < 0.15){
                    this.maxSteps = this.getBestMean() +1;
                }
                else{
                    this.maxSteps = this.getBestMean() -1;
                }
            }
            else{
                this.maxSteps = this.getBestMean();
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        } 
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

    //Calculate mean of given values
    private long mean(JSONArray values) {
        long result = 0;
        for (int i = 0; i < values.size(); i++) {
            result += (long) values.get(i);
        }
        return result / values.size();
    }

    //Add the new score change in the right Array (key is number of steps) in the .json file
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

    //Chose the best number of steps depending on the values of previous iterations
    private int getBestMean() throws Exception {
        long bestMean = -9999;
        int bestSteps = 0;
        FileReader reader = new FileReader("src/search/learning.json");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        for(int i = 3; i<8; i++){
            JSONArray jsonArray = (JSONArray) jsonObject.get(Integer.toString(i));
            long mean = this.mean(jsonArray);
            if(mean > bestMean){
                bestMean = mean;
                bestSteps = i;
            }
        }
        return bestSteps;
    }

}