import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePanel extends JPanel  implements ActionListener {

    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 600;


    final int FPS = 30;
    final int DELAY = 1000 / FPS;
    Timer timer;

    boolean restart;

    final int BOIDS_NUMBER = 50;
    Boid[] flock = new Boid[BOIDS_NUMBER];


    public static void main(String[] args) {
        System.out.println("res: " + SCREEN_HEIGHT + "(height) x " + SCREEN_WIDTH + "(width)");
    }

    GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // set window size
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // all drawing from this component will be done in an offscreen painting buffer -> improves performance

        this.setFocusable(true); // to use keyAdapter
        this.requestFocusInWindow();
        this.addKeyListener(new MyKeyAdapter());
    }

    public void start() {
        restart = false;

        System.out.println("game loop running, fps: " + FPS);
        if (timer == null) {
            timer = new Timer(DELAY, this);
            timer.setRepeats(true);
            timer.start();
        }

        // populate boid array
        for (int i = 0; i < flock.length; i++) {
            flock[i] = new Boid(SCREEN_WIDTH, SCREEN_HEIGHT);
        }


    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            // System.out.println(e.getKeyCode());
            switch (e.getKeyCode()) {
                case 32:
                    restart = true;
                    break;

                default:
                    break;
            }
        }
    }

    // called when the timer ends
    public void actionPerformed(ActionEvent event) {
        // update the screen
        //System.out.println("starting new cycle");

        if (restart) start();

        // move all flock
        for (Boid b: flock){
            b.align(flock);
            b.update(SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        repaint(); // to call paintComponent

    }

    // called by repaint()
    public void paintComponent(Graphics g) {
        // builtin method
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; // 2d gives more access on geometry, coords, ...
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        g2.setColor(new Color(255, 255, 255));
        for (Boid b: flock) {
                g2.fillOval((int)b.position.x(), (int)b.position.y(), 20, 20);
        }

        g2.dispose();

    }


}
