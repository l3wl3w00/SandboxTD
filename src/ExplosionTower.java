/**
 * robbanó lövedékeket lövő torony
 */
public class ExplosionTower extends Tower{
    public static double cost = 70;

    public ExplosionTower(Vector2D vector, Handler handler, int _range){
        super(vector,handler,_range);
        initAttackSpeed(0.3);
    }

    /**
     * letesz egy új robbanó lövedéket a játéktérre a saját pozíciójára
     * @param e az ellenség aki a lövedék célpontja lesz
     */
    @Override
    public void shoot(Enemy e){
        addGameObj(new ExplodingBullet(getPos().clone(),getHandler(),e));
    }


    /**
     * Megadja a torony árát
     * @return a torony árának értéke
     */
    public double getCost(){
        return cost;
    }
}
