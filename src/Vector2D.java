import java.io.Serial;
import java.io.Serializable;

/**
 * Egy 2 dimenziós vektor. Különböző vektorműveleteklet tud, amik kellenek a mozgatásnál általában
 */
public class Vector2D implements Serializable {

    @Serial
    private static final long serialVersionUID = 1736245586644222233L;

    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Megadja a vektor hosszát
     * @return a vektor hossza
     */
    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Megadja a kapott vektorral jelölt ponttól mért távolságot
     * @param v a vektor, amitől mértni kell a távolságot
     * @return a távolság
     */
    public double distance(Vector2D v) {
        double vx = v.x - this.x;
        double vy = v.y - this.y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    /**
     * Megadja, hogy mekkora szöget zár be az x tengellyel
     * @return
     */
    public double getAngle() {
        return Math.atan2(y, x);
    }


    /**
     * Megadja a vektor normalizált (1 hosszúságü) változatát
     * @return a vektor normalizált változata
     */
    public Vector2D getNormalized() {
        double magnitude = getLength();
        return new Vector2D(x / magnitude, y / magnitude);
    }

    /**
     * hozzáadja a vektorhoz a kapott vektort
     * @param v a hozzáadandó vektor
     */
    public void add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
    }

    /**
     * Visszaadja a vektor és a kapott vektor különségét. Semmilyen belső érték nem változik
     * @param v a kivonandó vektor
     * @return a különbség
     */
    public Vector2D getSubtracted(Vector2D v) {
        return new Vector2D(this.x - v.x, this.y - v.y);
    }

    /**
     * Megszorozza önmagát a kapott skalárral
     * @param scalar a skalár amivel szorozni kell
     */
    public void multiply(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    /**
     * Megadja önmaga és a kapott skalár szorzatát
     * @param scalar a skalár, amivel szorozni kell
     * @return a szorzat eredménye
     */
    public Vector2D getMultiplied(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    /**
     * Elforgatja a kapott szöggel a vektort
     * @param angle a szög, amivel el kell forgatni
     */
    public void rotateBy(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double rx = x * cos - y * sin;
        y = x * sin + y * cos;
        x = rx;
    }

    /**
     * Egy olyan vektort ad vissza, amiben az x és y értékek a jelenlegi vektor egészre kerekített értékei
     * @return a kerekített vektor
     */
    public Vector2D round(){
        return new Vector2D(Math.round(x),Math.round(y));
    }

    /**
     * készít egy másolatot önmagáról
     * @return a másolat
     */
    @Override
    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    /**
     * Megadja, hogy a kapott paraméterrel egyenlő-e
     * @param obj a paraméter amire vizsgálni kell az egyenlőséget
     * @return igaz, ha egyenlő az x és y koordináta
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Vector2D) {
            Vector2D v = (Vector2D) obj;
            return (x == v.x) && (y == v.y);
        }
        return false;
    }
}
