package org.example;

import org.example.bean.SpringBean1;
import org.example.bean.SpringBean2;
import org.example.config.AppConfig1;
import org.example.config.AppConfig2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class AppInitializer {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig1.class);
        context.register(AppConfig2.class);
        context.refresh();

        SpringBean1 springBean1 = context.getBean(SpringBean1.class);
        SpringBean1 springBean2 = context.getBean(SpringBean1.class);
        System.out.println(springBean1);
        System.out.println(springBean2);

        SpringBean2 springBean3 = context.getBean(SpringBean2.class);
        SpringBean2 springBean4 = context.getBean(SpringBean2.class);
        System.out.println(springBean3);
        System.out.println(springBean4);

        context.registerShutdownHook();
    }
}