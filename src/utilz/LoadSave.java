package utilz;

import entities.FireDemon;
import entities.FrostDemon;
import entities.ShadowDemon;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.*;
public class LoadSave {
    //public static final String PLAYER_ATLAS = "player/idle/idle-1.png";
    public static final String LEVEL_ONE_DATA = "level/level_1.png";
    public static final String MENU_BUTTONS = "UI/Menu/Buttons.png";
    public static final String MENU_BACKGROUND = "UI/Menu/Menu.png";
    public static final String PAUSE_BACKGROUND = "UI/Option/Option.png";
    public static final String SOUND_BUTTONS = "UI/Option/Volume.png";
    public static final String URM_BUTTONS = "UI/Option/Buttons.png";
    public static final String VOLUME_BUTTONS = "UI/Option/Slider.png";

    // Background
    public static final String PLAYING_BG_IMG_1 = "layer/night_1.png";
    public static final String PLAYING_BG_IMG_2 = "layer/night_2.png";
    public static final String PLAYING_BG_IMG_3 = "layer/night_3.png";
    public static final String PLAYING_BG_IMG_4 = "layer/night_4.png";
    public static final String PLAYING_BG_IMG_5 = "layer/night_5.png";
    public static final String CLOUD = "layer/cloud.png";
    public static final String HEART = "item/heart.png";

    public static final String[] BULLET = {
            "player/shot/shot-1.png",
            "player/shot/shot-2.png",
            "player/shot/shot-3.png"
    };

    public static BufferedImage GetSpriteAtlas(String fileName){
        BufferedImage image = null;
        try {
            image = ImageIO.read(LoadSave.class.getResourceAsStream("/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static ArrayList<FireDemon> GetFireDemon(){
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<FireDemon> list = new ArrayList<>();
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getGreen();
                if (value == FIRE_DEMON)
                    list.add(new FireDemon(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
            }
        }
        return list;
    }
    public static ArrayList<FrostDemon> GetFrostDemon(){
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<FrostDemon> list = new ArrayList<>();
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getGreen();
                if (value == FROST_DEMON)
                    list.add(new FrostDemon(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
            }
        }
        return list;
    }
    public static ArrayList<ShadowDemon> GetShadowDemon(){
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<ShadowDemon> list = new ArrayList<>();
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getGreen();
                if (value == SHADOW_DEMON)
                    list.add(new ShadowDemon(j * Game.TILES_SIZE, i * Game.TILES_SIZE));
            }
        }
        return list;
    }
    public static int[][] GetLevelData() {
        // Kích cỡ ảnh Level cỡ 15x165
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[i][j] = value;
            }
        }
        return lvlData;
    }
}
