package asisimAdvanced.support;

import asisimAdvanced.models.Soldier;
import com.google.gson.Gson;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

public class DataClass {

    private <T> T[][] toTwoDimensional(T[] array) {
        @SuppressWarnings("unchecked")
        T[][] newArray = (T[][]) Array.newInstance(array.getClass(), array.length, 1);

        for (int index = 0; index < array.length; index++) {
            newArray[index][0] = array[index];
        }

        return newArray;
    }

    public <T> T jsonData(String fileName, Class<T> clazz) {
        // TODO - add jackson
        Gson gson = new Gson();

        ClassLoader classLoader = getClass().getClassLoader();

        InputStream stream = classLoader.getResourceAsStream(fileName);

        if (stream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            return gson.fromJson(reader, clazz);
        }

        throw new NullPointerException("Loaded file " + fileName + " is null stream.");
    }

    @DataProvider(name = "SoldierData")
    public Object[][] getSoldierData() {
        Soldier[] soldiers = jsonData("test-data/SoldierData.json", Soldier[].class);
        Soldier[][] s = toTwoDimensional(soldiers);
        return s;
    }
}
