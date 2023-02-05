import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * Egy ellenség, amit le kell lőnie a tornyoknak.
 */
public class Enemy extends MovingGameObject {

    private Path path;
    private Iterator<Vector2D> currentPoint;
    private EnemyStateHandler stateHandler;
    public Enemy(Handler handler ,Path _path, double _hp,double damage,double value) {
        super(_path.getPoints().get(0).clone(),handler,Color.BLUE,15,3,0.1);
        stateHandler = new EnemyStateHandler(this,_hp,100,value,damage);
        path = _path;
        currentPoint = path.iterator();
        newGoalPos(currentPoint.next(),stateHandler.getMaxSpeed());

    }

    /**
     * Eltűnik a pályáról
     * a játékos megkapja az elpusztult ellenség értékét mint pénz
     */
    private void getKilledByPlayer(){
        getHandler().killEnemy(this);
    }

    /**
     * kirajzolja az életerőt ábrázoló csíkot, ami megmutatja, hogy a maximumhoz képest mennyi élete van az ellenségnek
     * @param g a grafika objektum amivel kirajzolja
     * @param width ilyen széles lesz az életerőcsík
     */
    public void drawHpBar(Graphics g, double width){
        int height = 5;
        double length = width*2;
        Vector2D drawPos = new Vector2D(
                (int)getPos().x-width,
                (int)getPos().y-width-height*2
        );

        stateHandler.drawHpBar(g,drawPos,length,height);

    }

    /**
     * megadja az állapotára mutató referenciát
     * @return
     */
    public EnemyStateHandler getStateHandler(){
        return stateHandler;
    }

    /**
     * Hozzáadja magát az ellenségek listájához
     * @param enemies az ellenségek listája
     * @param friends a barátok listája
     */
    @Override
    public void friendOrFoe(LinkedList<Enemy> enemies, LinkedList<Friend> friends){
        enemies.add(this);
    }

    /**
     * követi az útvonalat, amit a path változóban tárol. Ha le van lassítva, akkor csíkot húz maga után. Ha elérte a célpontját,
     * akkor megsemmisíti önmagát, illetve a játékos veszít annyi életet amennyi a damage változó értéke
     * @param delta ennyi idő telt el a függvény előző hívása óta
     */
    @Override
    public void tick(double delta) {
        stateHandler.atEndOfTurn();
        stateHandler.tick(delta);
        move(delta,stateHandler.getMaxSpeed());
        if (stateHandler.isSlowed()){
            trailEffect(0.3);
        }

        if (atGoal()) {
            if (currentPoint.hasNext()){
                newGoalPos(currentPoint.next(),stateHandler.getMaxSpeed());
            } else {
                getHandler().damagePlayer(stateHandler.getDamage());
                queueForDestroy();
            }
        }
        if(stateHandler.toRemove()) getKilledByPlayer();
    }

    /**
     * kirajzolja az életerőcsíkot a drawHpBar függvénnyel
     * @param g a grafikai objektum amit a render függvény kapott híváskor
     */
    @Override
    public void onRender(Graphics g) {
        drawHpBar(g,20);
    }

    /**
     * Visszaadja az értékét. Ennyit kap a játékos ha megöli ezt az ellenséget
     * @return az értéke
     */
    public double getValue() {
        return stateHandler.getValue();
    }
}
