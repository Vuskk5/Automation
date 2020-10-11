import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Practice {
    @Test
    public void decimalToFractionTest() {
        assertThat(decimalToFraction(0.125f)).isEqualTo("1/8");
        assertThat(decimalToFraction(0.375f)).isEqualTo("3/8");
        assertThat(decimalToFraction(0.5f)).isEqualTo("1/2");
        System.out.println(decimalToFraction(0.2f));
    }

    private static final int MAX_FRACTION = 1000;

    public static String decimalToFraction(float decimal) {
        for (int denominator = 1; denominator < MAX_FRACTION; denominator++) {
            float divisor = (1 / (float) denominator);
            float numerator = (decimal / divisor);

            if (decimal % divisor == 0) {
                return (int)numerator + "/" + denominator;
            }
        }

        return null;
    }
}
