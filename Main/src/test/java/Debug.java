public class Debug {
    public static void main(String[] args) {
        // Const definition
        final int NUMBER_OF_DIGITS = 4;

        // Variables definition
        int temp;
        int randomInt;
        int randomNumber = 0;
        boolean isDuplicate;

        // Code section

        for (int digitIndex = 0; digitIndex < NUMBER_OF_DIGITS; digitIndex++) {
            temp = randomNumber;
            isDuplicate = false;

            // Get a random number between 1-9
            randomInt = (int) (Math.random() * 9) + 1;

            // Check if the number is a duplicate
            while (temp % 10 > 0 && !isDuplicate) {
                if (temp % 10 == randomInt) {
                    isDuplicate = true;
                }

                temp /= 10;
            }

            if (!isDuplicate) {
                randomNumber += randomInt * Math.pow(10, digitIndex);
            }
            else {
                digitIndex--;
            }
        }

        System.out.println(randomNumber);
    }
}
