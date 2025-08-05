package org.example.config;

import org.example.bean.MyConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "org.example.bean")
public class AppConfig {
    @Bean
    @Scope("prototype") // note eke liyl athi mekedi destory eka wada krnne nththe ai kiyl
    public MyConnection myConnection() {
        return new MyConnection();
    }
}
