package model;

/**
 * Пиксель изображения
 */
public class RGBPixel {
    /**
     * Красная константа
     */
    private int red;

    /**
     * Зеленая консанта
     */
    private int green;

    /**
     * Синяя константа
     */
    private int blue;

    public RGBPixel() {
        red = 0;
        green = 0;
        blue = 0;
    }

    /**
     * Конструктор пикесля
     *
     * @param red   - красная константа
     * @param green - зеленя константа
     * @param blue  - синяя константа
     */
    public RGBPixel(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Консруктор копирования
     *
     * @param pixel - образец пикселя
     */
    public RGBPixel(RGBPixel pixel) {
        this.red = pixel.red;
        this.green = pixel.green;
        this.blue = pixel.blue;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        if (red < 0) {
            this.red = 0;
        } else if (red > 255) {
            this.red = 255;
        } else {
            this.red = red;
        }
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        if (green < 0) {
            this.green = 0;
        } else if (green > 255) {
            this.green = 255;
        } else {
            this.green = green;
        }
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        if (blue < 0) {
            this.blue = 0;
        } else if (blue > 255) {
            this.blue = 255;
        } else {
            this.blue = blue;
        }
    }
}
