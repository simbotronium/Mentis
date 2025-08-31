package com.example.mentis.application.serialisation;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;

public class SimpleBooleanPropertyDeserializer extends JsonDeserializer<SimpleBooleanProperty> {

    @Override
    public SimpleBooleanProperty deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return new SimpleBooleanProperty(p.getBooleanValue());
    }

}

