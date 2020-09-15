package asisimAdvanced.support;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class JsonUtil {
    @SuppressWarnings("unchecked")
    private static <T> T[][] oneDimensionalToTwoDimensional(T[] array, Class<T> arrayType, int innerArrayLength) {
        T[][] newArray = (T[][]) Array.newInstance(array.getClass(), array.length);

        for (int index = 0; index < array.length; index++) {
            newArray[index] = (T[]) Array.newInstance(arrayType, innerArrayLength);
            newArray[index][0] = array[index];
        }

        return newArray;
    }

    private static String readResource(String resource) {
        ClassLoader classLoader = JsonUtil.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(resource);

        if (stream == null) {
            throw new NullPointerException("Cant find " + resource);
        }

        InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        return (new BufferedReader(reader))
                                    .lines()
                                    .collect(Collectors.joining());
    }

    public static <T> T[] createArray(String fileName, Class<T> arrayType) {
        String jsonString = readResource(fileName);

        try {
            return parseJsonArray(jsonString, arrayType);
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] parseJsonArray(String json, Class<T> classOnWhichArrayIsDefined)
            throws IOException, ClassNotFoundException {
        Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + classOnWhichArrayIsDefined.getName() + ";");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, arrayClass);
    }

    public static <T> T[][] getDataAs2dArray(String fileName, Class<T> clazz) {
        T[] array = createArray(fileName, clazz);
        return oneDimensionalToTwoDimensional(array, clazz, 1);
    }
}
