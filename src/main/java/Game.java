public class Game {
    public static final int STRIKE = 10;
    private int rollsNumber = 0;
    public boolean isCompleted() {
        return false;
    }

    public void roll(int score) {
        if (score < 0) throw new IllegalScoreException(IllegalScoreException.ErrorCode.NEGATIVE_SCORE);
        if (score > STRIKE) throw new IllegalScoreException(IllegalScoreException.ErrorCode.SCORE_GREATER_THAN_TEN);
        rollsNumber += 1 + addRollIfStrike(score);
    }

    private static int addRollIfStrike(int score) {
        return score == STRIKE ? 1 : 0;
    }

    public int getFramesNumber() {
        return Math.floorDiv(rollsNumber, 2);
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
