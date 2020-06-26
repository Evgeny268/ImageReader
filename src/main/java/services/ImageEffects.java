package services;

import model.RGBImage;
import model.RGBPixel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Эффекты, которые можно применить к изображению
 *
 * @see RGBImage - изображение
 */
public class ImageEffects {

    /**
     * Обесцвечивание
     *
     * @param image - образец изображения
     * @return - обработанное изображение
     */
    public RGBImage colorless(RGBImage image) {
        RGBImage newImage = new RGBImage(image);
        for (int i = 0; i < newImage.getWidth(); i++) {
            for (int j = 0; j < newImage.getHeight(); j++) {
                RGBPixel pixel = newImage.getMatrix()[i][j];
                int sumColor = pixel.getRed() + pixel.getGreen() + pixel.getBlue();
                sumColor = sumColor / 3;
                pixel.setRed(sumColor);
                pixel.setGreen(sumColor);
                pixel.setBlue(sumColor);
            }
        }
        return newImage;
    }

    /**
     * Добавление шума на фото
     *
     * @param image - образец изображения
     * @param power - сила шума
     * @return - зашумленное изображение
     */
    public RGBImage noise(RGBImage image, int power) {
        RGBImage newImage = new RGBImage(image);
        for (int i = 0; i < newImage.getWidth(); i++) {
            for (int j = 0; j < newImage.getHeight(); j++) {
                RGBPixel pixel = newImage.getMatrix()[i][j];
                Random random = new Random();
                int rand = random.nextInt(power * 2) - power;
                int red = pixel.getRed() + rand;
                if (red < 0) {
                    red = 0;
                } else if (red > 255) {
                    red = 255;
                }
                pixel.setRed(red);

                random = new Random();
                rand = random.nextInt(power * 2) - power;
                int green = pixel.getGreen() + rand;
                if (green < 0) {
                    green = 0;
                } else if (green > 255) {
                    green = 255;
                }
                pixel.setGreen(green);

                random = new Random();
                rand = random.nextInt(power * 2) - power;
                int blue = pixel.getBlue() + rand;
                if (blue < 0) {
                    blue = 0;
                } else if (blue > 255) {
                    blue = 255;
                }
                pixel.setBlue(blue);
            }
        }
        return newImage;
    }

