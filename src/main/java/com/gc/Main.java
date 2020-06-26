package com.gc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.RGBImage;
import services.ImageEffects;
import services.RGBImageReader;
import services.RGBImageRender;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        RGBImageReader rgbImageReader = new RGBImageReader("D:/test/o.bmp");
        RGBImage image = rgbImageReader.readImage(); //Считывание изображения с диска

        Canvas canvas = new Canvas(image.getWidth() * 4, image.getHeight() * 3); //Создание полонтна для риосвания изображений
        Scene screen = new Scene(new StackPane(canvas), image.getWidth() * 4, image.getHeight() * 3);
        primaryStage.setScene(screen);
        primaryStage.show();


        RGBImageRender render = new RGBImageRender();
        ImageEffects effects = new ImageEffects();

        render.renderImage(image, canvas); //Рендер оригинального изображения

        //Добавление шума на изображения
        RGBImage noiseImg = effects.noise(image, 40);
        render.renderImage(noiseImg, canvas, image.getWidth(), 0);

        //Медианный фильтр, примененный к оригинальному изображению
        RGBImage denoiseMedian = effects.denoiser(image, 2);
        render.renderImage(denoiseMedian, canvas, image.getWidth() * 2, 0);

        //Медианный фильтр, примененный к зашумленному изображению
        RGBImage denoiseMedianNoise = effects.medianSmoothing(noiseImg, 2);
        render.renderImage(denoiseMedianNoise, canvas, image.getWidth() * 3, 0);

        //Фильтр, примененный к оригинальному изображению
        RGBImage denoise = effects.denoiser(image, 2);
        render.renderImage(denoise, canvas, 0, image.getHeight());

        //Фильтр, примененный к зашумленному изображению
        RGBImage denoiseNoise = effects.denoiser(noiseImg, 2);
        render.renderImage(denoiseNoise, canvas, image.getWidth(), image.getHeight());

        //Постеризованное изображение
        RGBImage posterizImage = effects.posterization(image, 4);
        render.renderImage(posterizImage, canvas, image.getWidth() * 2, image.getHeight());

        //Обесцвеченное изображение
        RGBImage colorlessImg = effects.colorless(image);
        render.renderImage(colorlessImg, canvas, image.getWidth() * 3, image.getHeight());

        // Оригинальное изображение -> шумная картинка -> медианный фильтр -> фильтр -> ковертирование к чб на основе лимита насыщенности
        RGBImage circuitImg = new RGBImage(image);
        circuitImg = effects.medianSmoothing(circuitImg, 2);
        circuitImg = effects.denoiser(circuitImg, 2);
        circuitImg = effects.twoTone(circuitImg, 30);
        render.renderImage(circuitImg, canvas, 0, image.getHeight() * 2);

        // оригинальная картинка + предыдушая картинка
        RGBImage sum = effects.additionImages(image, circuitImg);
        render.renderImage(sum, canvas, image.getWidth(), image.getHeight() * 2);
    }

}
