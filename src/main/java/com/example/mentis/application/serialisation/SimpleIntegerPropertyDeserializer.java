package com.example.mentis.application.serialisation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.IOException;

public class SimpleIntegerPropertyDeserializer extends JsonDeserializer<SimpleIntegerProperty> {

    @Override
    public SimpleIntegerProperty deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return new SimpleIntegerProperty(p.getIntValue());
    }

}
