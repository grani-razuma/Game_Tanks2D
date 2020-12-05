package oop;

import oop.enums.Direction;
import oop.pojo.abstacts.AbstractBFObject;
import oop.pojo.impl.Brick;
import oop.pojo.impl.Bullet;
import oop.pojo.impl.Tank;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BattleField extends JPanel{

    final int BF_WIDTH = 576;
    final int BF_HEIGHT = 576;

    final int QUADRANT_SIZE = 64;

    final int UP = 1;
    final int DOWN = 2;
    final int LEFT = 3;
    final int RIGHT = 4;

    final int TOP_Y = BF_HEIGHT - QUADRANT_SIZE;
    final int TOP_X = BF_WIDTH - QUADRANT_SIZE;


    AbstractBFObject[][] objects =  {
            {new Brick(),"B" , "B" , "G" , "G" , "W" , "G" , "W" , "B"},
            {"G" ,"G" , "G" , "G" , "G" , "G" , "G" , "W" , "B"},
            {"B" ,"B" , "B" , "G" , "G" , "G" , "G" , "G" , "B"},
            {"B" ,"B" , "B" , "G" , "G" , "G" , "G" , "B" , "B"},
            {"G" ,"G" , "G" , "G" , "G" , "G" , "G" , "B" , "B"},
            {"G" ,"G" , "G" , "G" , "G" , "G" , "G" , "G" , "B"},
            {"B" ,"B" , "G" , "G" , "G" , "W" , "G" , "W" , "B"},
            {"G" ,"B" , "B" , "G" , "G" , "W" , "W" , "W" , "B"},
            {"B" ,"B" , "G" , "G" , "G" , "G" , "G" , "W" , "B"},
    };

    // 1 - up, 2 - down, 3 - left, 4 - right.
    Direction direction = Direction.LEFT;

    //координаты пули
    private Bullet bullet;

    // Дефолтные координаты танка
    private Tank tank;

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

    void randomMove() throws Exception {
        Random random = new Random();
        int direction = random.nextInt(4) + 1;
        moving(direction);
    }

    boolean canNotMove(){
        return (direction == UP && tankY == 0) || (direction == DOWN && tankY == TOP_Y)
                || (direction == LEFT && tankX == 0) || (direction == RIGHT && tankX == TOP_X)
                || nextObject(direction).equals("B");
    }

    String nextObject(int direction){
        int y = tankY;
        int x = tankX;

        switch (direction){
            case UP:
                y-=64;
                break;
            case DOWN:
                y+=64;
                break;
            case LEFT:
                x-=64;
                break;
            case RIGHT:
                x+=64;
                break;
        }

        return objects[y/ QUADRANT_SIZE][x/ QUADRANT_SIZE];
    }

    boolean processInterception() {
        int y = bulletY/64;
        int x = bulletX/64;


        if (objects[y][x].equals("B") && y >= 0 && y<= 8 && x >= 0 && x<=8 ){
            objects[y][x] = "G";
            return true;
        }
        return false;
    }

    //Метод выстрела из танка
    void fire() throws Exception{
        bulletX = tankX + 25;
        bulletY = tankY + 25;

        //делаем условие, по которому летит пуля
        while (bulletX > 0 && bulletX < BF_WIDTH &&
                bulletY > 0 && bulletY < BF_HEIGHT){

            switch (direction){
                case 1:
                    bulletY--;
                    break;
                case 2:
                    bulletY++;
                    break;
                case 3:
                    bulletX--;
                    break;
                case 4:
                    bulletX++;
                    break;
            }

            if (processInterception()){
                destroyBullet();
            }

            Thread.sleep(5);
            repaint();
        }

        //Когда пуля выходят цикла, она улетает за "карту"
        destroyBullet();
    }


    void destroyBullet(){
        bulletX = -100;
        bulletY = -100;
        repaint();
    }


    void runTheGame() throws Exception{

        //while(tankY != 0){
        while (true){
            randomMove();
        }
    }


    public static void main(String[] args) throws Exception {
        BattleField main = new BattleField();
        main.runTheGame();

    }

    //Конструктор, чтобы обойти строгую типизацию вызова функций движения
    BattleField(){
        JFrame frame = new JFrame("Dandy Tanks 2D");
        frame.setMinimumSize(new Dimension(BF_WIDTH, BF_HEIGHT + 44));
        frame.getContentPane().add(this);
        frame.setLocation(0,0);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (AbstractBFObject[] abstractBFObjects: objects){
            for (AbstractBFObject bfObject : abstractBFObjects){
                bfObject.draw(g);
            }
        }


        //Рисуем танк
        tank.draw(g);

//        g.setColor(Color.YELLOW);
//        g.fillRect(tankX, tankY, QUADRANT_SIZE, QUADRANT_SIZE);

        //Рисуем пушку
        g.setColor(Color.MAGENTA);

        //Добавляем положение пушки
        if (direction == 1) {
            g.fillRect(tankX + 20, tankY - 12,24, 34);
        }else if (direction == 2){
            g.fillRect(tankX + 20, tankY + 42,24, 34);
        }else if (direction == 3){
            g.fillRect(tankX - 12, tankY + 20,34, 24);
        }else if (direction == 4){
            g.fillRect(tankX + 42 , tankY + 20,34, 24);
        }

        //Рисуем пулю
        bullet.draw(g);
//        g.setColor(Color.BLACK);
//        g.fillRect(bulletX,bulletY, 14,14);
    }
}
