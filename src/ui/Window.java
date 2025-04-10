package ui;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;

import core.gamestate.constants.GameRenderingConstants;

public class Window extends JFrame {
    public static int screenWidth, screenHeight;
    public static float screenSizeUnit;
    public static final float RENDER_SCALE = 2.0f;

    public Window(MainPanel root) {
        this.setTitle("Solitaire");
        this.setResizable(false);
        // this.setExtendedState(MAXIMIZED_BOTH);
        this.setSize(new Dimension(500, 500));

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);

        this.add(root);

        this.setVisible(true);

        Rectangle bounds = this.getBounds();

        System.out.println(bounds);

        Window.screenWidth = bounds.width;
        Window.screenHeight = bounds.height;
        Window.screenSizeUnit = (float) Window.screenWidth
                / (float) Window.screenHeight > GameRenderingConstants.GAME_SCREEN_RATIO
                        ? (float) (Window.screenHeight / GameRenderingConstants.HEIGHT_RATIO)
                        : (float) (Window.screenWidth / GameRenderingConstants.WIDTH_RATIO);
    }

}