import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestGame {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void newGame_isNotCompleted() {

        assertFalse(game.isCompleted());
    }

    @Test
    void rollWithNegativeValue_throwsIllegalScoreException() {
        assertThrows(Game.IllegalScoreException.class, () -> game.roll(-1));
    }

    @Test
    void rollWithValueLargerThanTen_throwIllegalScoreException() {
        assertThrows(Game.IllegalScoreException.class, () -> game.roll(11));
    }
}
