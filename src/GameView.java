/**
 * Egy nézete a játéknak
 */
public interface GameView {
    /**
     * akkor hívódik meg ha a nézet megjelenik a képernyőn
     */
    default public void onAppearing(){}
    /**
     * akkor hívódik meg ha a nézet éppen aktív volt, de egy új nézet vált aktívvá
     */
    default public void onDisappearing(){}
}
