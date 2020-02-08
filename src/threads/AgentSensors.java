package threads;

import search.Node;
import search.Position;
import java.lang.Math;

import javafx.scene.layout.GridPane;
import visual.Direction;
import visual.Window;

public class AgentSensors {
    public int x;
    public int y;
    public Environnement env; 

    public AgentSensors(int x, int y, Environnement env) throws InterruptedException{
        this.x = x;
        this.y = y;
        this.env = env;
    }

    //connaitre element le plus proche sur la grille
    // Manhattan distance
    public int heuristic(Position cell, Position goal) {
    	int dx = Math.abs(cell.x - goal.x);
    	int dy = Math.abs(cell.y - goal.y);
    	int d = (dx + dy);
		return d;
    }
   
    public void f(int g, int h) {
    	//this.f = g + h;
    }
    
    //methode pour savoir toutes les actions disponibles selon la case actuelle
    //droite/gauche/haut/bas avec gestion des limites, + aspiration et ramassage ????
    public void directionRobot(Position robot, Position goal) {
    	if (robot.x > goal.x) {
    		robot.x--;
    		Window.moveRobot(Direction.Left);
    	}
    	if (robot.x < goal.x) {
    		robot.x++;
    		Window.moveRobot(Direction.Right);
    	}
    	if (robot.y < goal.y) {
    		robot.x--;
    		Window.moveRobot(Direction.Top);
    	}
    	if (robot.y > goal.y) {
    		robot.x++;
    		Window.moveRobot(Direction.Down);
    	}
    }
}

