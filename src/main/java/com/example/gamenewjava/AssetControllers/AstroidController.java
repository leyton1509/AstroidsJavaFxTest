package com.example.gamenewjava.AssetControllers;

import com.example.gamenewjava.Assets.Rock;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Controls astroid generation
 */
public class AstroidController {

    /**
     * The width of the level
     */
    private final int LEVEL_WIDTH;

    /**
     * The height of the level
     */
    private final int LEVEL_HEIGHT;

    /**
     * The max size of an astroid
     */
    private final int maxAstroidSize = 60;

    /**
     * The time since last increasing the number of astroids
     */
    private long timeSinceLastAstroidIncrease = 0;

    /**
     * The current amount of astroids
     */
    private int currentAmountOfAstroids = 0;

    /**
     * The max number of astroids
     */
    private int maxNumberOfAstroids = 6;

    /**
     * @return The max number of astroids
     */
    public int getMaxNumberOfAstroids() {
        return maxNumberOfAstroids;
    }

    /**
     * Increase the max number of astroids
     */
    public void increaseMaxNumberOfAstroids(){
        if(maxNumberOfAstroids < 30){
            maxNumberOfAstroids++;
        }

    }

    /**
     * @return The time since last increased astroid count
     */
    public long getTimeSinceLastAstroidIncrease() {
        return timeSinceLastAstroidIncrease;
    }

    /**
     * @param timeSinceLastAstroidIncrease Sets the time since last increase
     */
    public void setTimeSinceLastAstroidIncrease(long timeSinceLastAstroidIncrease) {
        this.timeSinceLastAstroidIncrease = timeSinceLastAstroidIncrease;
    }

    /**
     * @return The number of astroids does not include split astroids
     */
    public int getCurrentAmountOfAstroids() {
        return currentAmountOfAstroids;
    }

    /**
     * @param currentAmountOfAstroids Sets the current amount of astroids
     */
    public void setCurrentAmountOfAstroids(int currentAmountOfAstroids) {
        this.currentAmountOfAstroids = currentAmountOfAstroids;
    }

    /**
     * Increases astroid count
     */
    public void increaseAstroidCount(){
        currentAmountOfAstroids++;
    }

    /**
     * Decreases astroid count
     */
    public void decreaseAstroidCount(){
        currentAmountOfAstroids--;
    }

    /**
     * @param width The width of level
     * @param height The height of level
     */
    public AstroidController(int width, int height){
        LEVEL_WIDTH = width;
        LEVEL_HEIGHT = height;
    }

    /**
     * @return The file path of one of four astroids
     */
    public String getAstroidFilePath(){
        int ranAstroid = (int) (Math.random() * (4) + 0);
        return switch (ranAstroid) {
            case 1 -> "imgs/astroid2.png";
            case 2 -> "imgs/astroid3.png";
            case 3 -> "imgs/astroid4.png";
            default -> "imgs/astroid1.png";
        };
    }

    /**
     * @return A new Astroid outside the player area
     * @throws FileNotFoundException e
     */
    public Rock generateNewAstroid() throws FileNotFoundException {
        String filepath = getAstroidFilePath();
        int ranSize = (int) (Math.random() * (maxAstroidSize - 15) + 15);

        // Generates a random x and y within a boundary just outside of user view

        int ranX;
        int ranY;

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
        // Sets the rotation to go into the player view

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


    /**
     * Generates n astroids
     * @param numberToGenerate Number of astroids to generate
     * @return List of astroids
     * @throws FileNotFoundException e
     */
    public LinkedList<Rock> generateRandomAstroids(int numberToGenerate) throws FileNotFoundException {
        LinkedList<Rock> astroids = new LinkedList<>();
        for (int i = 0; i < numberToGenerate; i++) {
            // Gets the filepath
            // Sets the position so that an astroid will not generate in the middle of the screen
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

    /**
     * @param angle The angle to go in
     * @param x The x coordinate
     * @param y The y coordinate
     * @param size The size of rock
     * @param speed The speed of rock
     * @param filepath the filepath of image
     * @return new rock
     * @throws FileNotFoundException e
     */
    public Rock generateSplitAstroid(double angle, int x, int y, int size, double speed, String filepath) throws FileNotFoundException {
        Rock rock = new Rock("SplitRock", size, size, filepath, x, y,  speed);
        rock.getImageView().setRotate(angle);
        return rock;
    }


    /**
     * Checks to see if new astroids should be generated when an astroid is destroyed
     * Generates number based on the size of the astroid destroyed
     * @param astroid The astroid that's being destroyed
     * @return An arraylist of new rocks
     * @throws FileNotFoundException e
     */
    public ArrayList<Rock> checkForSplitAstroid(Rock astroid) throws FileNotFoundException {
        ArrayList<Rock> splitRocks = new ArrayList<>();
        double astroidX = astroid.getImageView().getX();
        double astroidY = astroid.getImageView().getY();
        int minSplitAstroidSize = 20;
        if ((astroid.getSize() - astroid.getSize() / minSplitAstroidSize * 2)   > 40)
        {
            splitRocks.add(generateSplitAstroid(40, (int) (astroidX - astroid.getSize()/6),(int) astroidY - astroid.getSize()/6, (int) (astroid.getSize() * 0.7), 0.2, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(220, (int) astroidX + astroid.getSize()/6, (int) astroidY + astroid.getSize()/6, (int) (astroid.getSize() * 0.7) , 0.2, astroid.getFilePath()));

        }
        else if ((astroid.getSize() - astroid.getSize() / minSplitAstroidSize * 3)   > 30)
        {
            splitRocks.add(generateSplitAstroid(240, (int) (astroidX - astroid.getSize()/5),(int) astroidY - astroid.getSize()/5, (int) (astroid.getSize() * 0.6), 0.3, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(120, (int) astroidX + astroid.getSize()/5, (int) astroidY + astroid.getSize()/5, (int) (astroid.getSize() * 0.6) , 0.3, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(0, (int) (astroidX - astroid.getSize()/5),(int) astroidY + astroid.getSize()/5, (int) (astroid.getSize() * 0.6), 0.3, astroid.getFilePath()));
        }
        else if((astroid.getSize() - astroid.getSize() / minSplitAstroidSize * 4)   > 15)
        {
            splitRocks.add(generateSplitAstroid(270.0, (int) (astroidX - astroid.getSize()/4),(int) astroidY - astroid.getSize()/4, (int) (astroid.getSize() * 0.5), 0.4, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(90, (int) astroidX + astroid.getSize()/4, (int) astroidY + astroid.getSize()/4, (int) (astroid.getSize() * 0.5) , 0.4, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(0, (int) (astroidX - astroid.getSize()/4),(int) astroidY + astroid.getSize()/4, (int) (astroid.getSize() * 0.5), 0.4, astroid.getFilePath()));
            splitRocks.add(generateSplitAstroid(180, (int) astroidX + astroid.getSize()/4, (int) astroidY - astroid.getSize()/4, (int) (astroid.getSize() * 0.5) , 0.4, astroid.getFilePath()));

        }

        return splitRocks;
    }

}
