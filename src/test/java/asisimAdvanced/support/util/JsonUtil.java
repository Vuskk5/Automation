package asisimAdvanced.support.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

public class JsonUtil {
    @SuppressWarnings("unchecked")
    private static <T> T[][] arrayToMatrix(T[] array, Class<T> arrayType, int innerArrayLength) {
        T[][] matrix = (T[][]) Array.newInstance(array.getClass(), array.length);

        for (int index = 0; index < array.length; index++) {
            matrix[index] = (T[]) Array.newInstance(arrayType, innerArrayLength);
            matrix[index][0] = array[index];
        }

        return matrix;
    }

    private static String readResource(String resource) {
        ClassLoader classLoader = JsonUtil.class.getClassLoader();
        InputStream stream = classLoader.getResourceAsStream(resource);

        if (stream == null) {
            throw new NullPointerException("Cant find " + resource);
        }

        BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(stream, StandardCharsets.UTF_8));

        return reader.lines().collect(Collectors.joining());
    }

    public static <T> T[] createArray(String fileName, Class<T> arrayType) {
        String jsonString = readResource(fileName);

        return parseJsonArray(jsonString, arrayType);
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] parseJsonArray(String json, Class<T> classOnWhichArrayIsDefined) {
        try {
            Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + classOnWhichArrayIsDefined.getName() + ";");
            ObjectMapper mapper = new ObjectMapper()
                                    .setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
            return mapper.readValue(json, arrayClass);
        }
        catch (ClassNotFoundException ex) {
            throw new RuntimeException("Could not create array class.", ex);
        }
        catch (IOException ex) {
            throw new RuntimeException("Error parsing json to array.", ex);
        }
    }

    public static String objectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T[][] getDataMatrix(String fileName, Class<T> clazz) {
        T[] array = createArray(fileName, clazz);

        return arrayToMatrix(array, clazz, 1);
    }
}
