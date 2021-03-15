package org.upgrad.upstac.testutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class JsonHelper {

    private static final Logger log = LoggerFactory.getLogger(JsonHelper.class);

    public static <T> T getAsObjectOfType(String json, Class<T> tClass){

        //
        T object= null;
        try {
            object = getObjectMapper().readValue(json,tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return object;
    }

    public static <T> String getAsJsonString(T item) {

        try {
            final ObjectMapper objectMapper = getObjectMapper();
            String s = objectMapper.writeValueAsString(item);
           return s;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static ObjectMapper getObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static <T> void saveJsonToFile(List<T> items) {

        ObjectMapper objectMapper = getObjectMapper();
        try {
            File file = File.createTempFile("json-data",".json");
            byte[] byteArray = objectMapper.writeValueAsBytes(items);
            objectMapper.writeValue(file, items);
         //   log.info("Json COntents Saved in " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
