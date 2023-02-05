import org.junit.jupiter.api.Assertions;

import java.awt.event.MouseEvent;

class PathMakerTest {
    PathMaker maker;
    @org.junit.jupiter.api.BeforeEach
    void init(){
        maker = new PathMaker();
        maker.addPoint(100,200);
    }
    @org.junit.jupiter.api.Test
    void addPoint() {
        Assertions.assertEquals(maker.size(),1);
        //assertEquals();
    }
    @org.junit.jupiter.api.Test
    void removePoint() {

        maker.removePoint(100,200); // ez töröl meglévő pontot
        maker.removePoint(200,300); // ez nem, mert nincs a környezetében a listában lévő pontokból egy sem
        Assertions.assertEquals(maker.size(),0);
        //assertEquals();
    }

}