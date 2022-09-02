import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        Game.IllegalScoreException scoreException = assertThrows(Game.IllegalScoreException.class, () -> game.roll(-1));
        assertEquals(Game.IllegalScoreException.ErrorCode.NEGATIVE_SCORE, scoreException.errorCode);
    }

    @Test
    void rollWithValueLargerThanTen_throwIllegalScoreException() {
        Game.IllegalScoreException scoreException = assertThrows(Game.IllegalScoreException.class, () -> game.roll(11));
        assertEquals(Game.IllegalScoreException.ErrorCode.SCORE_GREATER_THAN_TEN, scoreException.errorCode);
    }
}
