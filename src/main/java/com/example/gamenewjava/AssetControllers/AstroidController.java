package com.example.gamenewjava.AssetControllers;

import com.example.gamenewjava.Assets.Rock;
import com.example.gamenewjava.Assets.SplitAstroid;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

public class AstroidController {

    private final int LEVEL_WIDTH;
    private final int LEVEL_HEIGHT;

    private final int maxAstroidSize = 60;

    private final int minSplitAstroidSize = 20;

    private long timeSinceLastAstroidIncrease = 0;

    private int currentAmountOfAstroids = 0;

    private int maxNumberOfAstroids = 6;

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
            case 1 -> "imgs/astroid2.png";
            case 2 -> "imgs/astroid3.png";
            case 3 -> "imgs/astroid4.png";
            default -> "imgs/astroid1.png";
        };
    }

    public Rock generateNewAstroid() throws FileNotFoundException {
        String filepath = getAstroidFilePath();
        int ranSize = (int) (Math.random() * (maxAstroidSize - 15) + 15);

        int ranX = 0;
        int ranY = 0;

        ranX = (int) (Math.random() * ((LEVEL_WIDTH + 100) - (-100)) + (-100) );

        if(ranX < 0 || ranX > LEVEL_WIDTH){
            ranY =  (int) (Math.random() * ((LEVEL_HEIGHT + 90) - 90)-90);
        }
        else{
            if((int) Math.round(Math.random()) == 0){
                ranY =  (int) (Math.random() * ((LEVEL_HEIGHT + 90) - LEVEL_HEIGHT) + LEVEL_HEIGHT);
            }
            else{
                ranY = (int) (Math.random() * (90));
                ranY = ranY - 90;
            }

        }

        Rock rock =  new Rock("Rock", ranSize, ranSize, filepath,ranX,ranY,  0.5);

        if(ranX < LEVEL_WIDTH/2 && ranY < LEVEL_HEIGHT / 2){
            rock.getImageView().setRotate((int)(Math.random() * (170-100)+100));
        }
        else if(ranX > LEVEL_WIDTH/2 && ranY < LEVEL_HEIGHT / 2){
            rock.getImageView().setRotate((int)(Math.random() * (260-190)+190));
        }
        else if(ranX < LEVEL_WIDTH/2 && ranY > LEVEL_HEIGHT/2){
            rock.getImageView().setRotate((int)(Math.random() * (80-10)+80));
        }
        else if(ranX > LEVEL_WIDTH/2 && ranY > LEVEL_HEIGHT/2){
            rock.getImageView().setRotate((int)(Math.random() * (350 - 260)+260));
        }


        return rock;
    }



    public LinkedList<Rock> generateRandomAstroids(int numberToGenerate) throws FileNotFoundException {
        LinkedList<Rock> astroids = new LinkedList<>();
        for (int i = 0; i < numberToGenerate; i++) {
            String filepath = getAstroidFilePath();
            int ranSize = (int) (Math.random() * (maxAstroidSize - 15) + 15);
            int ranX;
            int ranY;

            if((int) Math.round(Math.random()) == 0){
                ranX = (int) (Math.random() * ((LEVEL_WIDTH - ((LEVEL_WIDTH * 0.35)) -90)) - 90);
            }
            else{
                ranX = (int) (Math.random() * (LEVEL_WIDTH - ((LEVEL_WIDTH * 0.35))) + (LEVEL_WIDTH * 0.6));
            }

            if((int) Math.round(Math.random()) == 0){
                ranY =  (int) (Math.random() * (LEVEL_HEIGHT - ((LEVEL_HEIGHT * 0.35)) -90) -90);
            }
            else{
                ranY =  (int) (Math.random() * (LEVEL_HEIGHT - ((LEVEL_HEIGHT * 0.35))) + (LEVEL_HEIGHT * 0.6));
            }


            astroids.add(new Rock("Rock", ranSize, ranSize, filepath,ranX,ranY,  0.5));
        }
        return astroids;
    }

    public SplitAstroid generateSplitAstroid(double angle, int x, int y, int size, double speed, String filepath) throws FileNotFoundException {
        return new SplitAstroid("SplitRock", size, size, filepath, x, y,  speed);
    }


    public ArrayList<SplitAstroid> checkForSplitAstroid(Rock astroid) throws FileNotFoundException {
        ArrayList<SplitAstroid> splitRocks = new ArrayList<>();
        double astroidX = astroid.getImageView().getX();
        double astroidY = astroid.getImageView().getY();
        if((astroid.getSize() - (int)astroid.getSize() / minSplitAstroidSize * 4)   > minSplitAstroidSize){
            splitRocks.add(generateSplitAstroid(270.0, (int) (astroidX - astroid.getSize()/3),(int) astroidY - astroid.getSize()/3, (int) (astroid.getSize() * 0.5), 0.4, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(90, (int) astroidX + astroid.getSize()/3, (int) astroidY + astroid.getSize()/3, (int) (astroid.getSize() * 0.5) , 0.4, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(0, (int) (astroidX - astroid.getSize()/3),(int) astroidY + astroid.getSize()/3, (int) (astroid.getSize() * 0.5), 0.4, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(180, (int) astroidX + astroid.getSize()/3, (int) astroidY - astroid.getSize()/3, (int) (astroid.getSize() * 0.5) , 0.4, astroid.getFilePath()));

        } else if ((astroid.getSize() - (int)astroid.getSize() / minSplitAstroidSize * 3)   > minSplitAstroidSize-5) {
            splitRocks.add(generateSplitAstroid(240, (int) (astroidX - astroid.getSize()/2),(int) astroidY - astroid.getSize()/2, (int) (astroid.getSize() * 0.6), 0.3, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(120, (int) astroidX + astroid.getSize()/2, (int) astroidY + astroid.getSize()/2, (int) (astroid.getSize() * 0.6) , 0.3, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(0, (int) (astroidX - astroid.getSize()/2),(int) astroidY + astroid.getSize()/2, (int) (astroid.getSize() * 0.6), 0.3, astroid.getFilePath()));

        }else if ((astroid.getSize() - (int)astroid.getSize() / minSplitAstroidSize * 2)   > minSplitAstroidSize-10) {
            splitRocks.add(generateSplitAstroid(240, (int) (astroidX - astroid.getSize()/2),(int) astroidY - astroid.getSize()/2, (int) (astroid.getSize() * 0.7), 0.2, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(120, (int) astroidX + astroid.getSize()/2, (int) astroidY + astroid.getSize()/2, (int) (astroid.getSize() * 0.7) , 0.2, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(0, (int) (astroidX - astroid.getSize()/2),(int) astroidY + astroid.getSize()/2, (int) (astroid.getSize() * 0.7), 0.2, astroid.getFilePath()));

        }


        return splitRocks;
    }

}
