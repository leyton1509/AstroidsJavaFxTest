package com.example.gamenewjava.Assets;

import java.io.FileNotFoundException;

public class SplitAstroid extends Rock{

    public SplitAstroid(String _name, int _height, int _width, String _filepath, int _startX, int _startY, double _moveSpeed) throws FileNotFoundException {
        super(_name, _height, _width, _filepath, _startX, _startY, _moveSpeed);
    }
}
