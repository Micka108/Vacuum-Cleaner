package threads;

import java.lang.Math;
import search.Actions;

public class AgentSensors {
    public int x;
    public int y;
    public Environnement env; 

    public AgentSensors(int x, int y, Environnement env) throws InterruptedException{
        this.x = x;
        this.y = y;
        this.env = env;
    }

    // Manhattan distance
    public int heuristic(int x, int y) {
    	int dx = Math.abs(this.x - x);
    	int dy = Math.abs(this.y - y);
		return dx + dy;
    }
    
    //methode pour savoir toutes les actions disponibles selon la case actuelle
    //droite/gauche/haut/bas avec gestion des limites, + aspiration et ramassage ????
    
}

