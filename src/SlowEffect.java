/**
 * Lassítás effekt. Amíg rajta van valakin addig lelassítva van a megadott százalékkaal
 */
public class SlowEffect extends TimedEffect{

    private double ratio;
    public SlowEffect(double ratio,double time){
        super(time);
        this.ratio = ratio;
    }

    /**
     * a hordozóját lassítja
     * @param s egy ellenség állapota, amin aktív
     */
    @Override
    public void effect(EnemyStateHandler s) {
        s.setSlow(ratio);
    }

}
