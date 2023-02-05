import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {
    long time;
    Timer timer;
    @BeforeEach
    void setUp(){
        timer = new Timer(0.1,0);
    }

    @Test
    void tick(){
        time = System.currentTimeMillis();

        long loopCount = 100000 * 100000;
        // 10^10szer futó forciklus, hogy elteljen annyi idő hogy a timer többszörös tick-et is engedjen
        // a gép teljesítménye alapján lehet állítani a loopcount-ot
        for (long i = 0; i < loopCount; i++) {
            boolean ticked = timer.tick(()->{
                System.out.println("tick");
            });
            if (ticked){
                if (i != 0) {
                    assertEquals(100,System.currentTimeMillis() - time,5); // az előző tick óta kb 0.1 mp telt-e el
                }
                time = System.currentTimeMillis();
            }

        }

    }
}