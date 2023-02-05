import java.awt.*;

/**
 * Életerőt kezelő osztály
 */
public class HpHandler {
    private double hp;
    private double maxHp;

    public HpHandler(double _hp){
        hp = _hp;
        maxHp = _hp;
    }

    /**
     * csökkenti az életerőt a kapott értékkel
     * @param damage ennyivel csökkenti az életerőt
     */
    public void takeDamage(double damage){
        hp -= damage;
    }

    /**
     * Megadja a jelenlegi és a maximális életerőszint arányát
     * @return az arány
     */
    public double getHpRatio(){
        return hp/maxHp;
    }

    /**
     * Megadja, hogy a jelenlegi életerőszint 0, vagy alatta van e
     * @return
     */
    public boolean underZero(){
        return hp <=0;
    }

    /**
     * Megrajzolja az életerőcsíkot a megadott helyre a megadott méretekkel
     * @param g a grafika objektum amivel a kirajzolás történik
     * @param pos a kirajzolás pozíciója
     * @param length az életerőcsík hossza
     * @param height az életerőcsík magassága
     */
    public void drawHpBar(Graphics g, Vector2D pos, double length, int height){
        drawHpBar(g,pos,length,height,1);
    }

    /**
     * Megrajzolja az életerőcsíkot a megadott helyre a megadott méretekkel
     * @param g a grafika objektum amivel a kirajzolás történik
     * @param pos a kirajzolás pozíciója
     * @param length az életerőcsík hossza
     * @param height az életerőcsík magassága
     * @param thickness A téglalap keret vastagsága
     */
    public void drawHpBar(Graphics g, Vector2D pos, double length, int height, int thickness){
        double hpRatio = getHpRatio();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setStroke(new BasicStroke());

        g2.setColor(new Color(30,200,30));
        g2.fillRect((int)pos.x,(int)pos.y, (int) (length*hpRatio),height);

        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(new Color(30,100,30));
        g2.drawRect((int)pos.x,(int)pos.y, (int) (length),height);
        g2.dispose();
    }

    /**
     * készít egy másolatot saját magáról
     * @return a másolat
     */
    public HpHandler clone(){
        HpHandler h = new HpHandler(hp);
        h.maxHp = maxHp;
        h.hp = hp;
        return h;
    }

    /**
     * alaphelyzetbe állítja, azaz a jelenlegi életerőt a maximumra állítja
     */
    public void reset() {
        hp = maxHp;
    }
}
