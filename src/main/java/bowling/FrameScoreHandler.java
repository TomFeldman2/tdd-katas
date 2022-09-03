package bowling;

class FrameScoreHandler {
    private int bonusRolls;

    private int score;

    private final boolean isRegular;

    FrameScoreHandler(int score, int bonusRolls) {
        this.score = score;
        this.bonusRolls = bonusRolls;
        isRegular = bonusRolls == 0;
    }

    void addBonusScore(int rollScore) {
        if (bonusRolls <= 0) return;

        bonusRolls--;
        score += rollScore;
    }

    public int getScore() {
        return score;
    }

    public boolean isRegular() {
        return isRegular;
    }
}