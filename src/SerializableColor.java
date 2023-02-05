import java.awt.*;
import java.io.Serializable;

/**
 * Ugyanaz mint az awt Color osztálya csak sorosítható
 */
public class SerializableColor extends Color implements Serializable {
    public SerializableColor(int r, int g, int b) {
        super(r, g, b);
    }
}
