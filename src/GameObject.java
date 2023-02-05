import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/** Minden olyan objektum ebből örököl, amiből tetszőleges számú lehet a játéktéren*/
public abstract class GameObject {
    private Vector2D pos;
    private Handler handler;
    private double rad = 25;
    private int layer = 0;
    private Color originalColor;
    private Color color;
    private Timer trailTimer;
    public GameObject(Vector2D _pos,Handler _handler, Color col, double rad, int layer, double trailFrequency){
        pos = _pos;
        handler = _handler;
        color = col;
        originalColor = color;
        this.rad = rad;
        this.layer = layer;
        trailTimer = new Timer(trailFrequency,0);
    }
    public GameObject(Vector2D _pos,Handler _handler, Color col, double rad, int layer){
        pos = _pos;
        handler = _handler;
        color = col;
        originalColor = color;
        this.rad = rad;
        this.layer = layer;
        trailTimer = new Timer(0.01,0);
    }

    /**
     * megadja a handler-t
     * @return a handlerre mutató referencia
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     * megadja az objektum sugarát
     * @return az objektum sugara
     */
    public double getRad() {
        return rad;
    }

    /**
     * megadja az objektum színét
     * @return az objektum színe
     */
    public final Color getColor() {
        return color;
    }

    /**
     *  beállítja az objektum szinét a kapott értékre
     * @param color az új szín
     */
    public final void setColor(Color color) {
        this.color = color;
    }

    /**
     * beállítja az objektum sugarát
     * @param rad az új érték
     */
    public void setRad(double rad) {
        this.rad = rad;
    }

    /**
     * hozzáad egy új objektumot a játéktérben a handler változó segítségével
     * @param g a hozzáadandó objektum
     */
    public void addGameObj(GameObject g){
        handler.queueForAdd(g);
    }

    /**
     * kivesz egy játéktéren lévő objektumot a pályáról a handler változó segítségével
     * @param g az eltávolítandó objektum
     */
    public void removeGameObj(GameObject g){
        handler.queueForDestroy(g);
    }

    /**
     * letesz egy Trail objektumot saját maga "alá", ha a timer engedi. Ezzel lényegében egy csíkot fog maga után húzni
     * @param fadeTime ennyi idő múlva tűnik el a Trail objektum
     */
    public void trailEffect(double fadeTime){

        trailTimer.tick(()->{
            addGameObj(new Trail(
                    getPos().clone(),
                    new Color( Math.min(color.getRed()+100,255),  Math.min(color.getGreen()+100,255),  Math.min(color.getBlue()+100,255)),
                    rad,getHandler(),fadeTime
            ));
        });
    }

    /**
     * megadja, hogy a kapott objektummal van e egymást fedő része
     * @param o az objektum, amivel vizsgáljuk az ütközést
     * @return igaz, ha az adott objektum ütközik a kapott objektummal
     */
    public boolean collideWith(GameObject o){
        return o.pos.distance(pos) <= rad+o.rad;
    }

    /**
     * megadja, hogy a kapott pont belül esik e az játékobjektumon
     * @param point a pont, amit vizsgálunk
     * @return igaz, ha az adott objektum ütközik a kapott ponttal
     */
    public boolean collideWith(Vector2D point){
        return point.distance(pos) <= rad;
    }

    /**
     * leveszi a játéktérről sajátmagát
     */
    public void queueForDestroy(){
        handler.queueForDestroy(this);
    }

    /**
     * Ebben lesz leírva egy ős belső működése. Ez a függvény minden frame-ben meghívódik
     * @param delta ennyi idő telt el a függvény előző hívása óta
     */
    public abstract void tick(double delta);

    /**
     * kirajzolja az objektumot a képernyőre
     * @param g a grafikai objektum ami segítségével kirajzolja
     */
    public void render(Graphics g) {
        g.setColor(color);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Vector2D drawPos = new Vector2D(pos.x-rad,pos.y-rad);
        g2.fillOval((int)drawPos.x,(int)drawPos.y,(int)Math.round(rad*2),(int)Math.round(rad*2));
        onRender(g);
    }

    /**
     * a render függvény végén hívódik meg, az ősben üres, de a leszármazottak tetszőlegesen override-olhatják
     * @param g a grafikai objektum amit a render függvény kapott híváskor
     */
    public void onRender(Graphics g){

    }

    /**
     * Kettő kapott láncolt listából eldönti magáról, hogy ellenség-e vagy nem, és az annak megfelelő listához adja magát
     * @param enemies az ellenségek listája
     * @param friends a barátok listája
     */
    public abstract void friendOrFoe(LinkedList<Enemy> enemies, LinkedList<Friend> friends);

    /**
     * megadja a relatív, panelhez mért pozícióját
     * @return a pozíció
     */
    public Vector2D getPos() {
        return pos;
    }

    /**
     * beállítja a relatív, panelhez mért pozícióját
     * @param pos az új pozíció
     */
    public void setPos(Vector2D pos) {
        this.pos = pos;
    }

    /**
     * megváltoztatja a saját színét a megadott paraméterek szerint. Ha egy paraméter a 0-255 tartoményban nincs benne, akkor azt 255-re állítja ha túl nagy, illetve 0-ra ha túl kicsi
     * @param r ennyit ad a szín piros komponenséhez
     * @param g ennyit ad a szín zöld komponenséhez
     * @param b ennyit ad a szín kék komponenséhez
     * @param a ennyivel kevésbé lesz halvány a szín
     * @return visszaad egy olyan szín 4-est, ami megadja, hogy az objektum eredeti címéhez képest mennyivel tér el a függvény futása utáni jelenlegi szín
     */
    public int[] changeColorBy(int r, int g, int b, int a){
        if(color.getRed()+r<0){r = color.getRed();}
        if(color.getGreen()+g<0){g = color.getGreen();}
        if(color.getBlue()+b<0){b = color.getBlue();}
        if(color.getAlpha()+a<0){a = color.getAlpha();}
        color = new Color(
                Math.min(255,color.getRed()+r),
                Math.min(255,color.getGreen()+g),
                Math.min(255,color.getBlue()+b),
                Math.min(255,color.getAlpha()+a)
        );
        int[] diff = {
                color.getRed() - originalColor.getRed(),
                color.getBlue() - originalColor.getBlue(),
                color.getGreen() - originalColor.getGreen(),
                color.getAlpha() - originalColor.getAlpha()
        };
        return diff;
    }

    /**
     * akkor hívódik meg, amikor az egeret megmozgatták
     * @param e az egérrel kapcsolatos információkat tartalmazó MouseEvent objektum
     */
    public void onMouseMoved(MouseEvent e){}

    /**
     * akkor hívódik meg, amikor az egeret lenyomták
     * @param e az egérrel kapcsolatos információkat tartalmazó MouseEvent objektum
     */
    public void onMousePressed(MouseEvent e) {}

    /**
     * megadja hogy melyik rajzolási rétegbe tartozik
     * @return a réteg
     */
    public int getLayer() {
        return layer;
    }

}
