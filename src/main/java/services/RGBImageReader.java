package services;

import model.RGBImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Класс для чтения картинок с диска
 */
public class RGBImageReader {
    private String filepath = null;

    /**
     * Конструктор класса
     *
     * @param filepath - путь до файла
     */
    public RGBImageReader(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Чтение картинки с диска
     *
     * @return - прочтенная с диска картинка
     * @throws IOException - ошибка во время считывания картинки с диска
     * @see RGBImage
     */
    public RGBImage readImage() throws IOException {
        File bmpFile = new File(filepath);
        BufferedImage image = ImageIO.read(bmpFile);
        RGBImage rgbImage = new RGBImage(image.getWidth(), image.getHeight());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                int red = (color & 0x00ff0000) >> 16;
                int green = (color & 0x0000ff00) >> 8;
                int blue = color & 0x000000ff;
                rgbImage.getMatrix()[i][j].setRed(red);
                rgbImage.getMatrix()[i][j].setGreen(green);
                rgbImage.getMatrix()[i][j].setBlue(blue);
            }
        }
        return rgbImage;
    }

}
