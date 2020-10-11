package asisimAdvanced.support.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateJsonSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate date, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        final String dateString = date.format(JsonUtil.JSON_DATE_FORMAT);
        generator.writeString(dateString);
    }
}
