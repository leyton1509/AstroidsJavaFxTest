package com.example.gamenewjava.Assets;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DefaultAsset {

    private String name;
    private int height;
    private int width;
    private Image image;
    private String filePath;
    private double xCoordinate;
    private double yCoordinate;
    private ImageView imageView;


    public DefaultAsset(String _name, int _height, int _width, String _filepath, int _startX, int _startY) throws FileNotFoundException {
        name = _name;
        height = _height;
        width = _width;
        filePath = _filepath;
        image = new Image(new FileInputStream(filePath));
        imageView = new ImageView(image);
        xCoordinate = _startX;
        yCoordinate = _startY;

        imageView.setX(xCoordinate);
        imageView.setY(yCoordinate);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

    }

    public void onGameTick(){

    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

}
