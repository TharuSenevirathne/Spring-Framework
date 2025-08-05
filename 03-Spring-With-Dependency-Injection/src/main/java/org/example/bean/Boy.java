package org.example.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Boy {

    //    @Autowired // runtime girl kiyn dependency eka inject krgththa
    //    Girl girl;

    @Autowired
    @Qualifier("girl2")
    Agreement girl;

    public Boy() {
        System.out.println("Boy constructor");
    }

    public void chatWithGirl() {
        girl.chat();
    }
}
// Container eke thina object ekak  instance ekak widihata gnn puluwan =  @Autowired