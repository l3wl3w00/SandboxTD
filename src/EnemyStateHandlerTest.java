import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyStateHandlerTest {

    EnemyStateHandler enemyStateHandler;
    @BeforeEach
    void setUp() {
        enemyStateHandler = new EnemyStateHandler(null,100,10,10,10);
    }

    @Test
    void addEffect() {
        assertFalse(enemyStateHandler.isSlowed());
        enemyStateHandler.addEffect(new SlowEffect(0.5,0.5));
        enemyStateHandler.tick(0.1);
        assertTrue(enemyStateHandler.isSlowed());
    }
    @Test
    void removeEfect() {
        assertFalse(enemyStateHandler.isSlowed());
        enemyStateHandler.addEffect(new SlowEffect(0.5,0.5));
        enemyStateHandler.tick(0.1);
        assertTrue(enemyStateHandler.isSlowed());
        // eddig ugyanaz mint az addEffect
        enemyStateHandler.atEndOfTurn();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ha lejár a lassítás ideje akkor már nincs lassítva
        enemyStateHandler.tick(0.5);
        // ennek a körnek a végén veszi ki az aktív effektusok közül
        // a lassító effektust, tehát itt még aktív
        assertTrue(enemyStateHandler.isSlowed());
        enemyStateHandler.atEndOfTurn();

        // a követekző körben viszont már nincs lassítva
        enemyStateHandler.tick(0.1);
        assertFalse(enemyStateHandler.isSlowed());
    }



    @Test
    void setSlow() {
        assertFalse(enemyStateHandler.isSlowed());
        enemyStateHandler.setSlow(0.5);
        assertTrue(enemyStateHandler.isSlowed());
    }
}