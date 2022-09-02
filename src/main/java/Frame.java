import java.util.Optional;

public class Frame {
    private final static int PINS_NUMBER = 10;
    private State state = State.NO_THROW;
    private int score = 0;

    public Optional<FrameScoreHandler> roll(int rollScore) {
        if (state == State.TWO_THROW) throw new IllegalStateException("Cannot roll in a completed frame");
        if (rollScore < 0) throw new Game.IllegalScoreException(Game.IllegalScoreException.ErrorCode.NEGATIVE_SCORE);
        if (score + rollScore > PINS_NUMBER) throw new Game.IllegalScoreException(Game.IllegalScoreException.ErrorCode.SCORE_GREATER_THAN_TEN);

        score += rollScore;
        state = state.getNextSate();

        return getFrameBonusHandler();
    }

    private Optional<FrameScoreHandler> getFrameBonusHandler() {
        if (state == State.TWO_THROW) {
            return Optional.of(getNonStrikeBonusHandler());
        }

        if (score == PINS_NUMBER) {
            return Optional.of(getStrikeBonusHandler());
        }

        return Optional.empty();
    }

    private FrameScoreHandler getStrikeBonusHandler() {
        return getFrameBonusHandlerByFrameResult(Result.STRIKE);
    }

    private FrameScoreHandler getNonStrikeBonusHandler() {
        if (score == PINS_NUMBER) {
            return getFrameBonusHandlerByFrameResult(Result.SPARE);
        }

        return getFrameBonusHandlerByFrameResult(Result.REGULAR);
    }

    private FrameScoreHandler getFrameBonusHandlerByFrameResult(Result result) {
        return new FrameScoreHandler(score, result.getBonusRolls());
    }

    enum State {
        NO_THROW {
            @Override
            State getNextSate() {
                return ONE_THROW;
            }
        },
        ONE_THROW {
            @Override
            State getNextSate() {
                return TWO_THROW;
            }
        },
        TWO_THROW {
            @Override
            State getNextSate() {
                return TWO_THROW;
            }
        };

        abstract State getNextSate();
    }

    enum Result {
        REGULAR(0),
        SPARE(1),
        STRIKE(2);

        private final int bonusRolls;

        Result(int bonusRolls) {
            this.bonusRolls = bonusRolls;
        }

        public int getBonusRolls() {
            return bonusRolls;
        }
    }
}
