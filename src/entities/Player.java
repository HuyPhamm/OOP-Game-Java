package entities;

import main.Game;
import utilz.Constants;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.ObjectConstants.BULLET;
import static utilz.HelpMethods.*;

import java.util.ConcurrentModificationException;

import object.Bullet;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private ArrayList<BufferedImage> hearts;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private int timerAttack;
    private int timerAttackMax = 40;
    private boolean canAttack;
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
    private float playerSpeed = 1.5f * Game.SCALE;
    private float xDrawOffSet = 15 * Game.SCALE;
    private float yDrawOffSet = 12 * Game.SCALE;

    //Flip animation
    private int flipX = 0;
    private int flipW = 1;
    private int[][] lvlData;
    public Player(float x, float y, int widght, int height){
        super(x,y,widght,height);
        loadAnimation();
        initHitbox(x,y+10, (int)(15*Game.SCALE), (int) (28*Game.SCALE));
        initHeart();
        this.timerAttack = this.timerAttackMax;
    }

    private void initHeart() {
        hearts= new ArrayList<>();
        BufferedImage heart = LoadSave.GetSpriteAtlas(LoadSave.HEART);
        for(int i=1;i<=3;i++)
            hearts.add(heart);
    }

    public void update(){
        updateTimer();
        updatePos();
        updateAnimationTick();
        updateBullet();
        setAnimation();
    }

    private void updateTimer() {
        if (this.timerAttack >= this.timerAttackMax) {
            canAttack = true;
            this.timerAttack = 0;
        }
        this.timerAttack++;
    }

    private void updateBullet() {
        for (Bullet b : bullets) {
            b.update();
            if (b.isActive()) {
                b.update();
                if (IsBulletsHittingLevel(b, lvlData))
                    b.setActive(false);
            }
        }
    }

    public void render(Graphics g,int lvlOffset){
        g.drawImage(animations[playerAction][aniIndex], (int) (hitBox.x - xDrawOffSet - lvlOffset + flipX),(int) (hitBox.y -yDrawOffSet),60*flipW,60,null);

        drawHitbox(g,lvlOffset);
        drawBullet(g,lvlOffset);
        drawHeart(g);
    }
    private void drawHeart(Graphics g){
        for(int i=0;i<hearts.size();i++){
            g.drawImage(hearts.get(i),  50*(i+1), 30, 45, 45, null);
        }
    }
    // trừ máu.
    public void minusHeart(int value) {
        for (int i = 0; i < value; i++) {
//        	System.out.println(hearts.size());
            if (hearts.size() > 0) {
            //    playing.getGame().getAudioPlayer().playEffect(AudioPlayer.HURT);
                hearts.remove(hearts.size() - 1);

            }
        }
    }
    // reset tất cả thông số player
    public void resetAll(){
        resetDirBoolens();
        inAir = false;
        attacking = false;
        moving = false;
        jump = false;
        airSpeed = 0f;
        hitBox.x = x;
        hitBox.y = y;
        if (!IsEntityOnFloor(hitBox,lvlData))
            inAir = true;
        initHeart();
        bullets.clear();
    }

    public boolean IsDeath() {
        if (hitBox.y + hitBox.height + 1 > Game.GAME_HIGHT)
        {
            hearts.clear();
        }
        return hearts.isEmpty();
    }
    private void drawBullet(Graphics g, int xLvlOffset) {
        for (Bullet b : bullets) {
            b.draw(g, xLvlOffset);
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

        //Atacking
        if(attacking) {
            if (playerAction == RUN) {
                playerAction = RUN_SHOOT;
            }
            else if(playerAction == IDLE) {
                playerAction = SHOOT;
            }
        }
        if(startAni != playerAction){
            if (!(startAni == RUN && playerAction == RUN_SHOOT)) {
                resetAniTick();
            }
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

        float xSpeed = 0;

        // Left
        if (left) {
            xSpeed -= playerSpeed;
            flipW = -1;
            flipX = 60;
            moving = true;
        }
        // Right
        if (right && !left) {
            xSpeed += playerSpeed;
            flipW = 1;
            flipX = 0;
            moving = true;
        }
        if(!inAir){
            if(!IsEntityOnFloor(hitBox,lvlData)){
                inAir = true;
            }
            if(attacking && canAttack){
                System.out.println("Shoot");
                shoot();
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
                else {
                    airSpeed = fallSpeedAfterCollision;
                    updateXPos(xSpeed);
                }
            }
        }else{
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
            }
            updateXPos(xSpeed);
        }


    }

    private void shoot() {
        if (aniTick == 0) {
            shootBullet();
            canAttack = false;
        }
    }

    private void shootBullet() throws ConcurrentModificationException {
        int x = (int) (10 * Game.SCALE);
        int y = (int) (-3* Game.SCALE);
        bullets.add(new Bullet((int) this.hitBox.x + x, (int) this.hitBox.y + y, flipW, BULLET));
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
        attacking = false;
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

        //RUN SHOOT
        for (int i = 0; i < GetSpriteAmount(RUN_SHOOT); i++) {
            try {
                animations[RUN_SHOOT][i] = ImageIO.read(getClass().getResourceAsStream("/player/run-shoot/run-shoot-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
