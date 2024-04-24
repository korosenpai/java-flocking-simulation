// jframe tut -> https://www.youtube.com/watch?v=om59cwR7psI&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
// vid 2 1:11
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("flocking sim");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); // resize window to fit preferred size (specified in gamepanel)

        window.setLocationRelativeTo(null); // specify location of the window // unll -> display at center of screen
        window.setVisible(true); 

        gamePanel.start();

    }

}
