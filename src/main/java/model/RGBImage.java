package model;

/**
 * Цветное изображение, состоящее из пикселей
 * @see RGBPixel
 */
public class RGBImage {
    /**
     * Ширина изображения
     */
    private final int width;

    /**
     * Высота изображения
     */
    private final int height;

    /**
     * Матрица, хранящая пиксели
     */
    private RGBPixel[][] matrix;

    /**
     * Конструктор класса
     * @param width - ширина изображения
     * @param height - высота изобпажения
     */
    public RGBImage(int width, int height) {
        this.width = width;
        this.height = height;
        matrix = new RGBPixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = new RGBPixel();
            }
        }
    }

    public RGBImage(RGBImage image){
        this.width = image.width;
        this.height = image.height;
        this.matrix = new RGBPixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.matrix[i][j] = new RGBPixel(image.matrix[i][j]);
            }
        }
    }

    public RGBPixel[][] getMatrix() {
        return matrix;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