    /**
     * Фильтр анизотропного фильтра для сглаживания
     * шума на цветном RGB-изображении с апертурой blockSize
     *
     * @param image     - образец изображения
     * @param blockSize - апертура
     * @return - отфильтрованное изображение
     */
    public RGBImage denoiser(RGBImage image, int blockSize) {
        RGBImage newImage = new RGBImage(image);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                RGBPixel pixel = new RGBPixel(image.getMatrix()[i][j]);
                int red = 0;
                int green = 0;
                int blue = 0;
                int count = 0;
                for (int k = i - blockSize; k < i + blockSize; k++) {
                    for (int l = j - blockSize; l < j + blockSize; l++) {
                        if (k < image.getWidth() && l < image.getHeight() &&
                                k >= 0 && l >= 0) {
                            count++;
                            red += image.getMatrix()[k][l].getRed();
                            blue += image.getMatrix()[k][l].getBlue();
                            green += image.getMatrix()[k][l].getGreen();
                        }
                    }
                }
                red = red / count;
                green = green / count;
                blue = blue / count;
                pixel.setRed(red);
                pixel.setBlue(blue);
                pixel.setGreen(green);
                newImage.getMatrix()[i][j] = pixel;
            }
        }
        return newImage;
    }

    /**
     * Медианное сглаживание
     *
     * @param image     - образец изображения
     * @param blockSize - апертура
     * @return - сглаженное изображение
     */
    public RGBImage medianSmoothing(RGBImage image, int blockSize) {
        RGBImage newImage = new RGBImage(image);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                RGBPixel pixel = new RGBPixel(image.getMatrix()[i][j]);
                List<Integer> redList = new ArrayList<>();
                List<Integer> greenList = new ArrayList<>();
                List<Integer> blueList = new ArrayList<>();
                for (int k = i - blockSize; k < i + blockSize; k++) {
                    for (int l = j - blockSize; l < j + blockSize; l++) {
                        if (k < image.getWidth() && l < image.getHeight() &&
                                k >= 0 && l >= 0) {
                            redList.add(image.getMatrix()[k][l].getRed());
                            greenList.add(image.getMatrix()[k][l].getGreen());
                            blueList.add(image.getMatrix()[k][l].getBlue());
                        }
                    }
                }
                Collections.sort(redList);
                Collections.sort(greenList);
                Collections.sort(blueList);
                int red = redList.get(redList.size() / 2);
                int green = greenList.get(greenList.size() / 2);
                int blue = blueList.get(blueList.size() / 2);
                pixel.setRed(red);
                pixel.setGreen(green);
                pixel.setBlue(blue);
                newImage.getMatrix()[i][j] = pixel;
            }
        }
        return newImage;
    }

    /**
     * Черно белая картинка, регулируемая через порог насыщенности
     *
     * @param image           - оригинальное изображение
     * @param saturationLimit - лимит насыщенности от 0 до 100. Если насыщенность пискеля
     *                        меньше чем saturationLimit, то он закрашивается белым цветом,
     *                        иначе закрашивается черным цветом
     * @return - черно белая картинка
     */
    public RGBImage twoTone(RGBImage image, int saturationLimit) {
        RGBImage newImage = new RGBImage(image);
        for (int i = 0; i < newImage.getWidth(); i++) {
            for (int j = 0; j < newImage.getHeight(); j++) {
                RGBPixel pixel = newImage.getMatrix()[i][j];
                double[] hsv = RGBtoHSV(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
                if (hsv[1] * 100 > saturationLimit) {
                    pixel.setRed(255);
                    pixel.setGreen(255);
                    pixel.setBlue(255);
                } else {
                    pixel.setRed(0);
                    pixel.setGreen(0);
                    pixel.setBlue(0);
                }
            }
        }
        return newImage;
    }

    /**
     * Сложение двух картинок
     *
     * @param first  - первая картинка
     * @param second - вторая картинка
     * @return - картинка, являющаяся результатом сложения двух картинок
     */
    public RGBImage additionImages(RGBImage first, RGBImage second) {
        RGBImage newImage = new RGBImage(first.getWidth(), first.getHeight());
        for (int i = 0; i < newImage.getWidth(); i++) {
            for (int j = 0; j < newImage.getHeight(); j++) {
                int red = first.getMatrix()[i][j].getRed() + second.getMatrix()[i][j].getRed();
                red = red / 2;
                int green = first.getMatrix()[i][j].getGreen() + second.getMatrix()[i][j].getGreen();
                green = green / 2;
                int blue = first.getMatrix()[i][j].getBlue() + second.getMatrix()[i][j].getBlue();
                blue = blue / 2;
                RGBPixel pixel = new RGBPixel(red, green, blue);
                newImage.getMatrix()[i][j] = pixel;
            }
        }
        return newImage;
    }

    /**
     * Постеризация изображения
     *
     * @param image - оригинальное изображение
     * @param level - кол-во цветов, из которых будет составлена новая картинка
     * @return - постеризованная картинка
     */
    public RGBImage posterization(RGBImage image, int level) {
        int step = 255 / (level - 1);
        RGBImage newImage = new RGBImage(image);
        for (int i = 0; i < newImage.getWidth(); i++) {
            for (int j = 0; j < newImage.getHeight(); j++) {
                RGBPixel pixel = newImage.getMatrix()[i][j];
                int red = pixel.getRed();
                if (red > 52) {
                    red = red;
                }
                if (red % step < step - (red % step)) { //если ближе к нулю
                    while (red > 0 && (red % step) != 0) {
                        red--;
                    }
                } else {
                    while (red < 255 && (red % step) != 0) {
                        red++;
                    }
                }

                int green = pixel.getGreen();
                if (green % step < step - (green % step)) { //если ближе к нулю
                    while (green > 0 && (green % step) != 0) {
                        green--;
                    }
                } else {
                    while (green < 255 && (green % step) != 0) {
                        green++;
                    }
                }

                int blue = pixel.getBlue();
                if (blue % step < step - (blue % step)) { //если ближе к нулю
                    while (blue > 0 && (blue % step) != 0) {
                        blue--;
                    }
                } else {
                    while (blue < 255 && (blue % step) != 0) {
                        blue++;
                    }
                }
                pixel.setRed(red);
                pixel.setBlue(blue);
                pixel.setGreen(green);
            }
        }
        return newImage;
    }

    /**
     * Перевод пикселя из цветового пространства RGB в пространство HSV
     *
     * @param r - красный цвет
     * @param g - зеленый цвет
     * @param b - синий цвет
     * @return - массив цвета в пространстве HSV {h,s,v}, где
     * h - тон, s - насыщенность, v - значение
     */
    public static double[] RGBtoHSV(double r, double g, double b) {

        //Тон
        double h;

        //Насыщенность
        double s;

        //Значение
        double v;

        double min, max, delta;

        min = Math.min(Math.min(r, g), b);
        max = Math.max(Math.max(r, g), b);
        // V
        v = max;
        delta = max - min;
        // S
        if (max != 0)
            s = delta / max;
        else {
            s = 0;
            h = -1;
            return new double[]{h, s, v};
        }
        // H
        if (r == max)
            h = (g - b) / delta; // between yellow & magenta
        else if (g == max)
            h = 2 + (b - r) / delta; // between cyan & yellow
        else
            h = 4 + (r - g) / delta; // between magenta & cyan
        h *= 60;    // degrees
        if (h < 0)
            h += 360;
        return new double[]{h, s, v};
    }

    /**
     * Перевод числа из одного диапазона в другой
     *
     * @param oldValue - старое значение
     * @param oldMin   - нижний порог старого диапазона
     * @param oldMax   - верхний порог старого диапазона
     * @param newMin   - нижний порог нового диапазона
     * @param newMax   - верхний порог нвого диапазона
     * @return - новое значение
     */
    private float rangeChange(float oldValue, float oldMin, float oldMax, float newMin, float newMax) {
        return (((oldValue - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
    }
}
