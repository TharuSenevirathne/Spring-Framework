package org.example;

import org.example.bean.MyConnection;
import org.example.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppInitializer {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();

        MyConnection myConnection1 = context.getBean(MyConnection.class);
        MyConnection myConnection2 = context.getBean(MyConnection.class);
        System.out.println(myConnection1);
        System.out.println("-------------------");
        System.out.println(myConnection2);

        context.registerShutdownHook();
    }
}