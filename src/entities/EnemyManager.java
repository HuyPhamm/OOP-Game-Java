package entities;

import gamestates.Playing;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] fireAnimations;
    private ArrayList<FireDemon> fireDemons = new ArrayList<>();
    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        fireDemons = LoadSave.GetFireDemon();
    }

    public void update(int[][] lvlData){
        for(FireDemon d : fireDemons){
            d.update(lvlData);
        }
    }

    public void draw(Graphics g,int xLvlOffset){
        drawDemons(g,xLvlOffset);
    }

    private void drawDemons(Graphics g, int xLvlOffset) {
        for(FireDemon d : fireDemons){
            g.drawImage(fireAnimations[d.getEnemyState()][d.getAniIndex()],(int) d.getHitBox().x- xLvlOffset - FIRE_DEMON_DRAWOFFSET_X, (int) d.getHitBox().y - FIRE_DEMON_DRAWOFFSET_Y, DEMON_WIDTH,DEMON_HEIGHT,null);
        }
    }

    private void loadEnemyImgs() {
        fireAnimations = new BufferedImage[7][22];
        for (int i = 0; i < GetSpirteAmount(FIRE_DEMON, IDLE); i++) {
            try {
                fireAnimations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/01_demon_idle/demon_idle_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < GetSpirteAmount(FIRE_DEMON, WALK); i++) {
            try {
                fireAnimations[WALK][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/02_demon_walk/demon_walk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpirteAmount(FIRE_DEMON, CLEAVE); i++) {
            try {
                fireAnimations[CLEAVE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/03_demon_cleave/demon_cleave_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpirteAmount(FIRE_DEMON, TAKE_HIT); i++) {
            try {
                fireAnimations[TAKE_HIT][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/04_demon_take_hit/demon_take_hit_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpirteAmount(FIRE_DEMON, DEAD); i++) {
            try {
                fireAnimations[DEAD][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Fire Demon/05_demon_death/demon_death_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
