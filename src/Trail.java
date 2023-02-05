import java.awt.*;
import java.util.LinkedList;

/**
 * A GameObject objektumok húzhatnak maguk után elhalványuló csíkot. Ezt ennek az osztálynak a segítségével teszik.
 * Az osztály egy objektuma egy átlátszó folyamatosan elhalványuló kör. Ebből sok közel egymás mellet egy csíkot alkot
 */
public class Trail extends GameObject implements Friend{
    private ChangeableStat alpha;
    private double secondsAlive;
    public Trail(Vector2D v, Color c, double w, Handler handler,double seconds){
        super(v,handler,c,w,1);
        alpha = new ChangeableStat(getColor().getAlpha());
        secondsAlive = seconds;
    }

    /**
     * Átlátszóbbá teszi a kört
     * @param amount a mérték amennyivel átlátszóbb lesz a kör
     */
    private void reduceAlpha(double amount){
        alpha.add(-1*amount);
        if (alpha.calculate() <= 0){
            alpha.multiplyBy(0);
        }
        setColor(new Color(getColor().getRed(),getColor().getGreen(), getColor().getBlue(),(int)Math.round(alpha.calculate())));
    }

    /**
     * Minden frame-ben meghívódik. Elhalványítja kicsit az objektumot, és ha teljesen eltűnik, akkor kiveszi a játéktérről
     * @param delta ennyi idő telt el a függvény előző hívása óta
     */
    @Override
    public void tick(double delta) {
        reduceAlpha((alpha.getDefault()/secondsAlive) *delta);
        if (alpha.calculate() <= 0){
            removeGameObj(this);
        }
    }

    /**
     * Hozzáadja magát a barátok listájához
     * @param enemies az ellenségek listája
     * @param friends a barátok listája
     */
    @Override
    public void friendOrFoe(LinkedList<Enemy> enemies, LinkedList<Friend> friends) {
        friends.add(this);
    }
}
