import java.awt.*;

public class ExplodingBullet extends Bullet{

    public ExplodingBullet(Vector2D pos, Handler h, Enemy _target) {
        super(pos, h, _target,300, Color.red,5);
        setTrailOn(true);
    }

    /**
     * Rátesz a játéktérre egy robbanás objektumot a saját pozícióján
     * @param targetState Az eltalált ellenség állapota, amit ez a függvény befolyásol
     */
    @Override
    public void onHit(EnemyStateHandler targetState){
        addGameObj(new Explosion(getPos().clone(),getHandler(),150,80));
    }

    /**
     * kiszámítja az elfordulás szögét, és ennyivel elfordult irányba lép minden frame-ben
     * @return az elfordulási szög
     */
    @Override
    public double calculateRotation(){
        double res = Math.cos(getSecondsSinceSpawn()*6)*1000;
        if (res<0){
            return Math.toRadians(-1*Math.sqrt(Math.cos((getSecondsSinceSpawn()*6) - Math.PI)*1000));
        } else{
            return Math.toRadians(Math.sqrt(res));
        }
    }
}
