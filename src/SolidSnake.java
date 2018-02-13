import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

public class SolidSnake extends JPanel {
    int score =0; //Score of the player
    Random random = new Random();
    static int DIRECTION = 0; // Direction int 0 = right, 1= left , 2 = top, 3 = bottom
    Random rand = new Random();
    ArrayList<Pair> body = new ArrayList<Pair>(); //array which stores snakes co ordinates
    Pair food = new Pair(30, 50);  //location of the food
    boolean gameover =false;

    private void moveBall() {

    }

    public int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) random.nextInt(range) + min;
    }

    //Random function that generates food
    public void setValidRange() {
        while (true) {
            int x = randomWithRange(0, 35) * 10;
            int y = randomWithRange(0, 35) * 10;

            Pair p = new Pair(x, y);
            if (!body.contains(p)) {
                food.setFi(p.getFi());
                food.setSe(p.getSe());
                break;
            }
        }

    }

    //check if snake has collided with food
    public boolean detectCollision(int topx, int topy) {
        if (topx == food.getFi() && topy == food.getSe()) {
            setValidRange();
            score = score + 10;
            return true;
        }

        return false;
    }

    public void checkGameOver(int topx,int topy){
        Pair p = new Pair(topx,topy);
            if(body.contains(p))
                gameover = true;
            else
               gameover = false;

    }

    // print snake food
    private void spwanFood(Pair food, Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(food.getFi() + 1, food.getSe(), 9, 9);
    }

    //print snakes body
    public void printSnake(ArrayList<Pair> snake, Graphics2D g2d) {
        for (int i = 0; i < snake.size(); i++) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(snake.get(i).getFi() + 1, snake.get(i).getSe(), 9, 9);
        }
    }

    //initialize the snake with length 3
    public void init() {
        body.add(new Pair(0, 0));
        body.add(new Pair(10, 0));
        body.add(new Pair(20, 0));

    }

    //check if snake goes out of bounds
    public int modulus(int x) {
        if (x >= 400)
            x = 0;
        if (x < 0)
            x = 390;
        return x;

    }

    //Snake Traversal
    public void traversal() {
        if (body.size() > 0) {

            for (int i = 0; i < body.size() - 1; i++) {
                body.get(i).setFi(body.get(i + 1).getFi());
                body.get(i).setSe(body.get(i + 1).getSe());
            }
            int topx = body.get(body.size() - 1).getFi();
            int topy = body.get(body.size() - 1).getSe();
            switch (DIRECTION) {
                case 0:
                    checkGameOver(topx + 10, topy);
                    if (detectCollision(topx + 10, topy))
                        body.add(new Pair(topx + 10, topy));
                    else
                        body.get(body.size() - 1).setFi(modulus(topx + 10));
                    break;
                case 1:
                    checkGameOver(topx - 10, topy);
                    if (detectCollision(topx - 10, topy))
                        body.add(new Pair(topx - 10, topy));
                    else
                        body.get(body.size() - 1).setFi(modulus(topx - 10));
                    break;
                case 2:
                    checkGameOver(topx , topy+10);
                    if (detectCollision(topx, topy + 10))
                        body.add(new Pair(topx, topy + 10));
                    else
                        body.get(body.size() - 1).setSe(modulus(topy + 10));
                    break;
                case 3:
                    checkGameOver(topx , topy-10);
                    if (detectCollision(topx, topy - 10))
                        body.add(new Pair(topx, topy - 10));
                    else
                        body.get(body.size() - 1).setSe(modulus(topy - 10));
                    break;
            }

        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        printSnake(body, g2d);
        spwanFood(food, g2d);
       /* g2d.setColor(Color.BLACK);
        g2d.fillRect(x, y, 10, 10);*/

    }

    public void paintGameOver(Graphics g){
        super.paint(g);
        Font font =new Font(Font.SANS_SERIF,Font.BOLD,25);
        g.setFont(font);
        g.setColor(Color.RED);
        g.drawString("Game Over",120,100);
        g.drawString("Your Score: "+Integer.toString(score),110,150);
    }

    public static void main(String args[]) {
        SolidSnake snake = new SolidSnake();
        snake.init();
        JFrame jFrame = new JFrame("Sample Game");
        jFrame.add(snake);
        jFrame.setSize(400, 400);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        Graphics g = jFrame.getGraphics();
        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 39) {
                    if (DIRECTION != 1)
                        DIRECTION = 0;
                }
                if (e.getKeyCode() == 37) {
                    if (DIRECTION != 0)
                        DIRECTION = 1;
                }
                if (e.getKeyCode() == 40) {
                    if (DIRECTION != 3)
                        DIRECTION = 2;
                }
                if (e.getKeyCode() == 38) {
                    if (DIRECTION != 2)
                        DIRECTION = 3;
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


        while (true) {
            if(snake.gameover)
                break;
            snake.traversal();
            snake.repaint();

            try {
                Thread.sleep(105);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        snake.paintGameOver(g);
    }


}
//pair class to maintain  x and y co ordinates of the snakes
class Pair {
    private Integer fi;
    private Integer se;

    Pair(int x, int y) {
        fi = x;
        se = y;
    }


    public Integer getFi() {
        return fi;
    }

    public void setFi(Integer fi) {
        this.fi = fi;
    }

    public Integer getSe() {
        return se;
    }

    public void setSe(Integer se) {
        this.se = se;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (!fi.equals(pair.fi)) return false;
        return se.equals(pair.se);
    }

    @Override
    public int hashCode() {
        int result = fi.hashCode();
        result = 31 * result + se.hashCode();
        return result;
    }
}

