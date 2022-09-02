class FrameScoreHandler {
    private int bonusRolls;

    private int score;

    FrameScoreHandler(int score, int bonusRolls) {
        this.score = score;
        this.bonusRolls = bonusRolls;
    }

    void addBonusScore(int rollScore) {
        if (bonusRolls <= 0) return;

        bonusRolls--;
        score += rollScore;
    }

    public int getScore() {
        return score;
    }
}