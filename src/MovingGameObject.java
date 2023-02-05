import java.awt.*;

/**
 * Minden mozgó jéktéren lévő játékobjektum őse
 */
public abstract class MovingGameObject extends GameObject {
    private Vector2D stepVector;
    private Vector2D goalPosition;



    private double secondsSinceSpawn = 0;
    public MovingGameObject(Vector2D pos, Handler handler, Color color, double rad, int layer, double trailFrequency) {
        super(pos,handler,color,rad,layer,trailFrequency);
    }

    /**
     * Megadja a létrejötte óta eltelt másodpercek számát
     * @returna létrejötte óta eltelt másodpercek számát
     */
    public double getSecondsSinceSpawn() {
        return secondsSinceSpawn;
    }

    /**
     * Beállítja a kapott pontot az új célpozíciónak
     * @param p az új célpozíció
     * @param maxSpeed a sebesség, amivel megteszi majd az utat
     */
    public void newGoalPos(Vector2D p, double maxSpeed){
        goalPosition = p;
        calculateStep(maxSpeed);
    }

    /**
     * kiszámolja a következő lépés nagyságát a célpozíció és a kapott sebesség alapján
     * @param maxSpeed a sebesség értéke
     */
    private void calculateStep(double maxSpeed){
        // a jelen pozíciótól a cél felé mutató vektor
        Vector2D goalVector = goalPosition.getSubtracted(getPos());
        stepVector = goalVector.getNormalized().getMultiplied(maxSpeed);
    }

    /**
     * Megadja, hogy elérte-e már a célpozíciót
     * @return igaz, ha már elérte
     */
    public boolean atGoal(){return getPos().round().equals(goalPosition.round());}

    /**
     * kiszámolja a lépés nagyságát és irányát és elmozgatja magát a kapott sebesség alapján
     * @param delta a függvény előző hívása óta eltelt idő
     * @param speed a mozgás sebessége
     */
    public void move(double delta, double speed){
        move(delta,speed,0);
    }
    /**
     * kiszámolja a lépés nagyságát és irányát és elmozgatja magát a kapott sebesség alapján
     * @param delta a függvény előző hívása óta eltelt idő
     * @param speed a mozgás sebessége
     * @param rotationRadians ekkora szöggel fordul el a lépés iránya az eredetihez képest
     */
    public void move(double delta, double speed,double rotationRadians){
        secondsSinceSpawn += delta;
        if (atGoal()) {return;}
        calculateStep(speed);
        Vector2D finalStep = stepVector.clone();
        finalStep.multiply(delta);
        finalStep.rotateBy(rotationRadians);
        getPos().add(finalStep);

    }

}
