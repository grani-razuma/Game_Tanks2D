import javax.swing.*;
import java.awt.*;

public class Main extends JPanel{

    final int BF_WIDTH = 576;
    final int BF_HEIGHT = 576;

    final int OBJECT_SIZE = 64;

    final int UP = 1;
    final int DOWN = 2;
    final int LEFT = 3;
    final int RIGHT = 4;

    String[][] objects =  {
            {"B" ,"B" , "B" , "G" , "G" , "W" , "G" , "W" , "B"},
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
    int direction = 1;

    //координаты пули
    int bulletX = -100;
    int bulletY = -100;

    // Дефолтные координаты танка
    int tankX = 256;
    int tankY = 256;

    void moving(int direction) throws Exception {
        this.direction = direction;

        if(direction == 1){
            tankY--;
        } else if (direction == 2){
            tankY++;
        } else if (direction == 3){
            tankX--;
        } else if (direction == 4) {
            tankX++;
        }
        Thread.sleep(33);
        repaint();
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
            Thread.sleep(5);
            repaint();
        }

        //Когда пуля выходят цикла, она улетает за "карту"
        bulletX = -100;
        bulletY = -100;

    }

    void runTheGame() throws Exception{
        //fire();
        while(tankX != 0){
            moving(LEFT);
        }
    }


    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.runTheGame();

    }

    //Конструктор, чтобы обойти строгую типизацию вызова функций движения
    Main(){
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

        for (int y = 0; y < objects.length; y++) {
            for (int x = 0; x < objects.length; x++) {
                switch (objects[y][x]) {
                    case "B":
                        g.setColor(new Color(250, 132, 6));
                        break;
                    case "W":
                        g.setColor(new Color(21, 175, 222));
                        break;
                    case "G":
                        g.setColor(new Color(104, 87, 60));
                        break;
                }
                g.fillRect(x * OBJECT_SIZE, y * OBJECT_SIZE, OBJECT_SIZE, OBJECT_SIZE);
            }
        }


        //Рисуем танк
        g.setColor(Color.YELLOW);
        g.fillRect(tankX, tankY,OBJECT_SIZE,OBJECT_SIZE);

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
        g.setColor(Color.BLACK);
        g.fillRect(bulletX,bulletY, 14,14);
    }
}
