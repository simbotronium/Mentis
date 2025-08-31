package com.example.mentis.application.serialisation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class ObservableListDeserializer<T> extends JsonDeserializer<ObservableList<T>> {

    private final Class<T> clazz;

    public ObservableListDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public ObservableList<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        List<T> list = mapper.readValue(p, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        return FXCollections.observableArrayList(list);
    }

}
