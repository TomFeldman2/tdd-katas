import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int FRAMES_NUMBER = 10;
    private final List<FrameScoreHandler> frameScoreHandlers = new ArrayList<>(FRAMES_NUMBER);

    private Frame currentFrame = new Frame();

    public void roll(int score) {
        if (currentFrame == null) throw new GameEndedException();

        addBonusRollFromEnd(1, score);
        addBonusRollFromEnd(2, score);

        currentFrame
                .roll(score)
                .ifPresent(this::onFrameCompleted);
    }

    private void addBonusRollFromEnd(int index, int score) {
        if (frameScoreHandlers.size() >= index) {
            frameScoreHandlers.get(frameScoreHandlers.size() - index).addBonusScore(score);
        }
    }

    private void onFrameCompleted(FrameScoreHandler frameScoreHandler) {
        frameScoreHandlers.add(frameScoreHandler);

        if (frameScoreHandlers.size() == FRAMES_NUMBER) {
            currentFrame = null;
            return;
        }

        currentFrame = new Frame();
    }

    public int getFramesNumber() {
        return frameScoreHandlers.size();
    }

    public int getScore() {
        return frameScoreHandlers.stream()
                .mapToInt(FrameScoreHandler::getScore)
                .sum();
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

    public static class GameEndedException extends RuntimeException{
    }
}
