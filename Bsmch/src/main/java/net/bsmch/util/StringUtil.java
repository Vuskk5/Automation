package net.bsmch.util;

public class StringUtil {
    /**
     * Replace all none-alphanumeric characters in the string with an underscore.
     * @param text The text to replace.
     * @return The new text.
     */
    public static String forceAlphaNumeric(String text) {
        return text.replaceAll("[^a-zA-Z0-9.\\-]", "_");
    }

    /**
     * Converts a camelCase string to Title Case.
     * Example: "toString", would become "To String"
     * @param text The text to convert.
     * @return The new Text.
     */
    public static String toTitleCase(String text) {
        StringBuilder testName = new StringBuilder();
        String[] camelCaseWords = text.split("(?=[A-Z0-9])");

        for (String word : camelCaseWords) {
            testName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        testName.deleteCharAt(testName.length() - 1);

        return (testName.toString());
    }
}
