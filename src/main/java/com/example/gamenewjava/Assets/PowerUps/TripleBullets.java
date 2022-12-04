package com.example.gamenewjava.Assets.PowerUps;

import java.io.FileNotFoundException;

public class TripleBullets extends BasePowerUp {
    /**
     * @param _name      The name of asset
     * @param _height    The height of asset
     * @param _width     The width of asset
     * @param _filepath  The file path of image
     * @param _startX    The starting x coordinate
     * @param _startY    The starting y coordinate
     * @param _moveSpeed The starting speed
     * @throws FileNotFoundException e
     */
    public TripleBullets(String _name, int _height, int _width, String _filepath, int _startX, int _startY, double _moveSpeed) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY, _moveSpeed);
    }


}
