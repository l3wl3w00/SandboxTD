import java.awt.*;
import java.util.LinkedList;

/** Egy torony által kilőtt lövedék */
public class Bullet extends MovingGameObject implements Friend {
    private double dmg = 10;
    private double maxSpeed;
    private Enemy target;
    private boolean trailOn = true;
    public Bullet(Vector2D pos, Handler h, Enemy _target, double maxSpeed, Color color, double rad){
        super(pos,h,color,rad,2,0.01);
        target = _target;
        this.maxSpeed = maxSpeed;
        newGoalPos(target.getPos(),maxSpeed);
    }

    /**
     * Be/kikapcsolja az önmaga után húzott csíkot
     * @param trailOn true a bekapcsolásra, false a kikapcsolásra
     */
    public void setTrailOn(boolean trailOn) {
        this.trailOn = trailOn;
    }

    /**
     * Akkor hívódik meg, amikor a lövedék eltalálta a kapott EnemyStateHandler tulajdonosát
     * @param targetState Az eltalált ellenség állapota, amit ez a függvény befolyásol
     */
    public void onHit(EnemyStateHandler targetState){
        if (targetState == null) return;
        targetState.takeDamage(dmg);
    }

    /**
     * Hozzáadja a barát listához magát
     * @param enemies az ellenségek listája
     * @param friends a barátok listája
     */
    @Override
    public void friendOrFoe(LinkedList<Enemy> enemies, LinkedList<Friend> friends){
        friends.add(this);
    }

    /**
     * Az lövedék belső működése. Folyamatosan követi a célpontot, és meghívja rá az onHit-et, ha eltalálta.
     * Elpusztítja önmagát ha eltalált valakit, vagy ha elért a korábban beállított célponthoz.
     * @param delta ennyi idő telt el a függvény előző hívása óta
     */
    @Override
    public void tick(double delta) {
        if (target != null){
            newGoalPos(target.getPos(),maxSpeed);
        }
        if (trailOn){
            trailEffect(0.1);
        }


        move(delta,maxSpeed,calculateRotation());
        if (collideWith(target)){
            onHit(target.getStateHandler());
            queueForDestroy();
        }
        if (atGoal()){
            onHit(null);
            queueForDestroy();
        }
    }

    /**
     * A mozgás során minden alkalommal meg van hívva, és a visszatérési értékben azt a szöget
     * adja meg, amivel az adott hívásban el kell forgatni a lépést leíró vektort
     * @return a kiszámolt érték
     */
    public double calculateRotation(){
        return 0;
    }

    /**
     * Megadja a sebzést
     * @return a sebzés értéke
     */
    public double getDmg() {
        return dmg;
    }
}
