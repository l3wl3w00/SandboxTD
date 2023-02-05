/**
 * Időzítésre használható osztály
 */
public class Timer {
    private double secondsBetweenTicks = 0;
    private double originalSecondsBetweenTicks;
    private long lastTickTime = 0;
    private double acceleration = 0;
    private double minimalTime = 0;
    public Timer( double secondsBetweenTicks_,double acc){
        secondsBetweenTicks = secondsBetweenTicks_;
        originalSecondsBetweenTicks = secondsBetweenTicks;
        acceleration = acc;
    }
    public Timer( double secondsBetweenTicks_,double acc, double m){
        secondsBetweenTicks = secondsBetweenTicks_;
        originalSecondsBetweenTicks = secondsBetweenTicks;
        acceleration = acc;
        minimalTime = m;
    }

    /**
     * Kap egy Tickable interfész implementációt, és ha a secondsBetweenTick tagváltozónál több idő telt el az
     * előző sikeres végrehajtás óta, akkor végrehajtja az implementáció tick függvényét
     * @param t a Tickable interfész implementáció
     * @return igaz, ha az interfész tick függvénye végrehajtódott
     */
    public boolean tick(Tickable t){
        if (System.currentTimeMillis() - lastTickTime > secondsBetweenTicks*1000)
        {
            t.tick();
            if(secondsBetweenTicks > minimalTime){
                secondsBetweenTicks =Math.max(minimalTime,secondsBetweenTicks*(1-acceleration));
            }

            lastTickTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    /**
     * alaphelyzetbe állítja a visszaszámlálót
     */
    public void reset() {
        lastTickTime = 0;
        secondsBetweenTicks = originalSecondsBetweenTicks;
    }
}

