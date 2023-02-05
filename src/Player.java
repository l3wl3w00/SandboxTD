import java.awt.*;

/**
 * A játékost kezelő osztály
 */
public class Player {
    private HpHandler hp;
    private ChangeableStat money;

    public Player(double _hp, double money) {
        hp = new HpHandler(_hp);
        this.money = new ChangeableStat(money);
    }

    /**
     * megadja, hogy a játékos élete 0 vagy alatta van e
     * @return igaz, ha a játékos élete 0 vagy alatta van
     */
    public boolean hpUnderZero(){
        return hp.underZero();
    }

    /**
     * kirajzolja a játékos életének megfelelő csíkot a képernyő bal felső felére
     * @param g a grafika objektum, amivel a kirajzolás történik
     */
    public void drawHp(Graphics g){
        hp.drawHpBar(g, new Vector2D(10,10),300,30,5);
    }

    /**
     * a játékos életét csökkenti a kapott értékkel
     * @param dmg az érték amivel csökken a játékos életereje
     */
    public void takeDamage(double dmg) {
        hp.takeDamage(dmg);
    }

    /** Ha van elég pénze a játékosnak (tehát csökkenés után nem esik 0 alá) akkor csökken a kapott mennyiséggel.
     * @param amount amennyivel csökkenteni kell
     * @return igaz, ha volt elég pénz */
    public boolean loseMoney(double amount){
        if (money.calculate() - amount < 0){
            return false;
        }
        money.add(-1*amount);
        return true;
    }

    /**
     * alaphelyzetbe állítja a pénzt és az életet
     */
    public void reset() {
        money.reset();
        hp.reset();
    }

    /**
     * megadja, hogy mennyi pénze van a játékosnak
     * @return
     */
    public double getMoney() {
        return money.calculate();
    }
}
