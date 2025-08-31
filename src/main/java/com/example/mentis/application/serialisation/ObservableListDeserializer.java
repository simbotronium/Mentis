package com.example.mentis.application.serialisation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.List;

public class ObservableListDeserializer<T> extends JsonDeserializer<ObservableList<T>> implements ContextualDeserializer {

    private final JavaType valueType;

    public ObservableListDeserializer() {
        this.valueType = null;
    }

    private ObservableListDeserializer(JavaType valueType) {
        this.valueType = valueType;
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        JavaType wrapperType = property.getType(); // ObservableList<T>
        JavaType contentType = wrapperType.containedType(0);
        return new ObservableListDeserializer<>(contentType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<T> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        List<T> list = mapper.readValue(
                p,
                mapper.getTypeFactory().constructCollectionType(List.class, valueType)
        );
        return FXCollections.observableArrayList(list);
    }
}

