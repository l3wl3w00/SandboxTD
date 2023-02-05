import javax.swing.*;
import java.awt.*;

/**
 * A pályakészítő nézetet kezelő osztály
 */
public class MakeMapView extends JPanel implements GameView {
    private JButton backButton = new JButton("Back");
    private JButton saveButton = new JButton("Save");
    private JTextField textField = new JTextField();
    private JPanel buttonPanel = new JPanel();
    private PathMaker pathMaker = new PathMaker();
    private Game game;
    /**
     * Konstruktor. Beállítja a menün lévő gombokat és funkcióit
     * @param g A játékkezelő osztály egy objektuma
     */
    public MakeMapView(Game g){
        game = g;

        setBackground(Color.GRAY);
        setLayout(new BorderLayout());

        buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,20,10));
        buttonPanel.add(saveButton);
        buttonPanel.add(textField);
        textField.setPreferredSize(new Dimension(100,20));
        textField.setToolTipText("Name your map!");

        buttonPanel.add(backButton);

        backButton.addActionListener((e)->{
            game.changeView("menu");
        });
        saveButton.addActionListener((e -> {
            pathMaker.setPathName(textField.getText());
            pathMaker.save();
            resetPathMaker();
            textField.setText("");
        }));

        add(buttonPanel,BorderLayout.NORTH);


        addMouseListener(pathMaker);
    }

    /**
     * Az aktuális pályát alaphelyzetbe állítja
     */
    @Override
    public void onAppearing(){
        resetPathMaker();
    }
    /**
     * Az aktuális pályát alaphelyzetbe állítja
     */
    private void resetPathMaker(){
        pathMaker = new PathMaker();
        addMouseListener(pathMaker);
    }

    /**
     * kirajzolja a pályát
     * @param g a grafika objektum, amivel a kirajzolás történik
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        pathMaker.render(g);
        paintComponents(g);
        g.dispose();
    }
}
