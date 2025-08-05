package org.example.config;

import org.example.bean.SpringBean1;
import org.example.bean.SpringBean2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example.bean")
public class AppConfig {

// InterBean Dependency Satisfied

    /* methana thina me code tika SpringBeanThree ekt aran giya
    @Bean
    public SpringBean1 springBean1() {
        SpringBean2 springBean2One = new SpringBean2();
        SpringBean2 springBean2Two = new SpringBean2();
        System.out.println(springBean2One);
        System.out.println(springBean2Two);

        return new SpringBean1();
    }

    @Bean
    public SpringBean2 springBean2() {
        return new SpringBean2();
    } */
}
