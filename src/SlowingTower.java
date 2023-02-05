/**
 * olyan torony ami lassító hatású lövedékeket lő
 */
public class SlowingTower extends Tower{
    public static double cost = 50;

    public SlowingTower(Vector2D vector, Handler handler, int _range){
        super(vector,handler,_range);
        initAttackSpeed(1.0);
    }

    /**
     * a játéktérre tesz egy új lassító lövedéket a saját pozíciójára
     * @param enemy a célpont, akit követ a lövedék
     */
    @Override
    public void shoot(Enemy enemy){
        addGameObj(new SlowingBullet(getPos().clone(),getHandler(),enemy));
    }


    /**
     * megadja a torony árát
     * @return a torony ára
     */
    public double getCost(){
        return cost;
    }
}
