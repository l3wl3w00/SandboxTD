import java.awt.*;
import java.util.LinkedList;

/**
 * Egy ellenség belső állapotát kezeli.
 */
public class EnemyStateHandler {
    private ChangeableStat maxSpeed;
    private HpHandler hpHandler;
    private LinkedList<Effect> activeEffects = new LinkedList<>();
    private EnemyStateHandler prevState;
    private double value;
    private double damage;
    private Color ownerColor;
    private Enemy owner;

    public EnemyStateHandler(Enemy owner, double hp, double speed, double value, double damage) {
        hpHandler = new HpHandler(hp);
        maxSpeed = new ChangeableStat(speed);
        this.value = value;
        this.damage = damage;
        this.owner = owner;
        this.ownerColor = new Color(owner.getColor().getRGB());
    }

    /**
     * megddja azt az ellenséget, akinek az állapotát kezeli
     * @return
     */
    public Enemy getOwner() {
        return owner;
    }

    /**
     * Megadja, hogy van e lassító effektus rajta
     * @return igaz, ha le van lassítva
     */
    public boolean isSlowed(){return maxSpeed.getDefault()>maxSpeed.calculate();}

    /**
     * új effektet ad hozzá az aktív effektjei közé
     * @param e az új effekt
     */
    public void addEffect(Effect e){
        activeEffects.add(e);
        e.onActivate(this);
    }

    /**
     * készít egy másolatot önmagáról és visszaadja
     * @return a másolat
     */
    public EnemyStateHandler clone(){
        EnemyStateHandler s =  new EnemyStateHandler(owner, 0, maxSpeed.getDefault(), value, damage);
        s.hpHandler = hpHandler.clone();
        s.activeEffects = (LinkedList<Effect>) activeEffects.clone();
        s.ownerColor = new Color(ownerColor.getRGB());
        return s;
    }

    /**
     * A tulajdonosa tick függvénye hívja meg minden alkalommal, tehát minden frame-ben meghívódik.
     * minden effektus kifejti a hatását rajta, illetve a törlendő hatásokat törli az aktív hatások közül
     * @param delta ennyi másodperc telt el a függvény előző hívása óta
     */
    public void tick(double delta){
        LinkedList<Effect> removables = new LinkedList<>();
        prevState = clone();
        for (Effect e : activeEffects) {
            e.effect(this);
            if (e.toRemove()) {
                removables.add(e);
            }
        }
        for (Effect e: removables){
            e.onInactivate(this);
            activeEffects.remove(e);
        }
    }

    /**
     * 2 tick függvény hívás között hívódik meg. A sebességét az előző frame-beli állapottá teszu egyenlővé
     */
    public void atEndOfTurn(){
        if (prevState == null) return;
        maxSpeed = prevState.maxSpeed.clone();
        owner.setColor(new Color(prevState.ownerColor.getRGB()));
    }

    /**
     * megadja, hogy törlendő-e a tulajdonosa
     * @return igaz, ha törlendő
     */
    public boolean toRemove(){
        boolean lowHp = hpHandler.underZero();
        return lowHp;
    }

    /**
     * beállítja a sebesség lassítási rátáját a kapott értékre
     * @param amount ennyiszerese lesz az új sebesség az eredetinek
     */
    public void setSlow(double amount){
        maxSpeed.setMultiplier(amount);
    }

    /**
     * beállítja a sebzést
     * @param value az új sebzés
     */
    public void takeDamage(double value){
        hpHandler.takeDamage(value);
    }

    /**
     * a handler segítségével kirajzolja az életerőcsíkot
     * @param g grafikai objektum ami segítségel történik a kirajzolás
     * @param pos a pozíció ahova rajzolni kell
     * @param length ilyen hosszú lesz
     * @param height ilyen magas lesz
     */
    public  void drawHpBar(Graphics g, Vector2D pos, double length, int height){
        hpHandler.drawHpBar(g,pos,length,height);
    }

    /**
     * megadja a sebességet
     * @return a sebesség értéke
     */
    public double getMaxSpeed() {
        return maxSpeed.calculate();
    }

    /**
     * megvéltoztatja a színét a kapott értékekkel
     * @param r a piros változtatás értéke
     * @param g a zöld változtatás értéke
     * @param b a kék változtatás értéke
     * @param a ennyivel lesz átlátszóbb
     * @return
     */
    public int[] changeColorBy(int r, int g, int b, int a) {
        return getOwner().changeColorBy(r,g,b,a);
    }

    /**
     * Megadja az értékét. Ennyit kap a játékos ha megöli az ellenséget
     * @return az ellenség értéke
     */
    public double getValue() {
        return value;
    }

    /**
     * Megadja a sebzést. Ennyivel sebzi meg a játékost ha beér a céljába
     * @return a sebzés értéke
     */
    public double getDamage() {
        return damage;
    }
}
