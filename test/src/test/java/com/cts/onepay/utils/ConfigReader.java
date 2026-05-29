package com.cts.onepay.utils;

import java.io.FileReader;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    static{
        try{
            properties.load(new FileReader(System.getProperty("user.dir")+"/src/test/resources/"+CONFIG_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key){
        return properties.getProperty(key);
    }

    public static long getLong(String key) {
        return Long.parseLong(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
