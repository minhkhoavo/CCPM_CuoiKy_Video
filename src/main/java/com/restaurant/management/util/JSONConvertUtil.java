package com.restaurant.management.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public class JSONConvertUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String entityToJSON(List<T> entities) {
        try {
            return objectMapper.writeValueAsString(entities);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }
}
