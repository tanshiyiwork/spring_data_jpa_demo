package com.github.test;

import com.github.service.impl.CommonService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {

    @Test
    public void test1(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        CommonService commonService = (CommonService)applicationContext.getBean("commonService");
        commonService.findAll();
    }
}
