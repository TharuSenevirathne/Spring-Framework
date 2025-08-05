package org.example.config;

import org.example.bean.E;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "org.example.bean")
@Import({AppConfig1.class, AppConfig2.class})
/*@ImportResource("classpath:applicationContext.xml") = me project eke thina path eka // xml configuration me widihata register krnnth puluwan
@ImportResource("file:absolute/path/to/applicationContext.xml") = me c drive eke idnm ena path eka */
public class AppConfig {
    @Bean
    public E e() {
        return new E();
    }
}
