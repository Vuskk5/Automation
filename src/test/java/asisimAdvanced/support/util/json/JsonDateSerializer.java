package asisimAdvanced.support.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonDateSerializer extends JsonSerializer<LocalDateTime> {
    private static final String pattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void serialize(LocalDateTime date, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        final String dateString = date.format(formatter);
        generator.writeString(dateString);
    }
}
