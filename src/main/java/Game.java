import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Frame> frames = new ArrayList<>(10);
    public boolean isCompleted() {
        return false;
    }

    public Game() {
        frames.add(new Frame());
    }

    public void roll(int score) {
        getLastFrame()
                .roll(score)
                .ifPresent(frames::add);
    }

    private Frame getLastFrame() {
        return frames.get(frames.size() - 1);
    }

    public int getFramesNumber() {
        return frames.size() - 1 + (getLastFrame().isFrameCompleted() ? 1 : 0);
    }

    public static class IllegalScoreException extends IllegalArgumentException {

        public final ErrorCode errorCode;

        public IllegalScoreException(ErrorCode errorCode) {
            this.errorCode = errorCode;
        }

        public enum ErrorCode {
            NEGATIVE_SCORE,
            SCORE_GREATER_THAN_TEN
        }
    }
}
