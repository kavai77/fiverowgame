package com.himadri.fiverowgame;

import lombok.Value;

@Value
public class Rectangle {
    int width, height;

    public boolean contains(int x, int y) {
        if ((x | y) < 0) {
            return false;
        }
        return x < width && y < height;
    }

    public boolean contains(Point point) {
        return contains(point.x, point.y);
    }
}
