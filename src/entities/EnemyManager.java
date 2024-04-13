package entities;

import gamestates.Playing;
import object.Bullet;
import utilz.Constants;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.*;

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

    public void update(int[][] lvlData, Player player){
        for(FireDemon d : fireDemons){
            if(d.isActive())
                d.update(lvlData,player);
        }
    }

    public void draw(Graphics g,int xLvlOffset){
        drawDemons(g,xLvlOffset);
    }

    private void drawDemons(Graphics g, int xLvlOffset) {
        for(FireDemon d : fireDemons){
            if(d.isActive()) {
                g.drawImage(fireAnimations[d.getEnemyState()][d.getAniIndex()], (int) d.getHitBox().x - xLvlOffset - FIRE_DEMON_DRAWOFFSET_X + d.flipX(), (int) d.getHitBox().y - FIRE_DEMON_DRAWOFFSET_Y, DEMON_WIDTH * d.flipW(), DEMON_HEIGHT, null);
                d.drawHitbox(g, xLvlOffset);
                d.drawAttackHitBox(g, xLvlOffset);
            }
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

    // Check xem bị bắn hay không
    public void checkEnemyHit(Bullet b) {
        Rectangle attackBox = b.getHitbox().getBounds();
        for (int i = 0; i < fireDemons.size(); i++) {
            if (!fireDemons.get(i).isDead() && fireDemons.get(i).getEnemyState() != DEAD) {
                if (fireDemons.get(i).getHitBox().intersects(attackBox)) {
                    fireDemons.get(i).hurt(playing.getPlayer().getDamage());
                    b.setActive(false);
                    return;
                }

            }
        }
    }
    public void resetEnemies() {
        for (FireDemon d : fireDemons) {
            d.resetEnemy();
        }
    }
}
