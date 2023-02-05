import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangeableStatTest {

    ChangeableStat changeableStat ;
    @BeforeEach
    void setUp() {
        changeableStat = new ChangeableStat(100);
        changeableStat.multiplyBy(2);
    }

    @Test
    void additionAndMultiplication(){

        changeableStat.add(10);
        // 100*2 + 10 = 210 lenne az értéke ha sima double-t használnék,
        // azonban a fix számolási sorrend miatt, amit ez az osztály biztosít (100+10)*2 = 220 lesz az értéke
        assertEquals(220,changeableStat.calculate(),0.001);

    }
    @Test
    void reset(){

        // a szorzás miatt az eredeti érték nem egyezik a calculate függvény által számolttal
        assertNotEquals(changeableStat.getDefault(), changeableStat.calculate(), 0.001);
        changeableStat.reset();

        // reset után már igen
        assertEquals(changeableStat.getDefault(), changeableStat.calculate(),0.001);
    }
}