package gamestates;

import UI.MenuButton;
import main.Game;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;
    public State(Game game){
        this.game = this.game;
    }
    public boolean isIn(MouseEvent e, MenuButton mb){
        return  mb.getBounds().contains(e.getX(),e.getY());
    }
    public Game getGame(){
        return game;
    }
}
