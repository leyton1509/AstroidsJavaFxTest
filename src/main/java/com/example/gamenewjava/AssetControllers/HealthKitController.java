package com.example.gamenewjava.AssetControllers;

import com.example.gamenewjava.Assets.HealthPack;
import com.example.gamenewjava.Assets.Rock;

import java.io.FileNotFoundException;

public class HealthKitController {

    /**
     * The width of the level
     */
    private final int LEVEL_WIDTH;

    /**
     * The height of the level
     */
    private final int LEVEL_HEIGHT;


    public HealthKitController(int width, int height) {
        LEVEL_WIDTH = width;
        LEVEL_HEIGHT = height;
    }


    private long timeSinceLastHealthPack = System.currentTimeMillis() + 15000;


    /**
     * @return Gets the time since last health pack generated.
     */
    public long getTimeSinceLastHealthKit() {
        return timeSinceLastHealthPack;
    }

    /**
     * @param _timeSinceLastHealthPack The time to set the time since
     */
    public void setTimeSinceLastHealthKit(long _timeSinceLastHealthPack) {
        this.timeSinceLastHealthPack = _timeSinceLastHealthPack;
    }

    public HealthPack spawnNewHealthKit() throws FileNotFoundException {
        int ranX;
        int ranY;

        ranX = (int) (Math.random() * ((LEVEL_WIDTH + 100) - (-100)) + (-100));

        if (ranX < 0 || ranX > LEVEL_WIDTH) {
            ranY = (int) (Math.random() * ((LEVEL_HEIGHT + 90) - 90) - 90);
        } else {
            if ((int) Math.round(Math.random()) == 0) {
                ranY = (int) (Math.random() * ((LEVEL_HEIGHT + 90) - LEVEL_HEIGHT) + LEVEL_HEIGHT);
            } else {
                ranY = (int) (Math.random() * (90));
                ranY = ranY - 90;
            }

        }
        HealthPack hp = new HealthPack("HealthKit", 20, 20, "imgs/healthkit.png", ranX, ranY, 0.5, 50);
        // Sets the rotation to go into the player view

        if (ranX < LEVEL_WIDTH / 2 && ranY < LEVEL_HEIGHT / 2) {
            hp.getImageView().setRotate((int) (Math.random() * (170 - 100) + 100));
        } else if (ranX > LEVEL_WIDTH / 2 && ranY < LEVEL_HEIGHT / 2) {
            hp.getImageView().setRotate((int) (Math.random() * (260 - 190) + 190));
        } else if (ranX < LEVEL_WIDTH / 2 && ranY > LEVEL_HEIGHT / 2) {
            hp.getImageView().setRotate((int) (Math.random() * (80 - 10) + 80));
        } else if (ranX > LEVEL_WIDTH / 2 && ranY > LEVEL_HEIGHT / 2) {
            hp.getImageView().setRotate((int) (Math.random() * (350 - 260) + 260));
        }

        return  hp;

    }







}
