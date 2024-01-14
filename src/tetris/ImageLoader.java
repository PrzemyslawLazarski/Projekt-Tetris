package tetris;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    public static BufferedImage loadImage(String path, int newWidth, int newHeight) {
        try {
            // Wczytaj oryginalny obraz
            BufferedImage originalImage = ImageIO.read(new File("data" + path));

            // Skaluj obraz do nowych wymiarów
            Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            // Konwertuj z powrotem na BufferedImage
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            // Zwróć zmieniony obraz
            return resizedImage;

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}