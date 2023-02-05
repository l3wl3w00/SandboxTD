/**
 * Tűz effektusos lövedékeket lövő torony
 */
public class FireTower extends Tower{
    public static double cost = 30;

    public FireTower(Vector2D vector, Handler handler, int _range){
        super(vector,handler,_range);
        initAttackSpeed(0.5);
    }

    /**
     * letesz egy új tűzes lövedéket a játéktérre a saját pozíciójára
     * @param e az ellenség aki a lövedék célpontja lesz
     */
    @Override
    public void shoot(Enemy e){
        addGameObj(new FireBullet(getPos().clone(),getHandler(),e));
    }

    /**
     * Megadja a torony árát
     * @return a torony árának értéke
     */
    public double getCost(){
        return cost;
    }
}
