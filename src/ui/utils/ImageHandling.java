package ui.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ImageHandling {
    public static BufferedImage loadImage(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));

            return image;
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return null;
    }
}
