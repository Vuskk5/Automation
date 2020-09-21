package asisimAdvanced.support.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonDateDeserializer extends JsonDeserializer<LocalDateTime> {
    private static final String pattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectCodec codec = parser.getCodec();
        TextNode node = codec.readTree(parser);
        String dateString = node.textValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateString, formatter);
    }
}
