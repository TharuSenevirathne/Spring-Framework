package org.example.bean;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component // component annotation eka nisa spring bean constructor call weno
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // @Scope("prototype") = mehema dennath puluwn
public class TestBean1 {

    public TestBean1() {
        System.out.println("TestBean1 constructor");
    }

}
