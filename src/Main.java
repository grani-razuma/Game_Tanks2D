import javax.swing.*;
import java.awt.*;

public class Main extends JPanel{

    final int BF_WIDTH = 576;
    final int BF_HEIGHT = 576;

    // 1 - up, 2 - down, 3 - left, 4 - right.
    int direction = 2;

    //координаты пули
    int bulletX = 320;
    int bulletY = 320;

    // Дефолтные координаты танка
    int x = 256;
    int y = 256;

    void moving(int direction) throws Exception {
        this.direction = direction;

        if(direction == 1){
            y--;
        } else if (direction == 2){
            y++;
        } else if (direction == 3){
            x--;
        } else if (direction == 4) {
            x++;
        }
        Thread.sleep(33);
        repaint();
    }

    void runTheGame() throws Exception{
        while (y!=0){
            moving(1);
        }
    }

    //Функции движения
    void moveUp() throws Exception {
        direction = 1;
        while(y!=0){
            y--;
            Thread.sleep(33);
            repaint();
        }
    }


    void moveDown() throws Exception {
        direction = 2;
        while(y!= BF_HEIGHT-64){
            y++;
            Thread.sleep(33);
            repaint();
        }
    }


    void moveLeft() throws Exception {
        direction = 3;
        while( x!=0){
            x--;
            Thread.sleep(33);
            repaint();
        }
    }


    void moveRight() throws Exception {
        direction = 4;
        while(x != BF_WIDTH-64){
            x++;
            Thread.sleep(33);
            repaint();
        }
    }

        //логика движения по функциям
        /*if(direction == 1){
            moveUp();
        } else if (direction == 2){
            moveDown();
        } else if (direction == 3){
            moveLeft();
        } else if (direction == 4){
            moveRight();
        }*/


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
        //Рисуем танк
        g.setColor(Color.YELLOW);
        g.fillRect(x,y, 64,64);

        //Рисуем пушку
        g.setColor(Color.MAGENTA);

        //Добавляем положение пушки
        if (direction == 1) {
            g.fillRect(x + 20,y - 12,24, 34);
        }else if (direction == 2){
            g.fillRect(x + 20,y + 42,24, 34);
        }else if (direction == 3){
            g.fillRect(x - 12,y + 20,34, 24);
        }else if (direction == 4){
            g.fillRect(x + 42 ,y + 20,34, 24);
        }

        g.setColor(Color.BLACK);
        g.fillRect(bulletX,bulletY, 14,14);
    }
}
