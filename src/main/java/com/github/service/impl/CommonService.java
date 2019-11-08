package com.github.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("commonService")
public class CommonService {

    public void findAll(){
        System.out.println("findAll");
    }
}
