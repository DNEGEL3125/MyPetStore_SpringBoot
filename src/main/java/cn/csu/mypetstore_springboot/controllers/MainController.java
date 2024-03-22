package cn.csu.mypetstore_springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private static final String MAIN = "jsp/catalog/Main.html";

    @GetMapping("/main")
    public String doGet() {
        return "Catalog/Main";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
