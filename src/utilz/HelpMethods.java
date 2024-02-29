package utilz;

import main.Game;

import java.awt.geom.Rectangle2D;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData){
        if(!IsSolid(x,y,lvlData))
            if(!IsSolid(x+width,y+height,lvlData))
                if(!IsSolid(x+width,y,lvlData))
                    if(!IsSolid(x,y+height,lvlData))
                        return true;
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData){
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if(x <= 0 || x >= maxWidth)
            return true;
        if(y <= 0 || y >= Game.GAME_HIGHT)
            return true;
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];
        System.out.println("maxWidth: " + maxWidth);
        if(value >= 45 ||value == 0 || value == 5 || value == 6 || value == 7 || value == 8 || value == 35){
            return  false;
        }
        return true;
    }
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed){
        int currentTile = (int)(hitBox.x / Game.TILES_SIZE) ;
        if(xSpeed > 0){
            // Right : Phải
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitBox.width);
            return tileXPos + xOffset - 1;
        }else{
            //Left : Trái
            return currentTile * Game.TILES_SIZE ;
        }
    }
    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed){
        int currentTile = (int)(hitBox.y / (Game.TILES_SIZE -1)) ;
        if(airSpeed > 0){
            //Falling : Rơi chạm đất
            int tileYPos = currentTile * (Game.TILES_SIZE) ;
            int yOffset = (int)(Game.TILES_SIZE - hitBox.height + 1);
            return tileYPos + yOffset - 1;
        }else{
            //Jumping : Nhảy
            return currentTile * Game.TILES_SIZE ;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData){
        if(!IsSolid(hitBox.x,hitBox.y + hitBox.height,lvlData)){
            if(!IsSolid(hitBox.x + hitBox.width,hitBox.y + hitBox.height,lvlData))
                return false;
        }
        return true;
    }
}
