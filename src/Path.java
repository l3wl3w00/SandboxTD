import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A pálya/útvonal amit az ellenségek követnek
 */
public class Path implements Serializable, Iterable<Vector2D>{
    @Serial
    private static final long serialVersionUID = 5552913818480546830L;
    private LinkedList<Vector2D> points = new LinkedList<>();
    private SerializableColor color;
    private int width;
    private String name = "";
    // csak a PathMaker osztály módosíthatja

    public Path(){
        color = new SerializableColor(200,200,150);
        width = 30;
    }

    /**
     * Kirajzolja a pálya pontjait és az őket összekötő vonalakat
     * @param g grafika objektum
     * @param ratio a kirajzolás mérete, pl 0.5 érték esetén minden feleakkora lesz
     */
    public void render(Graphics g, double ratio) {
        double currWidth = width*ratio;
        g.setColor(color);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        Vector2D prev = null;
        for (Vector2D point:points){
            Vector2D currPoint = point.getMultiplied(ratio);
            Vector2D drawPos = new Vector2D((currPoint.x-currWidth),(currPoint.y-currWidth));

            g2.fillOval(
                    (int)Math.round(drawPos.x),
                    (int)Math.round(drawPos.y),
                    (int)Math.round(currWidth*2),
                    (int)Math.round(currWidth*2));

            if (prev != null) {
                Vector2D startPoint = new Vector2D(prev.x,(prev.y-currWidth));
                Rectangle rect = new Rectangle(
                        (int) Math.round(startPoint.x),
                        (int) Math.round(startPoint.y),
                        (int) Math.round(prev.distance(currPoint)),
                        (int) Math.round(currWidth*2)
                );
                double angle = (currPoint.getSubtracted(prev)).getAngle();
                g2.rotate(angle,startPoint.x,startPoint.y+currWidth);

                g2.draw(rect);
                g2.fill(rect);
                g2.rotate(-angle,startPoint.x,startPoint.y+currWidth);
            }
            prev = currPoint;
        }
    }

    /**
     * Kirajzolja a pálya pontjait és az őket összekötő vonalakat
     * @param g grafika objektum
     */
    public void render(Graphics g) {
        render(g,1);
    }

    /**
     * Megad egy iterátort a pontok listájára amikből a pálya áll
     * @return egy Vector2D iterátor
     */
    @Override
    public Iterator<Vector2D> iterator() {
        return points.iterator();
    }

    /**
     * megadja a pálya nevét
     * @return a pálya neve
     */
    public String getName() {
        return name;
    }

    /**
     * beállítja a pálya nevét
     * @param name az új név
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * megadja a pontok listáját
     * @return a pontok listája
     */
    public LinkedList<Vector2D> getPoints() {
        return points;
    }

    /**
     * Megadja, hogy milyen széles a pálya (kirajzolásnál fontos)
     * @return a pálya szélessége
     */
    public double getWidth() {
        return width;
    }

    /**
     * sztringgé konvertálja, azaz a nevét adja vissza
     * @return a pálya neve
     */
    @Override
    public String toString() {
        return name;
    }


}
