package oop.pojo.impl;

import oop.enums.Direction;
import oop.pojo.abstacts.AbstractMovableObject;

import java.awt.*;

public class Tank extends AbstractMovableObject {
    public Tank(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {

    }

    void moving(Direction direction) throws Exception {
        this.direction = direction;

        if (canNotMove()){
            System.out.println("Can't Move!!!");
            fire();
            return;
        }

        for (int i = 0; i < QUADRANT_SIZE; i++) {
            if (direction == UP) {
                tankY--;
            } else if (direction == DOWN) {
                tankY++;
            } else if (direction == LEFT) {
                tankX--;
            } else if (direction == RIGHT) {
                tankX++;
            }

            Thread.sleep(33);
            repaint();
        }
    }
}
