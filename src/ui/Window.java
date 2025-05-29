package ui;

import java.awt.Rectangle;

import javax.swing.JFrame;

import core.gamestate.constants.GameRenderingConstants;

public class Window extends JFrame {
    public static int screenWidth, screenHeight;
    public static int screenSizeUnit;

    public Window(MainPanel root) {
        this.setTitle("Scoundrel");
        this.setResizable(false);
        this.setExtendedState(MAXIMIZED_BOTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);

        this.add(root);

        this.setVisible(true);

        Rectangle bounds = this.getBounds();

        Window.screenWidth = bounds.width;
        Window.screenHeight = bounds.height;
        Window.screenSizeUnit = (float) Window.screenWidth
                / (float) Window.screenHeight > GameRenderingConstants.GAME_SCREEN_RATIO
                        ? (int) (Window.screenHeight / GameRenderingConstants.HEIGHT_RATIO)
                        : (int) (Window.screenWidth / GameRenderingConstants.WIDTH_RATIO);
    }

}