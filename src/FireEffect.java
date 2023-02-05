/**
 * Olyan effektus, ami folyamatosan égeti a hordozóját, és megadott időközönként levon az életéből
 */
public class FireEffect extends TimedEffect{
    private Timer damageTimer;
    private double damage;
    public FireEffect(double duration, double damageSpeed, double damageAmount){
        super(duration);
        damageTimer = new Timer(damageSpeed,0);
        damage = damageAmount;
    }

    /**
     * ha a timer engedi, megsebzi az ellenséget
     * @param s egy ellenség állapota, amin aktív
     */
    @Override
    public void effect(EnemyStateHandler s) {
        damageTimer.tick( ()->{
            s.takeDamage(damage);
        });
        s.changeColorBy(255,0,0,0);
    }
}
