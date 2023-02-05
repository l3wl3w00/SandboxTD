/**
 * Egy ellenségen aktív hatás
 */
public interface Effect {
    /**
     * akkor hívódik meg amikor aktívvá válik az effekt
     * @param s annak az ellenségnek a belső állapota akire aktiválva lett a hatás
     */
    public default void onActivate(EnemyStateHandler s){}
    /**
     * akkor hívódik meg amikor inaktívvá válik az effekt
     * @param s annak az ellenségnek a belső állapota akin eddig aktív volt
     */
    public default void onInactivate(EnemyStateHandler s){}

    /**
     * minden frame-ben meghívódik, amikor a hatás aktív egy objektumon
     * @param s egy ellenség állapota, amin aktív
     */
    public void effect(EnemyStateHandler s);

    /**
     * megadja, hogy törlésre kerüljön e a hatás, azaz inaktívvá vált
     * @return igaz, ha törlendő
     */
    public boolean toRemove();
}
