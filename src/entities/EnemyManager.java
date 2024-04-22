package entities;

import gamestates.Playing;
import object.Bullet;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] fireAnimations,frostAnimations,shadowAnimations;
    private ArrayList<FireDemon> fireDemons = new ArrayList<>();
    private ArrayList<FrostDemon> frostDemons = new ArrayList<>();
    private ArrayList<ShadowDemon> shadowDemons = new ArrayList<>();
    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        fireDemons = LoadSave.GetFireDemon();
        frostDemons = LoadSave.GetFrostDemon();
        shadowDemons = LoadSave.GetShadowDemon();
    }

    public void update(int[][] lvlData, Player player){
        boolean isAnyActive = false;
        for (FireDemon d : fireDemons) {
            d.update(lvlData, player);
            if(d.isActive()) isAnyActive = true;
        }
        for (FrostDemon d : frostDemons) {
            d.update(lvlData, player);
            if(d.isActive())  isAnyActive = true;
        }
        for (ShadowDemon d : shadowDemons) {
            d.update(lvlData, player);
            if(d.isActive())  isAnyActive = true;
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
        for(FrostDemon d : frostDemons){
            if(d.isActive()) {
                g.drawImage(frostAnimations[d.getEnemyState()][d.getAniIndex()], (int) d.getHitBox().x - xLvlOffset - FROST_DEMON_DRAWOFFSET_X + d.flipX(), (int) d.getHitBox().y - FROST_DEMON_DRAWOFFSET_Y, DEMON_WIDTH * d.flipW(), DEMON_HEIGHT, null);
                d.drawHitbox(g, xLvlOffset);
                d.drawAttackHitBox(g, xLvlOffset);
            }
        }
        for(ShadowDemon d : shadowDemons){
            if(d.isActive()) {
                g.drawImage(shadowAnimations[d.getEnemyState()][d.getAniIndex()], (int) d.getHitBox().x - xLvlOffset - SHADOW_DEMON_DRAWOFFSET_X + d.flipX(), (int) d.getHitBox().y - SHADOW_DEMON_DRAWOFFSET_Y, DEMON_WIDTH * d.flipW(), DEMON_HEIGHT, null);
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
        // Frost demons
        frostAnimations = new BufferedImage[7][22];
        for (int i = 0; i < GetSpirteAmount(FROST_DEMON, IDLE); i++) {
            try {
                frostAnimations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/idle/idle_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpirteAmount(FROST_DEMON, WALK); i++) {
            try {
                frostAnimations[WALK][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/walk/walk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpirteAmount(FROST_DEMON, CLEAVE); i++) {
            try {
                frostAnimations[CLEAVE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/1_atk/1_atk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpirteAmount(FROST_DEMON, TAKE_HIT); i++) {
            try {
                frostAnimations[TAKE_HIT][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/take_hit/take_hit_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpirteAmount(FROST_DEMON, DEAD); i++) {
            try {
                frostAnimations[DEAD][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Frost Demon/death/death_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Shadow demons
        shadowAnimations = new BufferedImage[7][10];
        for (int i = 0; i < GetSpirteAmount(SHADOW_DEMON, IDLE); i++) {
            try {
                shadowAnimations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Idle/Bringer-of-Death_Idle_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpirteAmount(SHADOW_DEMON, WALK); i++) {
            try {
                shadowAnimations[WALK][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Walk/Bringer-of-Death_Walk_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpirteAmount(SHADOW_DEMON, CLEAVE); i++) {
            try {
                shadowAnimations[CLEAVE][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Attack/Bringer-of-Death_Attack_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < GetSpirteAmount(SHADOW_DEMON, TAKE_HIT); i++) {
            try {
                shadowAnimations[TAKE_HIT][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Hurt/Bringer-of-Death_Hurt_" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        for (int i = 0; i < GetSpirteAmount(SHADOW_DEMON, DEAD); i++) {
            try {
                shadowAnimations[DEAD][i] = ImageIO.read(getClass().getResourceAsStream("/demon/Shadow Demon/Death/Bringer-of-Death_Death_" + (i + 1) + ".png"));

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
        for (int i = 0; i < frostDemons.size(); i++) {
            if (!frostDemons.get(i).isDead() && frostDemons.get(i).getEnemyState() != DEAD) {
                if (frostDemons.get(i).getHitBox().intersects(attackBox)) {
                    frostDemons.get(i).hurt(playing.getPlayer().getDamage());
                    b.setActive(false);
                    return;
                }
            }
        }
        for (int i = 0; i < shadowDemons.size(); i++) {
            if (!shadowDemons.get(i).isDead() && shadowDemons.get(i).getEnemyState() != DEAD) {
                if (shadowDemons.get(i).getHitBox().intersects(attackBox)) {
                    shadowDemons.get(i).hurt(playing.getPlayer().getDamage());
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
        for (FrostDemon d : frostDemons) {
            d.resetEnemy();
        }
        for (ShadowDemon d : shadowDemons) {
            d.resetEnemy();
        }
    }
}
