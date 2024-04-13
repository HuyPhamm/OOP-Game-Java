package object;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

import static utilz.Constants.Bullet.*;

public class Bullet extends GameObject {
    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean active = true;
    private BufferedImage[] animation;

    public Bullet(int x, int y, int dir, int objType) {
        super(x, y, objType);
        int xOffset = (int) (-10 * Game.SCALE);
        int yOffset = (int) (2 * Game.SCALE);

        if (dir == 1)
            xOffset = (int) (-10 * Game.SCALE);

        hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, BULLET_WIDTH, BULLET_HEIGHT);
        this.dir = dir;
        loadImgs();
    }

    private void loadImgs() {
        animation = new BufferedImage[3];

        for (int i = 0; i < animation.length; i++)
            animation[i] = LoadSave.GetSpriteAtlas(LoadSave.BULLET[i]);

    }

    public void update() {
        updateAnimationTick();
        updatePos();
    }

    public void draw(Graphics g, int xLvlOffset) {
        int flipX;
        if (dir == 1) flipX = 0;
        else flipX = BULLET_WIDTH;

        int flipW = dir;

        if (active) {
            g.drawImage(animation[aniIndex], (int) (this.hitbox.x - xDrawOffset - xLvlOffset) + flipX, (int) (this.hitbox.y - yDrawOffset), BULLET_WIDTH * flipW, BULLET_HEIGHT, null);
            drawHitbox(g,xLvlOffset);
        }
    }

    private void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) this.hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    private void updatePos() {
        hitbox.x += dir * SPEED;
    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

}