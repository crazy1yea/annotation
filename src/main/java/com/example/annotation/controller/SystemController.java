package com.example.annotation.controller;

import org.springframework.stereotype.Controller;

/**
 * 基础 交互
 * @author yangy
 */
@Controller
public class SystemController {
    /**
     * hello 页面
     * @return
     */
    public String toHello(){
        return "hello";
    }
}
