package com.example.mentis.application.serialisation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import javafx.beans.property.SimpleLongProperty;

import java.io.IOException;

public class SimpleLongPropertyDeserializer extends JsonDeserializer<SimpleLongProperty> {

    @Override
    public SimpleLongProperty deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return new SimpleLongProperty(p.getLongValue());
    }

}
