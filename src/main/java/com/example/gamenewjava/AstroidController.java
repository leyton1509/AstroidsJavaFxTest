package com.example.gamenewjava;

import com.example.gamenewjava.Assets.Rock;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class AstroidController {

    private final int LEVEL_WIDTH;
    private final int LEVEL_HEIGHT;

    private long timeSinceLastAstroidIncrease = 0;

    private int currentAmountOfAstroids = 0;

    private int maxNumberOfAstroids = 30;

    public int getMaxNumberOfAstroids() {
        return maxNumberOfAstroids;
    }



    public void increaseMaxNumberOfAstroids(){
        if(maxNumberOfAstroids < 30){
            maxNumberOfAstroids++;
        }

    }
    public long getTimeSinceLastAstroidIncrease() {
        return timeSinceLastAstroidIncrease;
    }

    public void setTimeSinceLastAstroidIncrease(long timeSinceLastAstroidIncrease) {
        this.timeSinceLastAstroidIncrease = timeSinceLastAstroidIncrease;
    }

    public int getCurrentAmountOfAstroids() {
        return currentAmountOfAstroids;
    }

    public void setCurrentAmountOfAstroids(int currentAmountOfAstroids) {
        this.currentAmountOfAstroids = currentAmountOfAstroids;
    }

    public void increaseAstroidCount(){
        currentAmountOfAstroids++;
    }
    public void decreaseAstroidCount(){
        currentAmountOfAstroids--;
    }

    public AstroidController(int width, int height){
        LEVEL_WIDTH = width;
        LEVEL_HEIGHT = height;
    }

    public String getAstroidFilePath(){
        int ranAstroid = (int) (Math.random() * (4) + 0);
        return switch (ranAstroid) {
            case 1 -> "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\astroid2.png";
            case 2 -> "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\astroid3.png";
            case 3 -> "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\astroid4.png";
            default -> "L:\\Novus\\Code\\JFX\\GameNewJava\\imgs\\astroid1.png";
        };
    }

    public Rock generateNewAstroid() throws FileNotFoundException {
        String filepath = getAstroidFilePath();
        int ranSize = (int) (Math.random() * (40 - 10) + 10);
        int ranX = (int) (Math.random() * (LEVEL_WIDTH - ((LEVEL_WIDTH * 0.1) -1)) + 1);
        int ranY =  (int) (Math.random() * (LEVEL_HEIGHT - (LEVEL_HEIGHT * 0.1)) -1 + 1);
        Rock rock =  new Rock("Rock", ranSize, ranSize, filepath,ranX,ranY,  0.5);
        return rock;
    }

    public LinkedList<Rock> generateRandomAstroids(int numberToGenerate) throws FileNotFoundException {
        LinkedList<Rock> astroids = new LinkedList<>();
        for (int i = 0; i < numberToGenerate; i++) {
            String filepath = getAstroidFilePath();
            int ranSize = (int) (Math.random() * (40 - 10) + 10);
            int lowerOrHigherX = (int) Math.round(Math.random());
            int lowerOrHigherY = (int) Math.round(Math.random());
            int ranX;
            int ranY;

            if(lowerOrHigherX == 0){
                ranX = (int) (Math.random() * ((LEVEL_WIDTH - ((LEVEL_WIDTH * 0.35)) -90)) - 90);
            }
            else{
                ranX = (int) (Math.random() * (LEVEL_WIDTH - ((LEVEL_WIDTH * 0.35))) + (LEVEL_WIDTH * 0.6));
            }

            if(lowerOrHigherY == 0){
                ranY =  (int) (Math.random() * (LEVEL_HEIGHT - ((LEVEL_HEIGHT * 0.35)) -90) -90);
            }
            else{
                ranY =  (int) (Math.random() * (LEVEL_HEIGHT - ((LEVEL_HEIGHT * 0.35))) + (LEVEL_HEIGHT * 0.6));
            }


            astroids.add(new Rock("Rock", ranSize, ranSize, filepath,ranX,ranY,  0.5));
        }
        return astroids;
    }

}
