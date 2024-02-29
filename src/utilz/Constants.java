package utilz;

import main.Game;

public class Constants {
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
