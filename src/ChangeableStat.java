/** Egy változtatható érték, ami megőrzi az alapértékét */
public class ChangeableStat {
    private Double baseValue;
    private Double bonus = 0.0;
    private Double multiplier = 1.0;

    public ChangeableStat(double amount){
        baseValue = amount;
    }

    /**
     * beállítja a szorzót
     * @param multiplier az új szorzó értéke
     */
    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * megszorozza a multiplier változót a kapott értékkel
     * @param amount ennyivel kell szorozni
     */
    public void multiplyBy(double amount){
        multiplier *= amount;
    }

    /**
     * hozzáadja a kapott értéket önmagához
     * @param amount ezt kell hozzáadni
     */
    public void add(double amount){
        bonus += amount;
    }

    /**
     * megadja az alap értéket
     * @return
     */
    public double getDefault(){return baseValue;}

    /**
     * megadott sorrendben kiszámolja az értéket.
     * @return a kiszámolt érték
     */
    public double calculate(){
        return (baseValue+bonus)* multiplier;
    }

    /**
     * visszaad egy másolatot önmagáról
     * @return a másolat
     */
    public ChangeableStat clone(){
        ChangeableStat stat = new ChangeableStat(baseValue);
        stat.multiplyBy(multiplier);
        stat.add(bonus);
        return stat;
    }

    /**
     * alaphelyzetbe állítja (tehát a calculate függvény eredménye ezután megegyezik az alapértékkel)
     */
    public void reset() {
        bonus = 0.0;
        multiplier = 1.0;
    }
}
