package org.example.bean;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;

// Api hadapu nathi class ekak me
public class MyConnection implements DisposableBean, BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean {

    // 1.the state of insantiation
    public MyConnection() {
        System.out.println("MyConnection constructor");
    }

    // 2.there is no method to find state of populate properties



    // 3.the state of Bean Name Aware. Bean id eka set wenw
    @Override
    public void setBeanName(String name) {
        System.out.println("SetBeanName");
    }

    // 4.the state of Bean Factory Aware . dependency injection bean ekt add wena welawa
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("SetBeanFactory");
    }

    // 5.the state of Application Context Aware
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("SetApplicationContext");
    }

    // 6.the state of Initializing bean
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AfterPropertiesSet");
    }

    //7. Disposiable Bean. context close and removing all available beans
    @Override
    public void destroy() throws Exception {
        System.out.println("Destroy");
    }
}
