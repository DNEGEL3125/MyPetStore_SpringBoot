package cn.csu.mypetstore_springboot.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController {
    @RequestMapping
    public String mainPage(Model model) {
        model.addAttribute("pageTitle", "Admin");
        return "admin/Main";
    }
}
