package org.example.di;

import org.springframework.stereotype.Component;

@Component
public class Test1 implements DI {
    private Test1(){
        System.out.println("Test1 constructor");
    }

    @Override
    public void sayHello() {
        System.out.println("Say Hello");
    }
}
