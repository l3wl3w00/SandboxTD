/**
 * Olyan effektus, ami egy adott idő után lejár
 */
public abstract class TimedEffect implements Effect {
    private double activeSeconds;
    private long activationTime;
    public TimedEffect(double duration){
        activationTime = System.currentTimeMillis();
        activeSeconds = duration;
    }

    /**
     * Megaadja, hogy lejárt e már az idő ami az effektus elavulását jelenti
     * @return igaz, ha lejárt az idő
     */
    @Override
    public boolean toRemove() {
        return System.currentTimeMillis() - activationTime >= activeSeconds*1000;
    }

}
