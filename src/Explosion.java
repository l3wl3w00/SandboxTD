import java.awt.*;
import java.util.LinkedList;

/**
 * Egy robbanás, ami mindenk ellenséget megsebez aki a hatótávon belül van
 */
public class Explosion extends GameObject implements Friend {
    private double damage;
    private double originalRad;
    private double alpha;
    private double timeSinceSpawned = 0;
    public Explosion(Vector2D pos, Handler h, double radius, double damage){
        super(pos,h,new Color(246, 61, 19, 169),radius,0);
        originalRad = radius;
        this.damage = damage;
        alpha = getColor().getAlpha();
        onCreation();
    }

    /**
     * A konstruktor hívja meg. Megnézi minden ellenségre, hogy hatótávon belül van, e és ha igen,
     * akkor leveszi az életét attól függően hogy milyen messze van
     */
    private void onCreation(){
        for (Enemy e : getHandler().getEnemies()) {
            if (collideWith(e)) {
                e.getStateHandler().takeDamage(
                        calculateDamage(e.getPos())

                );
            }
        }
    }

    /**
     * Kiszámolja hogy mennyi a sebzés az alapján, hogy milyen meszse van a sebzendő ellenség a robbanás középpontjától
     * @param point az ellenség pozíciója
     * @return a sebzés értéke
     */
    private double calculateDamage(Vector2D point){
        double distance = getPos().distance(point);
        double res = damage - damage*distance/originalRad;
        if (res<0){
            res = 0;
        }
        return res;
    }

    /**
     * egy kis ideig négyzetesen növeli, majd csökkenti a robbanás sugarát, majd a végén eltűnik
     * @param delta ennyi idő telt el a függvény előző hívása óta
     */
    @Override
    public void tick(double delta) {
        setRad(calculateRad(timeSinceSpawned,0.15));
        if (getRad()<originalRad*3/4){
            reduceAlpha(delta*2000);
        }
        timeSinceSpawned += delta;
    }

    /**
     * átlátszóbbá teszi a robbanás színét
     * @param value a mennyiség amennyivel az átlátszóságot megnöveli
     */
    private void reduceAlpha(double value){
        alpha -= value;
        if (alpha<0){
            alpha = 0;
        }
        setColor( new Color(getColor().getRed(),getColor().getGreen(),getColor().getBlue(),
                (int)Math.round(alpha)));
    }

    /**
     * kiszámolja a paraméterek alapján az aktuális méretét a robbanásnak
     * @param time ennyi másodperc telt el a robbanás létrejötte óta
     * @param secondsWhenReachingMax ennyi másodperc után fogja elérni a maximum sugarat, azaz ezután csökken a robbanás mérete
     * @return az aktuális sugara a robbanásnak
     */
    private double calculateRad(double time, double secondsWhenReachingMax){
        if(time < secondsWhenReachingMax){
            return -1000*Math.pow(time-secondsWhenReachingMax,2) + originalRad;
        }
        return -2000*Math.pow(time-secondsWhenReachingMax,2) + originalRad;
    }

    /**
     * hozzáadja magát a barátokhoz
     * @param enemies az ellenségek listája
     * @param friends a barátok listája
     */
    @Override
    public void friendOrFoe(LinkedList<Enemy> enemies, LinkedList<Friend> friends) {
        friends.add(this);
    }
}
