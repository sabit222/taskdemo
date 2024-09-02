package com.example.taskdemo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;


import java.io.IOException;
import java.util.Locale;

public class PriorityDeserializer extends JsonDeserializer<Priority> {
    @Override
    public Priority deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException{
        String value = p.getValueAsString();
        try {
            return Priority.valueOf(value.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new IOException("Unknown priority value: " + value);
        }
    }
}
