import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HpHandlerTest {
    private HpHandler hp;
    @BeforeEach
    void setUp() {
        hp = new HpHandler(100);
    }
    @Test
    void takeDamage(){
        hp.takeDamage(10);
        assertEquals(hp.getHpRatio(),0.9,0.01);
    }

    @Test
    void hpUnderZero(){
        hp.takeDamage(101);
        assertTrue(hp.underZero());
    }

    @Test
    void cloning(){
        hp.takeDamage(20);
        assertEquals(hp.clone().getHpRatio(),hp.getHpRatio());
    }
}