package asisimAdvanced.support.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class JsonUtil {
    public static final DateTimeFormatter JSON_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static final DateTimeFormatter JSON_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter JSON_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule()
                .addSerializer(     LocalDateTime.class, new DateTimeJsonSerializer())
                .addDeserializer(   LocalDateTime.class, new DateTimeJsonDeserializer())
                .addSerializer(     LocalDate.class, new DateJsonSerializer())
                .addDeserializer(   LocalDate.class, new DateJsonDeserializer());
        mapper.registerModule(module);
    }

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
