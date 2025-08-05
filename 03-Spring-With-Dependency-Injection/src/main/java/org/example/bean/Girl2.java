package org.example.bean;

import jdk.jfr.Percentage;
import org.springframework.stereotype.Component;

@Component
@Percentage
public class Girl2 implements Agreement {
    public Girl2() {
        System.out.println("Girl2 constructor");
    }

    public void chat() {
        System.out.println("Girl2 chat");
    }
}
