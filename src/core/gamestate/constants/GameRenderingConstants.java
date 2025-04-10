package core.gamestate.constants;

import java.awt.Font;

import objects.constants.CardConstants;

public class GameRenderingConstants {
    public static final float X_PADDING_RATIO = 1;
    public static final float Y_PADDING_RATIO = 1;
    public static final float WIDTH_RATIO = 2 * X_PADDING_RATIO + 5 * CardConstants.CARD_WIDTH_RATIO + 8;
    public static final float HEIGHT_RATIO = 2 * Y_PADDING_RATIO + 2 * CardConstants.CARD_HEIGHT_RATIO + 5;
    public static final float GAME_SCREEN_RATIO = WIDTH_RATIO / HEIGHT_RATIO;

    public static final String DEFAULT_FONT = "Arial";
}
