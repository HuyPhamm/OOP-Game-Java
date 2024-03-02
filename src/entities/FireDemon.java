package entities;
import static utilz.Constants.EnemyConstants.*;

public class FireDemon extends Enemy{

    public FireDemon(float x, float y) {
        super(x, y, DEMON_WIDTH, DEMON_HEIGHT, FIRE_DEMON);
    }
}
