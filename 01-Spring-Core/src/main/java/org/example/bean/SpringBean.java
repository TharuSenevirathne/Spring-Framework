package org.example.bean;


import org.springframework.stereotype.Component;

@Component
public class SpringBean {

    public SpringBean() {
        System.out.println("SpringBean constructor");
    }

    public void sayHello() {
        System.out.println("Hello");
    }

}
