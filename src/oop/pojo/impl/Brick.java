package oop.pojo.impl;

import oop.pojo.abstacts.AbstractBFObject;

import java.awt.*;

public class Brick extends AbstractBFObject {
    public Brick(int x, int y) {
        super(x, y);
    }
    public Brick(){
        super();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(250, 132, 6));
        g.fillRect(getX(),getY(), 64, 64);
    }
}
