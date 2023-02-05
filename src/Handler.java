import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * A játéktéren lévő objektumokat kezeli
 */

public class Handler {
    private LinkedList<GameObject> gameObjects = new LinkedList<>();
    private LinkedList<GameObject> destroyables = new LinkedList<>();
    private LinkedList<GameObject> addables = new LinkedList<>();
    private Player player = new Player(100,200);
    private Path path;
    private int spawnCount = 50;
    private int originalSpawnCount = spawnCount;
    public  Handler(){

    }

    /**
     * Megadja, hoyg véget ért e a játék
     * @return igaz, ha véget ért
     */
    public boolean over(){
        if (player.hpUnderZero()){

            return true;
        }
        if (spawnCount <= 0 && getEnemies().size()==0){
            return true;
        }
        return false;
    }

    /**
     * Megadja, hogy a játékos nyert e
     * @return igaz, ha a játékos nyert
     */
    public boolean playerWon(){
        return !player.hpUnderZero();
    }

    /**
     * Megidéz egy új ellenséget, és csökkenti a spawnCount számlálót
     */
    public void spawn(){
        if (spawnCount<=0) return;
        spawnCount--;
        queueForAdd(new Enemy(this,path,100,25,5));
    }

    /**
     * beállítja az ellenségek által követett útvonalat egy új értékre
     * @param p az új útvonal
     */
    public void newPath(Path p){
        path = p;
    }

    /**
     * minden játéktéren lévő objektumot léptet a tick függvénnyel, a törlendőket törli, a hozzáadandókat felveszi a játéktérre
     * @param delta
     */
    public synchronized void tick(double delta){
        for (GameObject g:gameObjects){
            g.tick(delta);
        }
        if (!addables.isEmpty()){
            gameObjects.addAll(addables);
            addables.clear();
        }
        gameObjects.removeAll(destroyables);
        destroyables.clear();
    }

    /**
     * Kirajzolja az összes játéktéren lévő objektumot a saját maguk által meghatározott rétegre
     * @param g a grafika objektum, ami végrehajtja a kirajzolást
     */
    public synchronized void render(Graphics g){
        path.render(g);
        int layers = getNumberOfLayers();

        // minden layerhez hozzárendel egy GameObject típusú listát
        LinkedList<LinkedList<GameObject>> layerObjectsList = new LinkedList<>();
        for (int i = 0; i < layers+1; i++) {
            layerObjectsList.add(getObjectsWithLayer(i));
        }

        for (LinkedList<GameObject> objectsWithLayer :layerObjectsList){
            for (GameObject obj:objectsWithLayer) {
                obj.render(g);
            }
        }
        player.drawHp(g);

    }

    /**
     * megadja, hogy melyik a legnagyobb értékű réteg
     * @return a legnagyobb értékű réteg értéke
     */
    public int getNumberOfLayers(){
        int maxLayer = 0;
        for (GameObject g : gameObjects) {
            if (g.getLayer() > maxLayer)
                maxLayer = g.getLayer();
        }


        return maxLayer;
    }

    /**
     * Minden objektumot megad, amik a kapott rétegre rajzolandóak
     * @param layer a réteg, amire ki kell rajzolni
     * @return az objektumok az adott réteggel
     */
    public LinkedList<GameObject> getObjectsWithLayer(int layer){
        LinkedList<GameObject> layerObjects = new LinkedList<>();
        for (GameObject g : gameObjects) {
            if (g.getLayer() == layer)
                layerObjects.add(g);
        }
        return layerObjects;
    }

    /**
     * felveszi a törlendők közé a kapott objektumot
     * @param g a törlendő objektum
     */
    public void  queueForDestroy(GameObject g){
        destroyables.add(g);
    }

    /**
     * felveszi a hozzáadandók közé a kapott objektumot
     * @param g a hozzáadandó objektum
     */
    public void  queueForAdd(GameObject g){
        addables.add(g);
    }

    /**
     * Akkor hívódik meg, amikor a felhasználó lenyomta az egeret.
     * minden játéktéren lévő objektumot értesít, hogy az egér le lett nyomva
     * @param e az egérrel kapcsolatos információkat tartalmazó objektum
     */
    public synchronized void onMousePressed(MouseEvent e){
        for (GameObject g:gameObjects){
            g.onMousePressed(e);

        }
    }
    /**
     * Akkor hívódik meg, amikor a felhasználó megmozdította az egeret.
     * minden játéktéren lévő objektumot értesít, hogy az egér meg lett mozdítva
     * @param e az egérrel kapcsolatos információkat tartalmazó objektum
     */
    public synchronized void onMouseMoved(MouseEvent e){
        for (GameObject g:gameObjects){
            g.onMouseMoved(e);


        }
    }

    /**
     * minden játéktéren lévő objektumot felvesz a barátok vagy az ellenségek közé ( mindenki eldönti magáról)
     * @param enemies az ellenséget listája
     * @param friends a barátok listája
     */
    public void friendsAndFoes(LinkedList<Enemy> enemies, LinkedList<Friend> friends){
        for (GameObject g:gameObjects){
            g.friendOrFoe(enemies,friends);
        }
    }

    /**
     * Minden játéktéren lévő ellenséges játékobjektumot megad
     * @return az ellenséges objektumok
     */
    public LinkedList<Enemy> getEnemies() {
        LinkedList<Enemy> enemies = new LinkedList<>();
        LinkedList<Friend> friends = new LinkedList<>();
        friendsAndFoes(enemies,friends);
        return enemies;
    }
    /**
     * Minden játéktéren lévő barátságos játékobjektumot megad
     * @return a barátságos objektumok
     */
    public LinkedList<Friend> getFriends() {
        LinkedList<Enemy> enemies = new LinkedList<>();
        LinkedList<Friend> friends = new LinkedList<>();
        friendsAndFoes(enemies,friends);
        return friends;
    }

    /**
     * a játékos életerejét csökkenti a kapott értékkel
     * @param dmg a sebzés mértéke
     */
    public void damagePlayer(double dmg) {
        player.takeDamage(dmg);
    }

    /**
     * törli az összes játéktéren lévő játékobjektumot, illetve alaphelyzetbe állítja a játékost
     */
    public void reset() {
        gameObjects.clear();
        destroyables.clear();
        addables.clear();
        player.reset();
        spawnCount = originalSpawnCount;
    }

    /**
     * megadja, hogy a játékos mennyi pénzzel rendelkezik
     * @return a játékos pénzének mennyisége
     */
    public double getPlayerMoney() {
        return player.getMoney();
    }

    /**
     * Ha a játékosnak van elég pénze, akkor a kapott tornyot hozzáadja a játéktér objektumaihoz
     * @param t a torony, amit a játékos megvesz
     */
    public void playerBuy(Tower t) {
        if (player.loseMoney(t.getCost())){
            queueForAdd(t);
        }
    }

    /**
     * a kapott elenség törlődik a játéktérről és a játékos megkapja a törölt ellenség értékét pénzben
     * @param enemy
     */
    public void killEnemy(Enemy enemy) {
        player.loseMoney(-1*enemy.getValue());
        queueForDestroy(enemy);
    }
}
