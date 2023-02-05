import java.awt.*;

/**
 * Lövedék, ami az eltalált ellenséget megégeti
 */
public class FireBullet extends Bullet{

    public FireBullet(Vector2D pos, Handler h, Enemy _target) {
        super(pos, h, _target,1000, Color.RED,5);
    }

    /**
     * Az eltalált ellenségre egy tűz effektust tesz
     * @param s az eltalált ellenség állapota
     */
    @Override
    public void onHit(EnemyStateHandler s){
        s.addEffect(new FireEffect(5.0,0.5,2));
    }
}
