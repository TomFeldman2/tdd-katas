public class Game {
    public boolean isCompleted() {
        return false;
    }

    public void roll(int score) {
        if (score < 0) throw new IllegalScoreException(IllegalScoreException.ErrorCode.NEGATIVE_SCORE);
        if (score > 10) throw new IllegalScoreException(IllegalScoreException.ErrorCode.SCORE_GREATER_THAN_TEN);
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
