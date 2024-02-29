package main;

import inputs.KeyboardInputs;
import inputs.MouseInput;

import javax.swing.*;
import java.awt.*;
import static main.Game.GAME_WIDTH;
import static main.Game.GAME_HIGHT;
public class GamePanel extends JPanel {
    private MouseInput mouseInput;
    private Game game;
    public GamePanel(Game game){
        mouseInput = new MouseInput(this);
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }
    private void setPanelSize(){
        Dimension size = new Dimension(GAME_WIDTH,GAME_HIGHT);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }
    public void updateGame(){

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }
    public Game getGame(){
        return game;
    }
}
