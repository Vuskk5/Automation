package bowling;

public class Frame {
    int extraScore;     // Bonus points awarded from strikes and spares
    int firstChance;    // Number of pins downed at the 1st throw
    int secondChance;   // Number of pints downed at the 2nd throw
    boolean isStrike;   // Whether this frame is a strike
    boolean isSpare;    // Whether this frame is a spare

    public Frame() {
        extraScore = 0;
        firstChance = 0;
        secondChance = 0;
        isStrike = false;
        isSpare = false;
    }

    // Returns the total score of this frame
    public int getScore() {
        return (firstChance + secondChance + extraScore);
    }

    public int getExtraScore() {
        return extraScore;
    }

    public void setExtraScore(int extraScore) {
        this.extraScore = extraScore;
    }

    public int getFirstChance() {
        return firstChance;
    }

    public void setFirstChance(int firstChance) {
        this.firstChance = firstChance;
    }

    public int getSecondChance() {
        return secondChance;
    }

    public void setSecondChance(int secondChance) {
        this.secondChance = secondChance;
    }

    public boolean isStrike() {
        return isStrike;
    }

    public void setStrike(boolean strike) {
        isStrike = strike;
    }

    public boolean isSpare() {
        return isSpare;
    }

    public void setSpare(boolean spare) {
        isSpare = spare;
    }

    public String toString() {
        if (isStrike) {
            return "_|X = " + getScore();
        }
        else if (isSpare) {
            return firstChance + "|/ = " + getScore();
        }
        else {
            return firstChance + "," + secondChance + " = " + getScore();
        }
    }
}
