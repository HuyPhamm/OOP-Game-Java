package object;

import java.awt.Graphics;
import java.util.ArrayList;

import entities.Player;
import gamestates.Playing;
import levels.Level;

public class ObjectManager {

    private Playing playing;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public ObjectManager(Playing playing) {
        this.playing = playing;
    }


    public void loadObjects(Level newLevel) {
        bullets.clear();
    }


    public void update(int[][] lvlData, Player player) {

    }


    public void draw(Graphics g, int xLvlOffset) {
//        drawBullets(g, xLvlOffset);
    }


    public void resetAllObjects() {
//        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Bullet b : bullets)
            b.reset();
    }
}