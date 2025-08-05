package org.example.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SpringBean3 {
    @Bean
    public SpringBean1 springBean1() {
        SpringBean2 springBeanTwo1 = springBean2();
        SpringBean2 springBeanTwo2 = springBean2();
        System.out.println(springBeanTwo1);
        System.out.println(springBeanTwo2);

        return new SpringBean1();
    }

    @Bean
    public SpringBean2 springBean2(){
        return new SpringBean2();
    }
}
