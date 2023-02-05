import java.awt.*;

/**
 * Olyan lövedék, ami lelassítja az eltalált ellenséget
 */
public class SlowingBullet extends Bullet{

    public SlowingBullet(Vector2D pos, Handler h, Enemy _target) {
        super(pos, h, _target,300, Color.BLUE,10);
        setTrailOn(false);
    }

    /**
     * megsebzi az eltalált ellenséget, és rátesz egy lassító effektust
     * @param targetState Az eltalált ellenség állapota, amit ez a függvény befolyásol
     */
    @Override
    public void onHit(EnemyStateHandler targetState){
        if (targetState == null) return;
        targetState.takeDamage(getDmg());
        targetState.addEffect(new SlowEffect(0.5,2.0));
    }
}
