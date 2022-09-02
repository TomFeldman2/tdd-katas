import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestGame {
    @Test
    void newGame_isNotCompleted() {
        Game game = new Game();
        Assertions.assertFalse(game.isCompleted());
    }
}
