package asisimAdvanced.support.util;

import org.testng.xml.dom.OnElement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ToStringHelper {
    private Map<String, Object> properties;
    private Object object;

    public ToStringHelper(Object object) {
        properties = new HashMap<>();
        this.object = object;
    }

    public ToStringHelper add(String propertyName, Object property) {
        properties.put(propertyName, property);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(object.getClass().getName()).append("[");

        Iterator<Map.Entry<String, Object>> iterator = properties.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> pair = iterator.next();
            if (pair.getValue() != null) {
                builder.append(pair.getKey()).append("=").append(pair.getValue()).append(", ");
            }
            iterator.remove(); // avoids a ConcurrentModificationException
        }

        builder.deleteCharAt(builder.length() - 1)
               .deleteCharAt(builder.length() - 1).append("]");

        return builder.toString();
    }
}
