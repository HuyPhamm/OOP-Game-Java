package levels;

import main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;
    public LevelManager(Game game){
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.GetLevelData());
    }
    private void importOutsideSprites(){
        levelSprite = new BufferedImage[98];
        // TILES
        // Tiles này cần chú ý giá trị value của nó, dùng để kiểm tra va chạm (collision)
        for (int i = 0 ;i <= 45;i++){
            try{
                levelSprite[i] = ImageIO.read(getClass().getResourceAsStream(String.format("/tiles/Tiles/Tile_%02d.png",i)));
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void draw(Graphics g, int lvlOffset){
        for(int i = 0; i < Game.TILES_IN_HIGHT ; i++){
            for(int j=0; j < levelOne.getLevelData()[0].length;j++){
                int index = levelOne.getSpriteIndex(j,i);
                g.drawImage(levelSprite[index],Game.TILES_SIZE * j - lvlOffset, Game.TILES_SIZE * i, Game.TILES_SIZE,Game.TILES_SIZE,null);
            }
        }
    }
    public void update(){

    }

    public Level getCurrentLevel(){
        return levelOne;
    }
}
