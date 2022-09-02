import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int FRAMES_NUMBER = 10;
    private final List<FrameScoreHandler> frameScoreHandlers = new ArrayList<>(FRAMES_NUMBER);

    private Frame currentFrame = new Frame();

    public void roll(int score) {

        if (isGameEnded()) throw new GameEndedException();

        addBonusRollFromEnd(1, score);
        addBonusRollFromEnd(2, score);

        currentFrame
                .roll(score)
                .ifPresent(this::onFrameCompleted);
    }

    private boolean isGameEnded() {
       if (frameScoreHandlers.size() != FRAMES_NUMBER) return false;

       if (frameScoreHandlers.get(frameScoreHandlers.size() - 1).isRegular()) {
           return true;
       }

       return currentFrame.isNotEmpty();
    }



    private void addBonusRollFromEnd(int index, int score) {
        if (frameScoreHandlers.size() >= index) {
            frameScoreHandlers.get(frameScoreHandlers.size() - index).addBonusScore(score);
        }
    }

    private void onFrameCompleted(FrameScoreHandler frameScoreHandler) {
        frameScoreHandlers.add(frameScoreHandler);
        currentFrame = new Frame();
    }

    public int getFramesNumber() {
        return frameScoreHandlers.size();
    }

    public int getScore() {
        int sum = frameScoreHandlers.stream()
                .mapToInt(FrameScoreHandler::getScore)
                .sum();

        return sum + currentFrame.getScore();
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
