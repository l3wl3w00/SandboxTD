import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PathHandlerTest {
    private  PathHandler pathHandler = new PathHandler();
    @BeforeEach
    void setUp() {
        pathHandler.loadPaths();
    }
    @Test
    public void activePath(){

        // Az aktív pálya mindig automatikusan a default nevű lesz, ha nem választunk explicit mást.
        // Ha nincs default nevű, akkor null lesz az aktív pálya
        if (pathHandler.getActivePath() != null)
            assertEquals(pathHandler.getActivePath().getName(),"default");

        // A pályák száma meg kell hogy eggyezzen a paths nevű mappában lévő file-ok számával
        File dir = new File("paths\\");
        int numberOfPaths = dir.listFiles().length;
        assertEquals(pathHandler.getPaths().size(),numberOfPaths);

    }
}