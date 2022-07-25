package com.chen.space.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhao
 * @Date 2022/7/22 17:46
 */
@RestController
@RequestMapping("/test")
public class testController {

    @RequestMapping("/test1")
    public String test(){
        return "success";
    }
}
