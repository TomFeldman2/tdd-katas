public class Game {
    public boolean isCompleted() {
        return false;
    }

    public void roll(int score) {
        if (score < 0) throw new IllegalScoreException();
        if (score > 10) throw new IllegalScoreException();
    }

    public static class IllegalScoreException extends IllegalArgumentException {
    }
}
