package codewars;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

// https://www.codewars.com/kata/52e88b39ffb6ac53a400022e
public class Int32ToIP {
    @Test
    public void test() {
        assertEquals(longToIP(2154959208L), "128.114.17.104");
        assertEquals(longToIP(0), "0.0.0.0");
        assertEquals(longToIP(2149583361L), "128.32.10.1");
        assertEquals(longToIP(886266630L), "52.211.91.6");
    }

    public static String longToIP(long ip) {
        String binaryIp = ("00000000000000000000000000000000" + Long.toBinaryString(ip)).substring(Long.toBinaryString(ip).length());
        int[] octets = new int[4];
        int octetSum = 0;

        for (int bitIndex = 1; bitIndex <= binaryIp.length(); bitIndex++) {
            char bit = binaryIp.charAt(binaryIp.length() - bitIndex);

            if  (bit == '1') {
                octetSum += Math.pow(2, (bitIndex - 1) % 8);
            }

            if (bitIndex % 8 == 0) {
                octets[4 - (bitIndex / 8)] = octetSum;
                octetSum = 0;
            }
        }

        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3];
    }
}
