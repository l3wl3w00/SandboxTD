import java.io.*;
import java.util.LinkedList;

/**
 * Ez az osztály kezeli az összes memóriából beolvasott pályát
 */
public class PathHandler {

    private LinkedList<Path> paths = new LinkedList<>();
    private Path activePath = null;
    public PathHandler(){
    }

    /**
     * Megadja Az összes pálya listáját
     * @return Az összes pálya listája
     */
    public LinkedList<Path> getPaths() {
        return paths;
    }

    /**
     * töröl minden pályát
     */
    public void clear(){
        paths.clear();
    }

    /**
     * beolvassa egy File tömbbe az összes pályát tartalmazó txt file-okat
     * @return
     */
    private File[] getPathFiles(){
        File dir = new File("paths");
        if (!dir.isDirectory()) {
            // TODO itt majd exceptiont kell dobni!
            System.out.println("nem directory!");
        }
        File[] files = dir.listFiles();
        return files;
    }

    /**
     * minden pályát betölt a memóriába ami a "paths" nevű mappában van
     */
    public void loadPaths() {
        File[] files = getPathFiles();
        if (files != null) {
            for (File file : files) {
                Path newPath = null;
                try {
                    FileInputStream fin = new FileInputStream("paths\\"+file.getName());
                    ObjectInputStream oin = new ObjectInputStream(fin);
                    newPath = (Path) oin.readObject();
                    oin.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (newPath.getName().equals("default") && activePath == null){
                    activePath = newPath;
                }
                paths.add(newPath);
            }

        }

    }

    /**
     * Beállítja az aktív pályát
     * @param activePath az új aktív pálya
     */
    public void setActivePath(Path activePath) {
        this.activePath = activePath;
    }

    /**
     * megadja az aktív pályát
     * @return az aktív pálya
     */
    public Path getActivePath() {
        return activePath;
    }

    /**
     * törli az aktív pályát a pályákat tartalmazó listából
     * @return a törölt pálya
     */
    public Path deleteActivePath() {
        File[] files = getPathFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().equals(activePath.getName()+".txt")){
                    file.delete();
                }
            }
        }
        paths.remove(activePath);
        Path temp = activePath;
        activePath = null;
        return temp;
    }
}
