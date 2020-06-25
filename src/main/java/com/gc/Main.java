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
        Canvas canvas = new Canvas(640,480);
        Scene screen = new Scene(new StackPane(canvas), 640,480);
        primaryStage.setScene(screen);
        primaryStage.show();

        RGBImageReader rgbImageReader = new RGBImageReader("D:/test/o.bmp");
        RGBImage image = rgbImageReader.readImage();

        ImageEffects effects = new ImageEffects();
        RGBImageRender render = new RGBImageRender();
        render.renderImage(image, canvas);

        RGBImage colores = new RGBImage(image);
        effects.colorless(colores);
        render.renderImage(colores, canvas,320,0);

        RGBImage noise = new RGBImage(image);
        effects.noise(noise,40);
        render.renderImage(noise, canvas,0,240);

        RGBImage denoise = new RGBImage(noise);
        effects.denoiser(denoise,2);
        render.renderImage(denoise,canvas,320,240);
    }

}
