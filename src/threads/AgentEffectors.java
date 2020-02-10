package threads;

import java.util.Stack;

import search.Actions;
import search.Learning;
import visual.Window;

public class AgentEffectors {
    private Environnement env;
    public Stack<Actions> intentions;
    private Agent agent;
    public Learning learner;

    //Constructor
    public AgentEffectors(Environnement env, Agent agent) {
        super();
        this.env = env;
        this.agent = agent;
        //Initiate the intentions to Sleep until we have real intentions
        this.intentions = new Stack<Actions>();
        this.intentions.push(Actions.Sleep);
        this.learner = new Learning(this.env);
    }

    //Given an action, the Effectors will send the Action to the Environnement, and decide
    //if another exploration is needed (also trigger the learning if choosen by the user)
    public boolean doAction(Actions action) {
        if (action == Actions.Sleep) {
            try {
                this.env.changeGridState(this.agent.sensors.x, this.agent.sensors.y, Actions.Sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }
        else{
            //tell the Action to do to the Environnement
            //either returns a change of position, get back an x and y, update them in the sensors
            //or if he suck/pick an item, return the boolean to the Agent to start a new exploration
            if(action == Actions.MoveTop || action == Actions.MoveDown || action == Actions.MoveRight || action == Actions.MoveLeft){
                int[] newPos;
                try {
                    newPos = this.env.moveAgentOnGrid(this.agent.sensors.x, this.agent.sensors.y, action);
                    this.agent.sensors.updatePosition(newPos[0], newPos[1]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(Window.getLearning()){
                    return learner.nextStep();
                }
                return false;
            }
            else{
                boolean event = false;
                try {
                    event = this.env.changeGridState(this.agent.sensors.x, this.agent.sensors.y, action);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //the changeGridState method will return a boolean to tell
                //the effectors that an item has been picked/sucked, so the effectors can tell the agent to restart an exploration
                if(Window.getLearning()){
                    if(event){
                        learner.reset();
                        return event;
                    }
                    else{
                        return learner.nextStep();
                    }
                }
                return event;
            }
        }
    }
}