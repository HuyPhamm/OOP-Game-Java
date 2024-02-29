package gamestates;

import UI.MenuButton;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements Statemethods{

    private MenuButton[] buttons = new MenuButton[4];
    private BufferedImage MenuBackGround;
    private int menuX,menuY,menuWidth,menuHeight;
    private BufferedImage[] backgroundImage;
    private BufferedImage cloud;
    private int scrollCloud;
    public Menu(Game game){
        super(game);
        loadBackground();
        loadButtons();
        loadMenuBackground();
        this.scrollCloud = 0;
    }
    private void loadBackground() {//ve background
        backgroundImage = new BufferedImage[5];
        backgroundImage[0] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_1);
        backgroundImage[1] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_2);
        backgroundImage[2] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_3);
        backgroundImage[3] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_4);
        backgroundImage[4] = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG_5);
        cloud = LoadSave.GetSpriteAtlas(LoadSave.CLOUD);

    }
    private void drawBackground(Graphics g) {
        if (backgroundImage[0] != null) g.drawImage(backgroundImage[0], 0, 0, Game.GAME_WIDTH, Game.GAME_HIGHT, null);
        if (backgroundImage[1] != null) g.drawImage(backgroundImage[1], 0, 0, Game.GAME_WIDTH, Game.GAME_HIGHT, null);
        if (backgroundImage[2] != null)
            g.drawImage(backgroundImage[2], 0, (int) (Game.GAME_HIGHT - backgroundImage[2].getHeight() * 1.7), (int) (backgroundImage[2].getWidth() * 1.7), (int) (backgroundImage[2].getHeight() * 1.7), null);
        if (backgroundImage[4] != null)
            g.drawImage(backgroundImage[4], Game.GAME_WIDTH - (int) (backgroundImage[4].getWidth() * 1.5), (int) (Game.GAME_HIGHT - backgroundImage[4].getHeight() * 1.5), (int) (backgroundImage[4].getWidth() * 1.5), (int) (backgroundImage[4].getHeight() * 1.5), null);
        if (backgroundImage[3] != null)
            g.drawImage(backgroundImage[3], 0, (int) (Game.GAME_HIGHT - backgroundImage[3].getHeight() * 2), (int) (backgroundImage[3].getWidth() * 2), (int) (backgroundImage[3].getHeight() * 2), null);
        for(int i=0;i<2;i++){
            if(cloud!=null){
                g.drawImage(cloud,i*Game.GAME_WIDTH+scrollCloud,-150,Game.GAME_WIDTH,400,null);
            }
        }
    }
    private void loadMenuBackground() {
        MenuBackGround = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (MenuBackGround.getWidth() * Game.SCALE);
        menuHeight = (int) (MenuBackGround.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * Game.SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (170 * Game.SCALE),0,Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (230 * Game.SCALE),1,Gamestate.LEADERBOARD);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE),2,Gamestate.OPTIONS);
        buttons[3] = new MenuButton(Game.GAME_WIDTH / 2, (int) (350 * Game.SCALE),3,Gamestate.QUIT);
    }

    @Override
    public void update() {
        for(MenuButton mb : buttons)
            mb.update();
        scrollCloud -= 1;
        if(scrollCloud <= -Game.GAME_WIDTH) scrollCloud = 0;
    }

    @Override
    public void draw(Graphics g) {
        drawBackground(g);
        g.drawImage(MenuBackGround, menuX, menuY,menuWidth,menuHeight,null);
        for(MenuButton mb : buttons)
            mb.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(MenuButton mb : buttons){
            if(isIn(e,mb)){
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb : buttons){
            if(isIn(e,mb)){
                if(mb.isMousePressed())
                    mb.applyGamestate();
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(MenuButton mb : buttons){
            mb.restBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb : buttons){
            mb.setMouseOver(false);
        }
        for(MenuButton mb : buttons){
            if(isIn(e,mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPress(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            Gamestate.state = Gamestate.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
