import javax.swing.*;
import java.awt.*;

/**
 * A főmenüt nézetet kezelő osztály
 */
public class MainMenu extends JPanel implements GameView{
    private JButton playButton = new JButton("Play");
    private JButton selectButton = new JButton("Select map");
    private JButton makeButton = new JButton("Make map");
    private JButton quitButton = new JButton("Quit");
    private JPanel buttonPanel = new JPanel();

    /**
     * Konstruktor. Beállítja a menün lévő gombokat és funkcióit
     * @param game A játékkezelő osztály egy objektuma
     */
    public MainMenu(Game game){
        setLayout(new GridBagLayout());
        Color color = new Color(179, 239, 93);
        setBackground(color);
        buttonPanel.setBackground(color);
        buttonPanel.setLayout(new GridLayout(0,1,0,10));

        playButton.addActionListener((e) -> {
            game.changeView("game");
        });
        selectButton.addActionListener((e) -> {
            game.changeView("selectMap");
        });
        makeButton.addActionListener((e) -> {
            game.changeView("makeMap");
        });
        quitButton.addActionListener(
                (e)->game.quit()
        );

        playButton.setAlignmentX(CENTER_ALIGNMENT);
        playButton.setMaximumSize(new Dimension(100,30));
        buttonPanel.add(playButton);

        selectButton.setAlignmentX(CENTER_ALIGNMENT);
        selectButton.setMaximumSize(new Dimension(100,30));
        buttonPanel.add(selectButton);

        makeButton.setAlignmentX(CENTER_ALIGNMENT);
        makeButton.setMaximumSize(new Dimension(100,30));
        buttonPanel.add(makeButton);

        quitButton.setAlignmentX(CENTER_ALIGNMENT);
        quitButton.setMaximumSize(new Dimension(100,30));
        buttonPanel.add(quitButton);

        add(buttonPanel);

    }
}
