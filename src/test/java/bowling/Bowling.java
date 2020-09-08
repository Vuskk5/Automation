package bowling;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Bowling {
    @DataProvider(name = "BowlingData")
    public Object[][] getBowlingData() {
        return new Object[][] {
            new Object[]{ "10\n10\n10\n10\n10\n10\n10\n10\n7\n2\n10\n5\n5", 255 },
            new Object[]{ "10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n10", 300 },
            new Object[]{ "10\n10\n10\n10\n10\n10\n10\n10\n10\n10\n4\n5", 283 },
            new Object[]{ "2\n2\n5\n2\n8\n0\n10\n3\n1\n10\n10\n2\n8\n6\n4\n10\n6\n4", 135 },
            new Object[]{ "10\n3\n7\n5\n2\n7\n0\n10\n10\n9\n1\n4\n4\n9\n1\n10\n4\n6", 160 },
            new Object[]{ "4\n3\n7\n3\n10\n10\n10\n9\n1\n7\n2\n5\n5\n10\n4\n6\n10", 192 }
        };
    }

    @Test(dataProvider = "BowlingData")
    public void bowlingTest(String bowlingInput, int expectedScore) {
        int score = getBowlingScore(bowlingInput);

        assertThat(score).isEqualTo(expectedScore);
    }

    private static Scanner reader = new Scanner(System.in);

    public int getBowlingScore(String bowlingInput) {
        InputStream stream = new ByteArrayInputStream(bowlingInput.getBytes());
        Scanner reader = new Scanner(stream);

        final int MAX_FRAMES = 10;

        int totalScore = 0;

        Frame[] frames = new Frame[MAX_FRAMES];

        // Code section

        for (int frameIndex = 0; frameIndex < MAX_FRAMES; frameIndex++) {
            Frame currentFrame = new Frame();
            frames[frameIndex] = currentFrame;

            System.out.println("Frame #" + (frameIndex + 1));
            System.out.print("1st chance: ");
            currentFrame.firstChance = reader.nextInt();

            // Strike
            if (currentFrame.firstChance == 10) {
                System.out.println("-- Strike! --");
                currentFrame.isStrike = true;
            }
            else {
                System.out.print("2nd chance: ");
                currentFrame.secondChance = reader.nextInt();

                // Spare
                if (currentFrame.firstChance + currentFrame.secondChance == 10) {
                    System.out.println("-- Spare! --");
                    currentFrame.isSpare = true;
                }
            }

            // Check frames affected by this frame's score
            if (frameIndex > 0) {
                Frame previousFrame = frames[frameIndex - 1];

                if (previousFrame.isStrike) {
                    previousFrame.extraScore = (currentFrame.firstChance + currentFrame.secondChance);

                    if (frameIndex > 1) {
                        Frame secondPreviousFrame = frames[frameIndex - 2];

                        if (secondPreviousFrame.isStrike) {
                            secondPreviousFrame.extraScore = (10 + currentFrame.firstChance);
                        }
                    }
                }
                else if (previousFrame.isSpare) {
                    previousFrame.extraScore = currentFrame.firstChance;
                }
            }
        }

        // If last throw was a strike or spare you get an extra throw
        Frame lastFrame = frames[MAX_FRAMES - 1];
        if (lastFrame.isStrike || lastFrame.isSpare) {
            System.out.print("Extra throw: ");
            int extraThrow = reader.nextInt();
            lastFrame.extraScore += extraThrow;

            // 2nd throw for a strike
            if (lastFrame.isStrike) {
                System.out.print("2nd extra throw: ");
                lastFrame.extraScore += reader.nextInt();

                Frame previousFrame = frames[MAX_FRAMES - 2];

                if (previousFrame.isStrike) {
                    previousFrame.extraScore = 10 + extraThrow;
                }
            }
        }

        // Sum the score and print frames
        for (Frame frame : frames) {
            totalScore += frame.getScore();
//            System.out.println(frame.toString());
        }

        printGame(frames);

        System.out.println("Score: " + totalScore);

        return totalScore;
    }

    public void printGame(Frame[] frames) {
        final String separationLine = "\n|-----------------------------------------------------------|\n";

        StringBuilder toPrint = new StringBuilder();

        toPrint.append(separationLine).append("|");
        for (int i = 1; i <= frames.length; i++) {
            toPrint.append("  ").append(i).append((i < 10) ? " " : "").append(" |");
        }

        toPrint.append(separationLine).append("|");
        for (Frame frame : frames) {
            toPrint.append(" ").append(frame.toString().split(" ")[0]).append(" |");
        }

        toPrint.append(separationLine).append("|");
        for (Frame frame : frames) {
            toPrint.append("  ").append(frame.getScore()).append((frame.getScore() < 10) ? " " : "").append(" |");
        }

        toPrint.append(separationLine);

        System.out.println(toPrint);
    }
}
