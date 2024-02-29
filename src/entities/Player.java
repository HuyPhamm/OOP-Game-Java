package entities;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private int aniTick ,aniIndex , aniSpeed = 20;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;

    // Jumping / Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    private float playerSpeed = 2.0f;
    private float xDrawOffSet = 15 * Game.SCALE;
    private float yDrawOffSet = 12 * Game.SCALE;
    private int[][] lvlData;
    public Player(float x, float y, int widght, int height){
        super(x,y,widght,height);
        loadAnimation();
        initHitbox(x,y,(int) (20*Game.SCALE),(int) (32*Game.SCALE));
    }
    public void update(){
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g,int lvlOffset){
        g.drawImage(animations[playerAction][aniIndex], (int) (hitBox.x - xDrawOffSet) - lvlOffset,(int) (hitBox.y -yDrawOffSet),null);
        //drawHitbox(g);
    }

    private void loadAnimation(){
        //BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[7][8];
        //IDLE
        for (int i = 0; i < GetSpriteAmount(IDLE); i++) {
            try {
                animations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/player/idle/idle-" + (i+1) + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //RUN
        for (int i = 0; i < GetSpriteAmount(RUN); i++) {
            try {
                animations[RUN][i] = ImageIO.read(getClass().getResourceAsStream("/player/run/run-" + (i+1) +".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //SHOOT
        for (int i = 0; i < GetSpriteAmount(SHOOT); i++) {
            try {
                animations[SHOOT][i] = ImageIO.read(getClass().getResourceAsStream("/player/shoot/shoot.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // JUMP
        for (int i = 0; i < GetSpriteAmount(JUMP); i++) {
            try {
                animations[JUMP][i] = ImageIO.read(getClass().getResourceAsStream("/player/jump/jump-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;
        if(!IsEntityOnFloor(hitBox,lvlData))
            inAir = true;
    }
    public void setMoving(boolean moving){
        this.moving = moving;
    }
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }
    private void setAnimation(){

        int startAni = playerAction;

        if(moving){
            playerAction = RUN;
        }
        else{
            playerAction = IDLE;
        }
        if(inAir || jump){
            playerAction = JUMP;
        }
        if(attacking)
            playerAction = SHOOT;
        if(startAni != playerAction){
            resetAniTick();
        }
    }
    public void resetAniTick(){
        aniTick = 0;
        aniIndex = 0;
    }
    private void updatePos(){
        moving = false;
        if(jump)
            jump();
//        if(!left && !right &&!inAir)
//            return;
        if(!inAir)
            if((!left && !right) || (right && left))
                return;

        float xSpeed = 0;

        if(left)
            xSpeed = -playerSpeed;
        if (right)
            xSpeed = playerSpeed;
        if(!inAir){
            if(!IsEntityOnFloor(hitBox,lvlData)){
                inAir = true;
            }
        }
        if(inAir){
            if(CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width,hitBox.height,lvlData)){
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }
            else{
                hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox,airSpeed);
                if(airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        }else
            updateXPos(xSpeed);
        moving = true;

//        if(CanMoveHere(hitBox.x+xSpeed,hitBox.y+ySpeed,width,height,lvlData)){
//            hitBox.x += xSpeed;
//            hitBox.y += ySpeed;
//            moving = true;
//        }
    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir(){
        inAir = false;
        airSpeed = 0;
    }
    private void updateXPos(float xSpeed){
        if(CanMoveHere(hitBox.x+xSpeed,hitBox.y,width,height,lvlData)){
            hitBox.x += xSpeed;
        }else{
            hitBox.x = GetEntityXPosNextToWall(hitBox,xSpeed);
        }
    }
    public void resetDirBoolens(){
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttack(boolean attacking){
        this.attacking = attacking;
    }
    public boolean isLeft(){
        return left;
    }
    public void setLeft(boolean left){
        this.left = left;
    }
    public boolean isUp(){
        return up;
    }
    public void setUp(boolean up){
        this.up = up;
    }
    public boolean isRight(){
        return right;
    }
    public void setRight(boolean right){
        this.right = right;
    }
    public boolean isDown(){
        return down;
    }
    public void setDown(boolean down){
        this.down = down;
    }
    public void setJump(boolean jump){
        this.jump = jump;
    }
}
