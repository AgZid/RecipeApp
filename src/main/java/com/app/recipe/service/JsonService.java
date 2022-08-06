package com.app.recipe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class JsonService {

    @Autowired
    private ObjectMapper objectMapper;

    public void exportToJson(Map<String, Double> text) {

        try {
            objectMapper.writeValue(new File("Ingredients.json"), text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
