import javax.swing.*;
import java.awt.*;

/** A játék végén a játékos elé táruló nézet. Ha nyert, akkor gratulál neki, ha vesztett akkor nem. Innen ki lehet lépni a játékból vagy újra lehet kezdeni*/
public class EndGameView extends JPanel implements GameView {
    private JButton restart = new JButton("Restart");
    private JButton quit = new JButton("Quit");
    private JLabel label1 = new JLabel();
    private JLabel label2 = new JLabel();
    private GamePanel gamePanel;
    public EndGameView(GamePanel gamePanel, Game game){
        this.gamePanel = gamePanel;
        setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1,0,10));
        panel.add(label1);
        label1.setText("Game Over!");
        panel.add(label2);
        restart.addActionListener((a)->{
            game.restart();
        });
        panel.add(restart);

        quit.addActionListener((a)->{
            game.quit();
        });
        panel.add(quit);
        add(panel);
    }

    /**
     * beállítja a játékost fogadó szöveget attól függően hogy nyert e vagy vesztett
     */
    @Override
    public void onAppearing(){
        if (gamePanel.playerWon()){
            label2.setText( "You won! Great job!");
        } else {
            label2.setText( "You lost.");
        }
    }

}
