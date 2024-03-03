package entities;
import main.Game;

import static utilz.Constants.EnemyConstants.*;

public class FireDemon extends Enemy{

    public FireDemon(float x, float y) {
        super(x, y, DEMON_WIDTH, DEMON_HEIGHT, FIRE_DEMON);
        initHitbox(x,y, 30* Game.SCALE,  30 * Game.SCALE);
    }
    public void update(int[][] lvlData, Player player){
        updateMove(lvlData,player);
        updateAnimationTick();
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
            }
        }


    }
}
