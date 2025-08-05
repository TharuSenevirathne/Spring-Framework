package org.example.bean;

import org.springframework.stereotype.Component;

@Component
public class TestBean {
    public TestBean() {
        System.out.println("TestBean constructor");
    }
}
