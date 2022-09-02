import java.util.Optional;

public class Frame {
    private final static int STRIKE = 10;
    private State state = State.EMPTY;
    private int score = 0;

    public Optional<Frame> roll(int rollScore) {
        if (rollScore < 0) throw new Game.IllegalScoreException(Game.IllegalScoreException.ErrorCode.NEGATIVE_SCORE);
        if (score + rollScore > STRIKE) throw new Game.IllegalScoreException(Game.IllegalScoreException.ErrorCode.SCORE_GREATER_THAN_TEN);

        score += rollScore;

        if (score == STRIKE || state == State.DURING) {
            state = State.COMPLETED;
            return Optional.of(new Frame());
        }

        state = State.DURING;
        return Optional.empty();
    }

    public boolean isFrameCompleted() {
        return state == State.COMPLETED;
    }

    enum State {
        EMPTY,
        DURING,
        COMPLETED
    }
}
