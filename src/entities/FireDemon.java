package entities;
import main.Game;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Direction.LEFT;
import static utilz.Constants.Direction.RIGHT;
import static utilz.Constants.EnemyConstants.*;
import entities.Enemy;
public class FireDemon extends Enemy{

    public FireDemon(float x, float y) {
        super(x, y, DEMON_WIDTH, DEMON_HEIGHT, FIRE_DEMON);
        initHitbox(x,y, 30* Game.SCALE,  30 * Game.SCALE);
        initAttackBox();
    }
    public void update(int[][] lvlData, Player player){
        updateMove(lvlData,player);
        updateAnimationTick();
        updateAttackBox();
    }
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) 40 * Game.SCALE, (int) 40 * Game.SCALE);
        attackBoxOffsetX = (int) (Game.SCALE * 35);
    }
    private void updateAttackBox() {
        // Update attack hitbox theo huong di cua quai
        if (walkDir == LEFT) {
           attackBox.x = hitBox.x - attackBoxOffsetX;
        } else if (walkDir == RIGHT) {
            attackBox.x = hitBox.x + attackBoxOffsetX - 20;
        }
        attackBox.y = hitBox.y;
    }
    public void updateMove(int[][] lvlData, Player player){
        if(firstUpdate)
            firstUpdateCheck(lvlData);
        if(inAir){
            updateInAir(lvlData);
        }else{
            switch (enemyState){
                case IDLE:
                    newState(WALK);
                    break;
                case WALK:
                    if(canSeePlayer(lvlData,player))
                        turnTowardsPlayer(player);
                    if(isPlayerCloseForAttack(player))
                        newState(CLEAVE);

                    move(lvlData);
                    break;
                case CLEAVE:
                    if(aniIndex == 0)
                        attackChecked = false;
                    if(aniIndex == 7 && !attackChecked)
                    {
                        checkPlayerHit(attackBox, player);
                    }
                    break;
            }
        }


    }
    public int flipX() {
        if (walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if (walkDir == RIGHT)
            return -1;
        else
            return 1;

    }
}
