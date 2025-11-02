package org.example.config;

import org.example.bean.MyConnection;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration // Spring Bean Configuration ekt adala beans tika dgnn widiha
@ComponentScan(basePackages = "org.example.bean")  // configuration class එකට Beans scan කරන්න
// ඕන path එක කොහෙද කියල කියනො.
public class AppConfig {

    @Bean("senez")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) // => Singleton thamai default inne
    public MyConnection myConnection() {
        return new MyConnection();
    }

}
