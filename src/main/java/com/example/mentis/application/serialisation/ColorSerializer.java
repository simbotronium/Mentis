package com.example.mentis.application.serialisation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ColorSerializer extends JsonSerializer<Color> {

    @Override
    public void serialize(Color color, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(color.toString()); // e.g. "0x123456ff"
    }

}
