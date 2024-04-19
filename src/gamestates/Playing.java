package gamestates;

import UI.GameOverOverlay;
import UI.LevelCompletedOverlay;
import UI.PauseOverlay;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import object.Bullet;
import utilz.LoadSave;
import object.ObjectManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Playing extends State implements Statemethods{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private ObjectManager objectManager;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean paused = false;
    private int xLvlOffset;
    private int leftBorder = (int) (0.4 * Game.GAME_WIDTH);
    private int rightBoder = (int) (0.6 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDGHT;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;

    // Background
    private BufferedImage[] backgroundImage;
    private BufferedImage cloud;
    private GameOverOverlay gameOverOverlay;
    private boolean gameover;
    private boolean lvlCompleted = false;

    public Playing(Game game) throws IOException{
        super(game);
        initClasses();
    }
    private void initClasses() throws IOException {
        loadBackground();
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(100,100,(int) (20 * Game.SCALE), (int) (24 * Game.SCALE),this);
        objectManager = new ObjectManager(this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
    }
    private void loadBackground() {//load background
        backgroundImage = new BufferedImage[5];
        backgroundImage[0] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_1);
        backgroundImage[1] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_2);
        backgroundImage[2] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_3);
        backgroundImage[3] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_4);
        backgroundImage[4] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_5);
        cloud = LoadSave.GetSpriteAtlas(LoadSave.CLOUD);

    }
    private void drawBackground(Graphics g, int xLvlOffset) {
        int bgImageWidth = Game.GAME_WIDTH;
        int bgOffset = xLvlOffset % bgImageWidth;
        int amountBgToFillScreen = (int) Math.ceil(1.0 * Game.GAME_WIDTH / bgImageWidth) + 1;
        System.out.println(amountBgToFillScreen);
        // VẼ
        for (int i = 0; i < amountBgToFillScreen; i++) {
            // LAYER 0
            if (backgroundImage[0] != null)
                g.drawImage(backgroundImage[0], i * bgImageWidth - bgOffset, 0, bgImageWidth, Game.GAME_HIGHT, null);
            // LAYER 1
            if (backgroundImage[1] != null)
                g.drawImage(backgroundImage[1], i * bgImageWidth - bgOffset, 0, bgImageWidth, Game.GAME_HIGHT, null);
            // LAYER 2
            if (backgroundImage[2] != null)
                g.drawImage(backgroundImage[2], i * bgImageWidth - bgOffset, (int) (Game.GAME_HIGHT - backgroundImage[2].getHeight() * 1.7), (int) (backgroundImage[2].getWidth() * 1.7), (int) (backgroundImage[2].getHeight() * 1.7), null);
            // LAYER 4
            if (backgroundImage[4] != null)
                g.drawImage(backgroundImage[4], i * bgImageWidth + Game.GAME_HIGHT - (int) (backgroundImage[4].getWidth() * 1.5) - bgOffset, (int) (Game.GAME_HIGHT - backgroundImage[4].getHeight() * 1.5), (int) (backgroundImage[4].getWidth() * 1.5), (int) (backgroundImage[4].getHeight() * 1.5), null);
            // LAYER 3
            if (backgroundImage[3] != null)
                g.drawImage(backgroundImage[3], i * bgImageWidth - bgOffset, (int) (Game.GAME_HIGHT - backgroundImage[3].getHeight() * 2), (int) (backgroundImage[3].getWidth() * 2), (int) (backgroundImage[3].getHeight() * 2), null);
        }

    }
    private void drawCloud(Graphics g, int xLvlOffset) {
        int bgImageWidth = Game.GAME_WIDTH;
        int bgOffset = xLvlOffset % bgImageWidth;
        int amountBgToFillScreen = (int) Math.ceil(1.0 * Game.GAME_WIDTH / bgImageWidth) + 1;

        // VẼ
        for (int i = 0; i < amountBgToFillScreen; i++) {
            // LAYER 0
            if (cloud != null)
                g.drawImage(cloud, i * bgImageWidth - bgOffset, -200, bgImageWidth, 400, null);

        }

    }
    // reset tất cả để replay.
    public void resetAll(){
        paused = false;
//        gameover = false;
//        lvlcompleted=false;
//        checkBorder = true;
//        existBoss = false;
//        levelCompletedOverlay.setAniIndex(0);
        player.resetAll();
        enemyManager.resetEnemies();
        objectManager.resetAllObjects();
    }
    public void windowFocusLost(){
        player.resetDirBoolens();
    }
    public Player getPlayer(){
        return player;
    }
    public void unpauseGame(){
        paused = false;
    }

    public void checkEnemyHit(Bullet b) {
        enemyManager.checkEnemyHit(b);
    }

    @Override
    public void update() {
        gameover= player.IsDeath();
        lvlCompleted = player.IsCompleted();
        if(!paused&&!gameover&&!lvlCompleted){
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(),player);
            objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToBorder();
        }
        if (gameover){
            gameOverOverlay.update();
        }
        else if(paused)
            pauseOverlay.update();
        else if (lvlCompleted)
            levelCompletedOverlay.update();
    }

    private void checkCloseToBorder() {
        int playerX =(int) player.getHitBox().x;
        int diff = playerX - xLvlOffset;

        if(diff > rightBoder)
            xLvlOffset += diff - rightBoder;
        else if(diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if(xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if(xLvlOffset < 0)
            xLvlOffset = 0;
    }

    @Override
    public void draw(Graphics g) {
        drawBackground(g,xLvlOffset);
        drawCloud(g,xLvlOffset);
        levelManager.draw(g,xLvlOffset);
        player.render(g,xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        //objectManager.draw(g, xLvlOffset);
        if(paused){
            g.setColor(new Color(0,0,0,100));
            g.fillRect(0,0,Game.GAME_WIDTH,Game.GAME_HIGHT);
            pauseOverlay.draw(g);
        } else if (gameover) {
            gameOverOverlay.draw(g);
        }
        else if(lvlCompleted){
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HIGHT);
            levelCompletedOverlay.draw(g);
        }
        if (objectManager != null) objectManager.draw(g, xLvlOffset);

    }
    public void mouseDragged(MouseEvent e){
        if(paused)
            pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(paused) {
            pauseOverlay.mousePressed(e);
        }
        else if (gameover) {
            gameOverOverlay.mousePressed(e);
        }
        else if(lvlCompleted)
            levelCompletedOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(paused) {
            pauseOverlay.mouseReleased(e);
        } else if (gameover) {
            gameOverOverlay.mouseReleased(e);
        }
        else if(lvlCompleted)
            levelCompletedOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e){
        if(paused) {
            pauseOverlay.mouseMoved(e);
        } else if (gameover) {
            gameOverOverlay.mouseMoved(e);
        }
        else if(lvlCompleted)
            levelCompletedOverlay.mouseMoved(e);

    }

    @Override
    public void keyPress(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_W:
                player.setJump(true);
                break;
            case KeyEvent.VK_J:
                player.setAttack(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_W:
                player.setJump(false);
                break;
            case KeyEvent.VK_J:
                player.setAttack(false);
                break;
        }
    }
}
