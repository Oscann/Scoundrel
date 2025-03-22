package ui;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JFrame;

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
        Window.screenSizeUnit = ((float) Window.screenWidth) / 100;
    }

}