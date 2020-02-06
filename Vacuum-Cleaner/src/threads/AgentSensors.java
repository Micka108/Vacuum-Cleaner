package threads;

public class AgentSensors {
    public int x;
    public int y;
    public Environnement env;

    public AgentSensors(int x, int y, Environnement env){
        this.x = x;
        this.y = y;
        this.env = env;
    }

    //connaitre element le plus proche sur la grille
    
   
    //methode pour savoir toutes les actions disponibles selon la case actuelle
    //droite/gauche/haut/bas avec gestion des limites, + aspiration et ramassage ????
}

