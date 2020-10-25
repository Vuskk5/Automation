package codewars;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

// https://www.codewars.com/kata/55983863da40caa2c900004e/train/java
public class NextBiggerNumber {
    @Test
    public void test() {
        assertThat(nextBiggerNumber(12L)).isEqualTo(21L);
        assertThat(nextBiggerNumber(513L)).isEqualTo(531L);
        assertThat(nextBiggerNumber(2017L)).isEqualTo(2071L);
        assertThat(nextBiggerNumber(414L)).isEqualTo(441L);
        assertThat(nextBiggerNumber(144L)).isEqualTo(414L);
        assertThat(nextBiggerNumber(10990L)).isEqualTo(19009L);
        assertThat(nextBiggerNumber(9L)).isEqualTo(-1L);
        assertThat(nextBiggerNumber(111L)).isEqualTo(-1L);
        assertThat(nextBiggerNumber(531L)).isEqualTo(-1L);
        assertThat(nextBiggerNumber(210243L)).isEqualTo(210324L);
        assertThat(nextBiggerNumber(329490474)).isEqualTo(329490744L);
        assertThat(nextBiggerNumber(59884848483559L)).isEqualTo(59884848483595L);
    }

    public static long nextBiggerNumber(long number) {
        char[] digits = String.valueOf(number).toCharArray();

        for (int i = digits.length - 2; i >= 0; i--) {
            for (int j = digits.length - 1; j > i; j--) {
                if (digits[i] < digits[j]) {
                    char temp = digits[i];
                    digits[i] = digits[j];
                    digits[j] = temp;

                    Arrays.sort(digits, i + 1, digits.length);

                    return Long.parseLong(String.valueOf(digits));
                }
            }
        }

        return -1;
    }
}
