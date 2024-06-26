package utilz;

import main.Game;

public class Constants {
    public static final int ANI_SPEED = 25;
    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static class EnemyConstants{
        public static final int FIRE_DEMON = 29;
        public static final int FROST_DEMON = 39;
        public static final int SHADOW_DEMON = 49;
        public static final int IDLE = 2;
        public static final int WALK = 3;
        public static final int CLEAVE = 4;
        public static final int TAKE_HIT = 5;
        public static final int DEAD = 6;

        public static final int DEMON_WIDTH_DEFAULT  = 120;
        public static final int DEMON_HEIGHT_DEFAULT = 80;
        public static final int DEMON_WIDTH = (int) (DEMON_WIDTH_DEFAULT * Game.SCALE);
        public static final int DEMON_HEIGHT = (int) (DEMON_HEIGHT_DEFAULT * Game.SCALE);
        public static final int FIRE_DEMON_DRAWOFFSET_X = (int) (50 * Game.SCALE);
        public static final int FIRE_DEMON_DRAWOFFSET_Y = (int) (48 * Game.SCALE);
        public static final int FROST_DEMON_DRAWOFFSET_X = (int) (50 * Game.SCALE);
        public static final int FROST_DEMON_DRAWOFFSET_Y = (int) (38 * Game.SCALE);
        public static final int SHADOW_DEMON_DRAWOFFSET_X = (int) (70 * Game.SCALE);
        public static final int SHADOW_DEMON_DRAWOFFSET_Y = (int) (43 * Game.SCALE);

        public static int GetSpirteAmount(int enemy_type, int enemy_state) {
            switch (enemy_type) {
                case FIRE_DEMON:
                    switch (enemy_state) {
                        case IDLE:
                            return 6;
                        case WALK:
                            return 12;
                        case CLEAVE:
                            return 15;
                        case TAKE_HIT:
                            return 5;
                        case DEAD:
                            return 22;
                    }
                case FROST_DEMON:
                    switch (enemy_state) {
                        case IDLE:
                            return 6;
                        case WALK:
                            return 10;
                        case CLEAVE:
                            return 14;
                        case TAKE_HIT:
                            return 7;
                        case DEAD:
                            return 16;
                    }
                case SHADOW_DEMON:
                    switch (enemy_state) {
                        case IDLE:
                            return 8;
                        case WALK:
                            return 8;
                        case CLEAVE:
                            return 10;
                        case TAKE_HIT:
                            return 3;
                        case DEAD:
                            return 10;
                    }
            }
            return 0;
        }
        public static int GetMaxHeath(int enemy_type){
            switch (enemy_type) {
                case FIRE_DEMON:
                    return 100;
                case FROST_DEMON:
                    return 130;
                case SHADOW_DEMON:
                    return 60;
            }
            return 0;
        }
    }
    //Bullet
    public static class Bullet {
        public static final int BULLET_DEFAULT_WIDTH = 16;
        public static final int BULLET_DEFAULT_HEIGHT = 13;

        public static final int BULLET_WIDTH = (int) (Game.SCALE * BULLET_DEFAULT_WIDTH);
        public static final int BULLET_HEIGHT = (int) (Game.SCALE * BULLET_DEFAULT_HEIGHT);
        public static final float SPEED = 1.2f * Game.SCALE;
    }

    public static class ObjectConstants {
        public static final int BULLET = 0;

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case BULLET:
                    return 3;
            }
            return 1;
        }
    }
    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 180;
            public static final int B_HEIGHT_DEFAULT = 48;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class URMButtons{
            public static final int URM_DEAFULT_SIZE = 56;
            public static final int URM_SIZE = (int) (URM_DEAFULT_SIZE * Game.SCALE);
        }
        public static class VolumeButtons{
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 44;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
        }
    }
    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int HURT = 1;
        public static final int BACK_JUMP = 3;
        public static final int JUMP = 2;
        public static final int RUN = 4;
        public static final int RUN_SHOOT = 5;
        public static final int SHOOT = 6;

        public static int GetSpriteAmount(int player_action){
            switch (player_action) {
                case HURT:
                    return 1;
                case IDLE:
                    return 4;
                case JUMP:
                    return 4;
                case BACK_JUMP:
                    return 7;
                case RUN, RUN_SHOOT:
                    return 8;
                case SHOOT:
                    return 1;
                default:
                    return 1;
            }
        }
    }
}
