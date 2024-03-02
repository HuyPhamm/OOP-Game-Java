package entities;
import main.Game;

import static utilz.Constants.EnemyConstants.*;

public class FireDemon extends Enemy{

    public FireDemon(float x, float y) {
        super(x, y, DEMON_WIDTH, DEMON_HEIGHT, FIRE_DEMON);
        initHitbox(x,y, 30* Game.SCALE,  30 * Game.SCALE);
    }
}
