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
