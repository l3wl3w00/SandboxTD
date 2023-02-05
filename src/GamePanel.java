import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * A játék nézetet kezeli, itt történik a játék érdemi része
 */
public class GamePanel extends JPanel implements GameView, MouseMotionListener, MouseListener {
    private Handler handler = new Handler();
    private JButton backButton = new JButton("Back");
    private JButton tower1Button = new JButton("normal");
    private JButton tower2Button = new JButton("exploding");
    private JButton tower3Button = new JButton("slowing");
    private JButton tower4Button = new JButton("fire");
    private JLabel playerMoneyText = new JLabel();
    private JPanel hud = new JPanel();
    private DrawPanel drawPanel = new DrawPanel();
    private Game game;
    private Timer spawnTimer = new Timer(5.0,0.1,1.0);

    /**
     * Erre rajzolódik a játék
     */
    private class DrawPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            handler.render(g);
            g.dispose();
        }
    }

    /**
     * Minden gombot beállít, és elrendezi őket a képernyőn
     * @param g A játékkezelő osztály egy objektuma
     */
    public GamePanel(Game g){
        game = g;
        setBackground(Color.GRAY);
        setLayout(new BorderLayout());//new BoxLayout(this,BoxLayout.X_AXIS));
        drawPanel.setBackground(new Color(179, 239, 93));
        addMouseListener(this);
        addMouseMotionListener(this);
        backButton.addActionListener((a) -> {
            game.changeView("menu");
        });

        tower1Button.addActionListener((e)->{
            Tower t = new Tower(
                    new Vector2D(
                            tower1Button.getX() + drawPanel.getWidth(),
                            tower1Button.getY()
                    ),handler,200
            );
            handler.playerBuy(t);
        });
        tower1Button.setText(tower1Button.getText()+" cost: "+40);

        hud.add(tower1Button);

        tower2Button.addActionListener((e)->{
            ExplosionTower t = new ExplosionTower(
                    new Vector2D(
                            tower2Button.getX() + drawPanel.getWidth(),
                            tower2Button.getY()
                    ),handler,300
            );
            handler.playerBuy(t);
        });
        tower2Button.setText(tower2Button.getText()+" cost: "+ExplosionTower.cost);
        hud.add(tower2Button);

        tower3Button.addActionListener((e)->{
            SlowingTower t = new SlowingTower(
                    new Vector2D(
                            tower3Button.getX() + drawPanel.getWidth(),
                            tower3Button.getY()
                    ),handler,150
            );
            handler.playerBuy(t);
        });
        tower3Button.setText(tower3Button.getText()+" cost: "+SlowingTower.cost);
        hud.add(tower3Button);

        tower4Button.addActionListener((e)->{
            FireTower t = new FireTower(
                    new Vector2D(
                            tower4Button.getX() + drawPanel.getWidth(),
                            tower4Button.getY()
                    ),handler,1000
            );
            handler.playerBuy(t);
        });
        tower4Button.setText(tower4Button.getText()+" cost: "+FireTower.cost);
        hud.add(tower4Button);

        hud.add(backButton);

        hud.add(playerMoneyText);
        hud.setPreferredSize(new Dimension(150,0));
        add(hud,BorderLayout.EAST);
        add(drawPanel,BorderLayout.CENTER);

    }
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Akkor hívódik meg, amikor az egért megmozdítja a felhasználó
     * A handler objektumnak jelzi az egérmozgatást
     * @param e az egérről információt tartalmazó objektum
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        handler.onMouseMoved(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    /**
     * Akkor hívódik meg, amikor az egért lenyomja a felhasználó
     * A handler objektumnak jelzi az egérlenyomást
     * @param e az egérről információt tartalmazó objektum
     */
    @Override
    public void mousePressed(MouseEvent e) {
        handler.onMousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * megadja, hogy a játékos nyert e
     * @return igaz, ha a játékos nyert
     */
    public boolean playerWon() {
        return handler.playerWon();
    }

    /**
     * alaphelyzetbe állítja a játékot
     */
    public void reset() {
        handler.reset();
        spawnTimer.reset();
    }


    /**
     * A korábban kiválasztott pályát beállítja
     */
    @Override
    public void onAppearing(){

        handler.newPath(game.getActivePath());
    }

    /**
     * Megidéz ellenséget, ha a timer engedi. Minden játéktéren lévő objektumra meghívja a tick függvényt
     * @param delta ennyi idő telt el a függvény előző hívása óta
     */
    public void tick(double delta) {
        spawnTimer.tick(()->{
            handler.spawn();
        });
        handler.tick(delta);
        if (handler.over()){
            game.changeView("endGame");
        }
        playerMoneyText.setText("Money: "+handler.getPlayerMoney());
    }
}
