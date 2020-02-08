package threads;

import java.util.Stack;

import search.Actions;

public class AgentEffectors {
    private Environnement env;
    public Stack<Actions> intentions;
    private Agent agent;

    public AgentEffectors(Environnement env, Agent agent) {
        super();
        this.env = env;
        this.agent = agent;
        //Initiate the intentions to Sleep until we have real intentions
        this.intentions = new Stack<Actions>();
        this.intentions.push(Actions.Sleep);
    }

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
                return event;
            }
        }
    }
}