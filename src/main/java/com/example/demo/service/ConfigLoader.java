package com.example.demo.service;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {

    public static String file = "src/main/resources/application.properties";

    public static String load(String name) {
        try {
            Properties props = new Properties();
            try (FileInputStream propStream = new FileInputStream(file)) {
                props.load(propStream);
            }
            return props.getProperty(name);
        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration: " + e, e);
        }
    }
}