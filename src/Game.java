import javax.swing.*;
import java.awt.*;

/**
 * A játék vezérléséért felelős osztály, kezeli a nézeteket és a köztük lévő átváltást
 */
public class Game extends JPanel{
    private int WIDTH = 1280, HEIGHT = WIDTH / 16 * 9;
    private String view = "menu";
    private JPanel panelCont = new JPanel();
    private CardLayout cardLayout = new CardLayout();
    private PathHandler pathHandler = new PathHandler();
    private MainMenu menu = new MainMenu(this);
    private MakeMapView makeMap = new MakeMapView(this);
    private SelectMapView selectMap = new SelectMapView(this,pathHandler);
    private GamePanel gamePanel = new GamePanel(this);
    private EndGameView endGame = new EndGameView(gamePanel,this);
    private boolean running = false;

    /**
     * Minden nézet nevét beállítja és egy cardlayout-ot is, ami segítségével lehet váltogatni a
     * nézeteket, illetve betölti az összes elérhető pályát
     */
    public Game() {
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        panelCont.setLayout(cardLayout);

        gamePanel.setName("game");
        panelCont.add(gamePanel,gamePanel.getName());

        menu.setName("menu");
        panelCont.add(menu,menu.getName());

        makeMap.setName("makeMap");
        panelCont.add(makeMap,makeMap.getName());

        selectMap.setName("selectMap");
        panelCont.add(selectMap,selectMap.getName());

        endGame.setName("endGame");
        panelCont.add(endGame,endGame.getName());

        cardLayout.show(panelCont, view);
        add(panelCont);

        pathHandler.loadPaths();
        new Window(WIDTH, HEIGHT, "Nagyhazi",this);
    }

    /**
     * Megadja az aktív pályát
     * @return az aktív pálya
     */
    public Path getActivePath(){
        return pathHandler.getActivePath();
    }

    /**
     * Megváltoztatja a nézetet, és értesíti a régi és az új nézeteket a történtekről
     * @param v
     */
    public void changeView(String v){
        if (getView(view) == null){
            System.out.println("no such view");
            return;
        }
        getView(view).onDisappearing();
        cardLayout.show(panelCont, v);
        view = v;
        getView(view).onAppearing();
    }

    /**
     * A játék fő ciklusa. addig fut, ameddig a játék fut. Minden frame-ben kiszámolja hogy az előző hívás óta mennyi idő telt el,
     * majd meghívja a tick és repaint függvényt, amik a játék működéséért és a játék kirajzolásáért felelősek
     */
    public void run() {
        running = true;
        long lastTime = System.nanoTime();
        double ns = 1000000000;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running){
            long now = System.nanoTime();
            delta = (now - lastTime) / ns;
            lastTime = now;

            if (isVisible())
                tick(delta);
            repaint();
            frames++;
            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }

    }

    /**
     * Ha az éppen aktív nézet a játéknézet, azaz a játék folyamatban van, akkor a játék minden objektumát lépteti.
     * @param delta az előző hívás óta eltelt idő
     */
    private void tick(double delta){
        if (view.equals(gamePanel.getName())){
            gamePanel.tick(delta);
        }
    }

    /**
     * Meghatározza a kapott névvel rendelkező nézetet
     * @param name a nézet neve
     * @return a nézet mint "GameView" objektum
     */
    public GameView getView(String name){
        Component[] components = panelCont.getComponents();
        for (Component comp : components) {
            if (comp.getName().equals(name)) {
                return (GameView)comp;
            }
        }
        return null;
    }

    /**
     * a main függvény
     * @param args
     */
    public static void main(String args[]){
        new Game();
    }

    /**
     * leállítja a játék futását
     */
    public void quit() {
        running = false;
    }

    /**
     * alaphelyzetbe állítja a nézeteket
     */
    public void restart() {
        changeView(menu.getName());
        gamePanel.reset();
    }
}
