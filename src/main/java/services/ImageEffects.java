package services;

import model.RGBImage;
import model.RGBPixel;

import java.util.Random;

/**
 * Эффекты, которые можно применить к изображению
 * @see RGBImage - изображение
 */
public class ImageEffects {

    /**
     * Обесцвечивание
     * @param image - изображение, которое будет обесцвечено
     */
    public void colorless (RGBImage image){
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                RGBPixel pixel = image.getMatrix()[i][j];
                int sumColor = pixel.getRed()+pixel.getGreen()+pixel.getBlue();
                sumColor = sumColor/3;
                pixel.setRed(sumColor);
                pixel.setGreen(sumColor);
                pixel.setBlue(sumColor);
            }
        }
    }

    /**
     * Добавление шума на фото
     * @param image - изображение, на которое будет добавлен шум
     * @param power - сила шума
     */
    public void noise(RGBImage image, int power){
        for (int i = 0; i < image.getWidth(); i++){
            for (int j = 0; j < image.getHeight(); j++){
                RGBPixel pixel = image.getMatrix()[i][j];
                Random random = new Random();
                int rand = random.nextInt(power*2) - power;
                int red = pixel.getRed() + rand;
                if (red < 0){
                    red = 0;
                }else if (red > 255){
                    red = 255;
                }
                pixel.setRed(red);

                random = new Random();
                rand = random.nextInt(power*2) - power;
                int green = pixel.getGreen() + rand;
                if (green < 0){
                    green = 0;
                }else if (green > 255){
                    green = 255;
                }
                pixel.setGreen(green);

                random = new Random();
                rand = random.nextInt(power*2) - power;
                int blue = pixel.getBlue() + rand;
                if (blue < 0){
                    blue = 0;
                }else if (blue > 255){
                    blue = 255;
                }
                pixel.setBlue(blue);
            }
        }
    }

    public void denoiser(RGBImage image, int blockSize){
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                RGBPixel pixel = image.getMatrix()[i][j];
                int red = 0;
                int green = 0;
                int blue = 0;
                int count = 0;
                for (int k = i-blockSize; k < i+blockSize; k++) {
                    for (int l = j-blockSize; l < j+blockSize; l++) {
                        int width = k;
                        int height = l;
                        if (width < image.getWidth() && height < image.getHeight() &&
                        width >= 0 && height >= 0){
                            count++;
                            red+=image.getMatrix()[width][height].getRed();
                            blue+=image.getMatrix()[width][height].getBlue();
                            green+=image.getMatrix()[width][height].getGreen();
                        }
                    }
                }
                red = red/count;
                green = green/count;
                blue = blue/count;
                pixel.setRed(red);
                pixel.setBlue(blue);
                pixel.setGreen(green);
            }
        }
    }
}
