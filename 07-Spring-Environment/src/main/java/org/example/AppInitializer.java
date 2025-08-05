package org.example;

import org.example.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.Properties;

public class AppInitializer {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();


//System Variables

        Map<String,String> getEnv = System.getenv();
        for (String key : getEnv.keySet()) {
            System.out.println(key + " = " + getEnv.get(key));
         //   System.out.println(entry.get("OS"));
        }
        System.out.println("---------------------------------");


// Java Variables

        Properties properties = System.getProperties();
        for (String key : properties.stringPropertyNames()) {
            System.out.println(key + ":" + properties.get(key));
        }
        System.out.println("=====================================");
        String osName = System.getProperty("os.name");
        System.out.println(osName);

        context.registerShutdownHook();
    }
}