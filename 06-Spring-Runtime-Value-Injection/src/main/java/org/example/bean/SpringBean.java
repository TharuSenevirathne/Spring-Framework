package org.example.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpringBean  implements InitializingBean {

//    @Autowired(required = false)
//    public SpringBean(@Value("Tharu Senevirathne") String name , @Value("21") int age) {
//        System.out.println("Name :" + name);
//        System.out.println("Age :" + age);
//    }
//
//    @Autowired(required = false)
//    public SpringBean(@Value("Senez") String name , @Value("34") int number,@Value("true") boolean value) {
//        System.out.println("Name :" + name);
//        System.out.println("Number :" + number);
//        System.out.println("Value :" + value);
//    }



/*-----------------------------------------------------------------------------------------------------------------------*/

    // ***Populate Property eka lifecycle eke


    // implement krgththa InitializingBean eka
    @Value("Tharu")
    private String name;

    @Value("gy##$$&&^**hug")
    private String key;

    public SpringBean() {
        System.out.println("Name :" + name); // methanin print wenne nh
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(name); // Obj eka hadila iwara wela thamai meka print wenne
        System.out.println(key);
    }
}
