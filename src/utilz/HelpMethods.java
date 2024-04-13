package utilz;

import main.Game;

import java.awt.geom.Rectangle2D;
import object.Bullet;
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

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData){
        int value = lvlData[(int) yTile][(int) xTile];
        if(value > 45 ||value == 0 || value == 5 || value == 6 || value == 7 || value == 8 || value == 35){
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
        int currentTile = (int)(hitBox.y / (Game.TILES_SIZE -2)) ;
        if(airSpeed > 0){
            //Falling : Rơi chạm đất
            int tileYPos = currentTile * (Game.TILES_SIZE) ;
            int yOffset = (int)(Game.TILES_SIZE - hitBox.height );
            return tileYPos + yOffset - 1;
        }else{
            //Jumping : Nhảy
            return currentTile * Game.TILES_SIZE ;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData){
        if(!IsSolid(hitBox.x,hitBox.y + hitBox.height + 1,lvlData)){
            if(!IsSolid(hitBox.x + hitBox.width,hitBox.y + hitBox.height + 1,lvlData))
                return false;
        }
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed,int[][] lvlData){
        return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] lvlData){
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData))
                return false;
            if (!IsTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }
        return true;
    }

    public static boolean IsSightClear(int [][] lvlData, Rectangle2D.Float firstHitbox,
                                       Rectangle2D.Float secondHitbox, int yTile){
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if(firstXTile > secondXTile)
            return IsAllTileWalkable(secondXTile,firstXTile,yTile,lvlData);
        else
            return IsAllTileWalkable(firstXTile,secondXTile,yTile,lvlData);
    }

    // Check xem đạn có va chạm với tiles hay không
    public static boolean IsBulletsHittingLevel(Bullet b, int[][] lvlData) {
        return IsSolid(b.getHitbox().x + b.getHitbox().width / 2, b.getHitbox().y + b.getHitbox().height / 2, lvlData);

    }
}
