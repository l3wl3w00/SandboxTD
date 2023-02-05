import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.Iterator;

/**
 * Az útvonalakat/pályákat elkészítő osztály, ez az osztály felelős pontok hozzáadásáért/törléséért az útvonalba
 */
public class PathMaker implements MouseListener {
    private Path path;
    public PathMaker(){
        path = new Path();
    }

    public PathMaker(Path p){
        path = p;
    }
    /**
     * új pontot vesz fel a pálya pontjai közé
     * @param v az új pont
     */
    public void addPoint(Vector2D v){
        path.getPoints().add(v);
    }

    /**
     * új pontot vesz fel a pálya pontjai közé
     * @param x az új pont x koordinátája
     * @param y az új pont y koordinátája
     */
    public void addPoint(double x,double y){ addPoint(new Vector2D(x,y)); }

    /**
     * töröl minden pontot, ami a kapott pont megadott sugarában van. Ez a sugár megegyezik a pálya szélességével
     * @param v a pont, ami környezetében törölni kell pontokat
     */
    public void removePoint(Vector2D v){
        Iterator<Vector2D> i = path.iterator();
        while (i.hasNext()) {
            Vector2D vector = i.next();
            if (v.distance(vector) <= path.getWidth()){
                path.getPoints().remove(vector);
                break;
            }
        }
        path.getPoints().remove(v);
    }

    /**
     * töröl minden pontot, ami a kapott pont megadott sugarában van. Ez a sugár megegyezik a pálya szélességével
     * @param x a pont x koordinátája, ami környezetében törölni kell pontokat
     * @param y a pont y koordinátájaa, ami környezetében törölni kell pontokat
     */
    public void removePoint(double x, double y){ removePoint(new Vector2D(x,y)); }

    /**
     * kirajzolja a pályát
     * @param g a grafika objektum, amivel a kirajzolás történik
     */
    public void render(Graphics g){
        path.render(g);
    }

    /**
     * Beállítja a pálya nevét
     * @param name az új név
     */
    public void setPathName(String name) { path.setName(name);}

    /**
     * kiírja a megfelelő (az útvonal nevével megegyező) file-ba a pályát szerializálva
     */
    public void save(){
        try {

            FileOutputStream f = new FileOutputStream("paths/"+path.getName()+".txt");
            ObjectOutputStream out = new ObjectOutputStream(f);

            out.writeObject(path);
            out.flush();
            out.close();
        }
        catch(IOException ex) {
            System.out.println("hiba");
            System.out.println(ex.getCause());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    /**
     * akkor hívódik meg, amikor az egér egy gombja lenyomódik. Ha jobbklikk volt, akkor törlődik
     * az összes pont, aminek a környezetébe kattintott a felhasználó, ha pedig balklikk,
     * akkor a kattintás helyére egy új pont jön létre és része lesz a pályának
     * @param e az egérről információt tartalmazó objektum
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isRightMouseButton(e)){
            removePoint(e.getX(),e.getY());
        }else if (SwingUtilities.isLeftMouseButton(e)){
            addPoint(e.getX(),e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Megadja a pálya méretét
     * @return a pálya mérete
     */
    public int size() {
        return path.getPoints().size();
    }
}
