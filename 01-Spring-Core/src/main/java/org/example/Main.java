package org.example;

import org.example.bean.MyConnection;
import org.example.bean.SpringBean;
import org.example.bean.TestBean1;
import org.example.bean.TestBean2;
import org.example.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();

//        SpringBean bean = context.getBean(SpringBean.class);
//        bean.sayHello();
//
//        TestBean1 TestBean1 = context.getBean(TestBean1.class);
//        System.out.println(TestBean1);
//
//        Runtime.getRuntime().addShutdownHook(new Thread(){  // Register shutdown hook to close context
//            public void run() {
//                context.close();
//            }
//        });
//        context.registerShutdownHook(); // Register shutdown hook mehemath puluwan
//
//        TestBean2 testBean2 = context.getBean(TestBean2.class);
//        System.out.println(testBean2);



//------Bean context eken bean ekak gnn widi

        //1.class name
//        SpringBean springBean = context.getBean(SpringBean.class);
//        System.out.println(springBean);

        //2Bean Id
//        SpringBean springBean1 = (SpringBean) context.getBean("springBean");
//        System.out.println(springBean1);
//        TestBean1 testBean1 = (TestBean1) context.getBean("testBean1");
//        System.out.println(testBean1);

        //3.Bean Id and class name
//        TestBean2 testBean2 = (TestBean2) context.getBean("senez"); // methana senez kiyl denw nan TestBean2 clz eketh @Component("senez") kiyl denn ona
//        System.out.println(testBean2);



//        MyConnection myConnection = context.getBean(MyConnection.class);
//        System.out.println(myConnection);

        // Bean Id -> Bean method name
//        MyConnection myConnection1 = (MyConnection) context.getBean("senez");
//        System.out.println(myConnection1);

        // Bean Id + class name
//        MyConnection myConnection2 = (MyConnection) context.getBean("senez" , MyConnection.class);
//        System.out.println(myConnection2);
//
//        context.registerShutdownHook();



//-----------------Bean Scope-------------------

        TestBean1 tst1 = context.getBean("testBean1", TestBean1.class);
        TestBean1 tst2 = context.getBean("testBean1", TestBean1.class);
        System.out.println(tst1);
        System.out.println(tst2);

        MyConnection myConnection1 = context.getBean( MyConnection.class);
        MyConnection myConnection2 = context.getBean(  MyConnection.class);
        System.out.println(myConnection1);
        System.out.println(myConnection2);

        context.registerShutdownHook();
    }
}