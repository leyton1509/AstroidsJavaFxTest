package com.example.gamenewjava.AssetControllers;

import com.example.gamenewjava.Assets.PowerUps.BasePowerUp;
import com.example.gamenewjava.Assets.PowerUps.TripleBullets;

import java.io.FileNotFoundException;

public class PowerUpsController {

    /**
     * The width of the level
     */
    private final int LEVEL_WIDTH;

    /**
     * The height of the level
     */
    private final int LEVEL_HEIGHT;


    public PowerUpsController(int width, int height) {
        LEVEL_WIDTH = width;
        LEVEL_HEIGHT = height;
    }


    private long timeSinceLastHealthPack = System.currentTimeMillis() + 15000;


    /**
     * @return Gets the time since last health pack generated.
     */
    public long getTimeSinceLastShipIncrease() {
        return timeSinceLastHealthPack;
    }

    /**
     * @param _timeSinceLastHealthPack The time to set the time since
     */
    public void setTimeSinceLastShipIncrease(long _timeSinceLastHealthPack) {
        this.timeSinceLastHealthPack = _timeSinceLastHealthPack;
    }

    public BasePowerUp spawnNewPowerUp() throws FileNotFoundException {
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

        int ran = (int) (Math.random() * (0) + 0);

        BasePowerUp pu;

        switch (ran) {
            default -> pu = new TripleBullets("PowerUp", 20, 20, "imgs/powerup.png", ranX, ranY, 0.5);
        }


        if (ranX < LEVEL_WIDTH / 2 && ranY < LEVEL_HEIGHT / 2) {
            pu.getImageView().setRotate((int) (Math.random() * (170 - 100) + 100));
        } else if (ranX > LEVEL_WIDTH / 2 && ranY < LEVEL_HEIGHT / 2) {
            pu.getImageView().setRotate((int) (Math.random() * (260 - 190) + 190));
        } else if (ranX < LEVEL_WIDTH / 2 && ranY > LEVEL_HEIGHT / 2) {
            pu.getImageView().setRotate((int) (Math.random() * (80 - 10) + 80));
        } else if (ranX > LEVEL_WIDTH / 2 && ranY > LEVEL_HEIGHT / 2) {
            pu.getImageView().setRotate((int) (Math.random() * (350 - 260) + 260));
        }

        return pu;
    }

    public void effect(){

    }

    }
