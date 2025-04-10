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
            // InputStream inputStream = new FileInputStream(path);
            BufferedImage image = ImageIO.read(new File(path));
            // BufferedImage image = ImageIO.read(inputStream);
            System.out.println(image.toString());

            return image;
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return null;
    }
}
