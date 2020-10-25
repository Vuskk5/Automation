package codewars;

import org.testng.annotations.Test;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;

// https://www.codewars.com/kata/5d325ae1dddb56dc5b740738
public class MadRobotProblem {
    @Test
    public void test() {
        // Cant see
        assertThat(boxVisibleDirections("0;0 1;0 2;0 0;1 1;1 2;1 0;2 1;2 2;2", 4))
                .isEqualTo(new char[] {  });
        // Cant see and small height
        assertThat(boxVisibleDirections("0;0 1;0 2;0 0;1 1;1 2;1 0;2 1;2 2;2 0;0 1;0 2;0 0;1 1;1 2;1 0;2 1;2 2;2", 4))
                .isEqualTo(new char[] {  });
        // Visible because of height
        assertThat(boxVisibleDirections("0;0 0;0 2;0 2;0 0;1 1;1 1;1 2;1 0;2 0;2 1;2 2;2 2;2", 6))
                .isEqualTo(new char[] { 'n', 'w', 's', 'e' });
        // Invisible from certain directions
        assertThat(boxVisibleDirections("0;0 0;0 2;0 2;0 0;1 1;1 1;1 2;1 0;2 0;2 1;2 2;2 2;2", 5))
                .isEqualTo(new char[] { 'n' });
        assertThat(boxVisibleDirections("2;2 2;2 1;1 1;1 2;1 2;1 3;1 3;1 1;2 1;2 1;3 1;3 3;2 3;2 3;3 3;3", 1))
                .isEqualTo(new char[] { 's' });
        assertThat(boxVisibleDirections("0;0 1;1 0;1 1;0 4;0 0;0", 1))
                .isEqualTo(new char[] { 's', 'e' });
        assertThat(boxVisibleDirections("0;0 5;5 5;0 5;5 5;3 5;3 5;5 4;5 4;3 4;5 5;7 5;6 5;7 5;7", 3))
                .isEqualTo(new char[] { 'e' });
        // Visible from every direction
        assertThat(boxVisibleDirections("10;10 3;2", 0))
                .isEqualTo(new char[] { 'n', 'w', 's', 'e' });
    }

    private static final char[] DIRECTIONS = { 'n', 'w', 's', 'e' };

    public static char[] boxVisibleDirections(String loadingHistory, int madRobotIndex) {
        int platformMaxDimension =  stream(loadingHistory.split("[\\s;]"))
                                    .mapToInt(Integer::parseInt)
                                    .max()
                                    .orElse(-1) + 1;

        String[] boxesLocationsAsString = loadingHistory.split(" ");

        int[][] boxes = new int[platformMaxDimension][platformMaxDimension];

        int robotBoxHeight = 0;
        int robotX = 0;
        int robotY = 0;

        // Populate the platform
        for (int boxIndex = 0; boxIndex < boxesLocationsAsString.length; boxIndex++) {
            String location = boxesLocationsAsString[boxIndex];

            int x = Integer.parseInt(location.split(";")[0]);
            int y = Integer.parseInt(location.split(";")[1]);

            boxes[y][x]++;

            if (boxIndex == madRobotIndex) {
                robotBoxHeight = boxes[y][x];
                robotX = x;
                robotY = y;
            }
        }

        // Check all directions for blocking boxes
        StringBuilder directions = new StringBuilder();

        for (int directionIndex = 0; directionIndex < DIRECTIONS.length; directionIndex++) {
            int tempX = robotX;
            int tempY = robotY;

            boolean canSee = true;

            while ( (tempX > 0) && (tempX < (platformMaxDimension - 1)) &&
                    (tempY > 0) && (tempY < (platformMaxDimension - 1)) && (canSee)) {
                double angleInRadians = Math.toRadians(directionIndex * 90);

                tempX -= Math.round(Math.sin(angleInRadians));
                tempY -= Math.round(Math.cos(angleInRadians));

                if (boxes[tempY][tempX] >= robotBoxHeight) {
                    canSee = false;
                }
            }

            if (canSee) {
                directions.append(DIRECTIONS[directionIndex]);
            }
        }

        return directions.toString().toCharArray();
    }
}
