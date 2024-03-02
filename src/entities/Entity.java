package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected float x,y;
    protected int width,height;
    protected Rectangle2D.Float hitBox;
    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawHitbox(Graphics g, int xlvlOffset){
        //Check va chạm
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x - xlvlOffset,(int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }
    protected void initHitbox(float x,float y, float width ,float height){
        hitBox = new Rectangle2D.Float(x,y,width,height);
    }
//    public void updateHitbox(){
//        hitBox.x = (int) x;
//        hitBox.y = (int) y;
//    }
    public Rectangle2D.Float getHitBox(){
        return hitBox;
    }
}
