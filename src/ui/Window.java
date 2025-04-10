package ui;

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
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);

        this.add(root);

        this.setVisible(true);

        Rectangle bounds = this.getBounds();
        Window.screenWidth = bounds.width;
        Window.screenHeight = bounds.height;
        Window.screenSizeUnit = Window.screenWidth / 100;
        // Window.screenSizeUnit = Window.screenWidth
        // / Window.screenHeight > GameRenderingConstants.SCREEN_RATIO
        // ? (float) (Window.screenHeight / GameRenderingConstants.HEIGHT_RATIO)
        // : (float) (Window.screenWidth / GameRenderingConstants.WIDTH_RATIO);
    }

}