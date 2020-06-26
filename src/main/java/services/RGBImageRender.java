package services;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import model.RGBImage;
import model.RGBPixel;

/**
 * Класс для рендера картинки
 */
public class RGBImageRender {

    /**
     * Рендер картинки на полотно
     *
     * @param image  - картинка для отрисовки
     * @param canvas - полотно, на котором будет нарисована картинка
     */
    public void renderImage(RGBImage image, Canvas canvas) {
        renderImage(image, canvas, 0, 0);
    }

    /**
     * Рендер картинки на полотно
     *
     * @param image       - картинка для отрисовки
     * @param canvas      - полотно, на котором будет нарисована картинка
     * @param shiftWidth  - смешение по ширине
     * @param shiftHeight - смешение по высоте
     */
    public void renderImage(RGBImage image, Canvas canvas, int shiftWidth, int shiftHeight) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                RGBPixel pixel = image.getMatrix()[i][j];
                float fred = rangeChange(pixel.getRed(), 0, 255, 0, 1);
                float fgreen = rangeChange(pixel.getGreen(), 0, 255, 0, 1);
                float fblue = rangeChange(pixel.getBlue(), 0, 255, 0, 1);
                canvas.getGraphicsContext2D().getPixelWriter().setColor(i + shiftWidth, j + shiftHeight,
                        Color.color(fred, fgreen, fblue));
            }
        }
    }

    private float rangeChange(float oldValue, float oldMin, float oldMax, float newMin, float newMax) {
        return (((oldValue - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
    }
}
