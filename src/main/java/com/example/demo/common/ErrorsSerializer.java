package com.example.demo.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {

    @Override
    public void serialize(Errors errors, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        errors.getFieldErrors().stream().forEach(e -> {
            try{
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("field", e.getField());
                jsonGenerator.writeStringField("objectName", e.getObjectName());
                jsonGenerator.writeStringField("code", e.getCode());
                jsonGenerator.writeStringField("message", e.getDefaultMessage());
                jsonGenerator.writeEndObject();
                Object rejectiveValue = e.getRejectedValue();
                if(rejectiveValue != null) {
                    jsonGenerator.writeStringField("reject",rejectiveValue.toString());
                }
                jsonGenerator.writeEndObject();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        });

        errors.getGlobalErrors().stream().forEach(e -> {
            try{
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("objectName", e.getObjectName());
                jsonGenerator.writeStringField("code", e.getCode());
                jsonGenerator.writeStringField("message", e.getDefaultMessage());
                jsonGenerator.writeEndObject();
            }catch (IOException e1){
                e1.printStackTrace();
            }
        });
        jsonGenerator.writeEndArray();

    }
}
