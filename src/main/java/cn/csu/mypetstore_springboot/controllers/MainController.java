package cn.csu.mypetstore_springboot.controllers;

import cn.csu.mypetstore_springboot.Repositories.ProductRepository;
import cn.csu.mypetstore_springboot.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/main")
    public String doGet() {
        return "Catalog/Main";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
