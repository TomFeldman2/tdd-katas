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
    void rollWithScoreLargerThanTen_throwIllegalScoreException() {
        Game.IllegalScoreException scoreException = assertThrows(Game.IllegalScoreException.class, () -> game.roll(11));
        assertEquals(Game.IllegalScoreException.ErrorCode.SCORE_GREATER_THAN_TEN, scoreException.errorCode);
    }

    @Test
    void twoRollsWithTotalScoreLargerThanTen_throwIllegalScoreException() {
        game.roll(6);
        Game.IllegalScoreException scoreException = assertThrows(Game.IllegalScoreException.class, () -> game.roll(5));
        assertEquals(Game.IllegalScoreException.ErrorCode.SCORE_GREATER_THAN_TEN, scoreException.errorCode);
    }

    @Test
    void newGame_hasZeroFrames() {
        assertEquals(0, game.getFramesNumber());
    }

    @Test
    void gameWithOneRoll_haseZeroFrames() {
        game.roll(0);
        assertEquals(0, game.getFramesNumber());
    }

    @Test
    void gameWithTwoRolls_haseOneFrame() {
        game.roll(0);
        game.roll(0);
        assertEquals(1, game.getFramesNumber());
    }

    @Test
    void gameWithStrike_hasOneFrame() {
        game.roll(10);
        assertEquals(1, game.getFramesNumber());
    }
}
