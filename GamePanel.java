import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

import javax.swing.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel  implements ActionListener {

    static final int SCREEN_WIDTH = 1600;
    static final int SCREEN_HEIGHT = 1000;

    final int FPS = 30;
    final int DELAY = 1000 / FPS;
    Timer timer;

    boolean restart;

    final int BOIDS_NUMBER = 500;
    Boid[] flock = new Boid[BOIDS_NUMBER];


    // slider to change values
    public JFrame sliderWindow;
    public SliderPanel sliderPanel;


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

        // start slider panel
        this.sliderWindow = new JFrame();
        sliderWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sliderWindow.setResizable(false);
        sliderWindow.setTitle("settings");

        this.sliderPanel = new SliderPanel();
        sliderWindow.add(this.sliderPanel);
        sliderWindow.pack(); // resize sliderWindow to fit preferred size (specified in gamepanel)

        sliderWindow.setLocationRelativeTo(null); // specify location of the sliderWindow // unll -> display at center of screen
        sliderWindow.setVisible(true); 


        System.out.println("ready.");
    }

    public void start() {
        restart = false;
        sliderPanel.reset();

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
                    // if space pressed
                    restart = true;
                    System.out.print("restarted: ");
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


        // System.out.print(this.sliderPanel.getAlignmentMult());
        // System.out.print(" - ");
        // System.out.print(this.sliderPanel.getCohesionMult());
        // System.out.print(" - ");
        // System.out.print(this.sliderPanel.getSeparationMult());
        // System.out.println();

        if (restart) start();

        // move all boids of the flock
        for (Boid b: flock){
            b.align(flock, this.sliderPanel.getAlignmentMult(), 1);
            b.cohesion(flock, this.sliderPanel.getCohesionMult(), 1);
            b.separation(flock, this.sliderPanel.getSeparationMult(), 1);
            b.update(SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        repaint(); // to call paintComponent

    }
    
    // some constants to create the boids triangles
    int L = 10;
    int L2 = 5;
    int L3 = 7;

    // called by repaint()
    public void paintComponent(Graphics g) {
        // builtin method
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; // 2d gives more access on geometry, coords, ...
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        for (Boid b: flock) {
            g2.setColor(new Color(b.colorR, b.colorG, b.colorB));

            //g2.fillOval((int)b.position.x() - 1, (int)b.position.y() - 1, 2, 2); // actual point

            // find inclination angle
            // by doing angle * 180 / PI it changes too rapidly
            // solution + 90 fixes orientation issues, that i have no clue why there were
            // there in the first place
            double angle = Math.atan2(b.velocity.y(), b.velocity.x()) + 90;

            // setup transform
            AffineTransform oldTransform = g2.getTransform();

            AffineTransform transform = new AffineTransform();
            transform.rotate(angle, b.position.x(), b.position.y());
            g2.setTransform(transform);

            // draw triangles
            // can use drawPolygon or fillPolygon
            g2.fillPolygon(new int[]{
                (int)b.position.x(),
                (int)b.position.x() + L2,
                (int)b.position.x() - L2
            },
            new int[]{
                (int)b.position.y() - L,
                (int)b.position.y() + L3,
                (int)b.position.y() + L3
            }, 3);


            g2.setTransform(oldTransform);

        }

        g2.dispose();

    }


}
