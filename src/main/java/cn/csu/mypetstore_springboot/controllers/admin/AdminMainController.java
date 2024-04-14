package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Interceptors.RequestStopInterceptor;
import cn.csu.mypetstore_springboot.Services.AccountService;
import cn.csu.mypetstore_springboot.Services.OrderService;
import cn.csu.mypetstore_springboot.domain.Account;
import cn.csu.mypetstore_springboot.domain.Admin;
import cn.csu.mypetstore_springboot.domain.Order;
import cn.csu.mypetstore_springboot.enums.HeaderSelection;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminMainController {
    private final static int SMALL_TABLE_RECORDS = 5;
    private final RequestStopInterceptor requestStopInterceptor;
    private final OrderService orderService;
    private final AccountService accountService;

    public AdminMainController(RequestStopInterceptor requestStopInterceptor, OrderService orderService, AccountService accountService) {
        this.requestStopInterceptor = requestStopInterceptor;
        this.orderService = orderService;
        this.accountService = accountService;
    }

    @RequestMapping
    public String mainPage(Model model, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return "admin/LoginForm";
        }

        List<Order> orders = orderService.getOrders(1, SMALL_TABLE_RECORDS);
        List<Account> accounts = accountService.getAccounts(1, SMALL_TABLE_RECORDS);

        model.addAttribute("pageTitle", "Admin");
        model.addAttribute("admin", admin);
        model.addAttribute("isRequestsStopped", requestStopInterceptor.isStopped());
        model.addAttribute("headerSelection", HeaderSelection.Home);
        model.addAttribute("ordersList", orders);
        model.addAttribute("accountsList", accounts);
        return "admin/Main";
    }
}
