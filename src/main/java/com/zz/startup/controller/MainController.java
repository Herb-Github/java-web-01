package com.zz.startup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("main")
    public String main(){
        return "admin-index-fixed";
    }

    @RequestMapping("login")
    public String login(){
        return "login";
    }
}
