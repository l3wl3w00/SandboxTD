import java.awt.*;
import javax.swing.*;

/**
 * Azt az ablakot kezelő osztály, amiben fut a játék
 */
public class Window {
        public Window(int width, int height, String title, Game game) {
            JFrame frame = new JFrame(title);
            frame.setPreferredSize(new Dimension(width, height));
            frame.setMinimumSize(new Dimension(width/2, height/2));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.add(game);
            frame.setVisible(true);
            frame.pack();
            game.run();
            frame.dispose();
        }
}