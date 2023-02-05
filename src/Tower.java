import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * Lövedékeket lövő torony. Minden olyan ellenségre lő, aki a hatótávon belül van
 */
public class Tower extends GameObject implements Friend{
    private int range;
    private double attackSpeed;
    private Timer attackTimer;
    private boolean drag = true;
    private boolean inFocus = true;
    public Tower(Vector2D vector, Handler handler,int _range) {
        super(vector,handler,Color.BLACK,25,4);
        range = _range;
        initAttackSpeed(2.0);
    }

    /**
     * Beállítja a támadási sebességet a kapott értékre
     * @param atkSpeed az új támadási sebesség
     */
    public void initAttackSpeed(double atkSpeed){
        attackSpeed = atkSpeed;
        attackTimer = new Timer(1/attackSpeed,0);
    }

    /**
     * Felvesz egy lövedéket a játéktérre a saját pozíciójára
     * @param enemy a lövedék célpontja, akit követ
     */
    public void shoot(Enemy enemy){
        addGameObj(new Bullet(getPos().clone(),getHandler(),enemy,1000,Color.RED,5));
    }

    /**
     * Megadja, hogy a kapott játékobjektum beleesik-e a torony hatótávába
     * @param obj a vizsgált objektum
     * @return igaz, ha benne van a hatótávban
     */
    private boolean inRange(GameObject obj){
        if (obj == null){return false;}
        return getPos().distance(obj.getPos())<range;
    }

    /**
     * Megadja a toronyhoz legközelebb eső ellenséget
     * @return a toronyhoz legközelebb eső ellenség
     */
    private Enemy getClosestEnemy(){
        Enemy closest = null;
        for (Enemy enemy:getHandler().getEnemies()) {
            if (closest == null){
                closest = enemy;
            } else if (enemy.getPos().distance(getPos()) < closest.getPos().distance(getPos()) ){
                closest = enemy;
            }
        }
        return closest;
    }

    /**
     * Ha a drag állapotban van (a drag változó igaz), akkor nem csinál semmit.
     * Rálő a legközelebb lévő ellenségre ha hatótávon belül van és a timer engedi
     * @param delta ennyi idő telt el a függvény előző hívása óta
     */
    @Override
    public void tick(double delta) {
        if (drag){ return;}
        Enemy closest = getClosestEnemy();
        if (inRange(closest)){
            attackTimer.tick(()->{
                shoot(closest);
            });
        }
    }

    /**
     * Hozzáadja magát a barátok listájához
     * @param enemies az ellenségek listája
     * @param friends a barátok listája
     */
    @Override
    public void friendOrFoe(LinkedList<Enemy> enemies, LinkedList<Friend> friends){
        friends.add(this);
    }

    /**
     * A drag állapotból normál állapotba vált
     * @param e az egérrel kapcsolatos információkat tartalmazó MouseEvent objektum
     */
    public void onMousePressed(MouseEvent e){
        if (drag){
            drag = false;
        } else{
            Vector2D mousePos = new Vector2D(
                    e.getX(),
                    e.getY()
            );
            if (collideWith(mousePos)){
                inFocus = true;
            } else{
                inFocus = false;
            }
        }
    }

    /**
     * Ha drag állapotban van, akkor a torony az egér pozíciójával megegyező pozícióra teszi magát
     * @param e az egérrel kapcsolatos információkat tartalmazó MouseEvent objektum
     */
    public void onMouseMoved(MouseEvent e){
        if (drag){
            Vector2D mousePos = new Vector2D(
                    e.getX(),
                    e.getY()
            );
            setPos(mousePos);
        }
    }
    /**
     * megadja, hogy mennyibe kerül
     * @return az ára
     */
    public double getCost(){
        return 40;
    }

    /**
     * Ha fókuszban van, akkor megmutatja a játékosnak kirajzolássaal, hogy a toronynak mekkora a hatótáva
     * @param g a grafikai objektum amit a render függvény kapott híváskor
     */
    @Override
    public void onRender(Graphics g){
        if(inFocus){
            g.setColor(new Color(129, 1, 1, 34));
            g.fillOval(
                    (int)(getPos().x - range),
                    (int)(getPos().y - range),
                    range*2,range*2);

            g.setColor(new Color(232, 200, 21));
            g.drawOval(
                    (int)(getPos().x - getRad()),
                    (int)(getPos().y - getRad()),
                    (int)getRad()*2,(int)getRad()*2);



            g.setColor(new Color(129, 1, 1));
            g.drawOval(
                    (int)(getPos().x - range),
                    (int)(getPos().y - range),
                    range*2,range*2);
        }
    }
}
