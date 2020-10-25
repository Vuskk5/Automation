package codewars;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

// https://www.codewars.com/kata/526989a41034285187000de4/train/java
public class CountIPAddresses {
    @Test
    public void test() {
        assertThat(ipsBetween("10.0.0.0", "10.0.0.50")).isEqualTo(50L);
        assertThat(ipsBetween("10.0.0.0", "10.0.1.0")).isEqualTo(256L);
        assertThat(ipsBetween("20.0.0.10", "20.0.1.0")).isEqualTo(246L);
    }

    public static long ipsBetween(String start, String end) {
        long range = 0;
        String[] startAddresses = start.split("[.]");
        String[] endAddresses = end.split("[.]");

        for (int byteIndex = 0; byteIndex < 4; byteIndex++) {
            int startIpByte = Integer.parseInt(startAddresses[4 - (byteIndex + 1)]);
            int endIpByte   = Integer.parseInt(endAddresses[4 - (byteIndex + 1)]);

            range += (endIpByte - startIpByte) * Math.pow(256, byteIndex);
        }

        return range;
    }
}
