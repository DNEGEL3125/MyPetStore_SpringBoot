package cn.csu.mypetstore_springboot.controllers;

import cn.csu.mypetstore_springboot.Services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping("/main")
    public String doGet() {
        return "Catalog/Main";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
