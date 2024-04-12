package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Interceptors.RequestStopInterceptor;
import cn.csu.mypetstore_springboot.domain.Admin;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController {
    private final RequestStopInterceptor requestStopInterceptor;

    public AdminMainController(RequestStopInterceptor requestStopInterceptor) {
        this.requestStopInterceptor = requestStopInterceptor;
    }

    @RequestMapping
    public String mainPage(Model model, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return "admin/LoginForm";
        }
        model.addAttribute("pageTitle", "Admin");
        model.addAttribute("admin", admin);
        model.addAttribute("isRequestsStopped", requestStopInterceptor.isStopped());
        return "admin/Main";
    }
}
