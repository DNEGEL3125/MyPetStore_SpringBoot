package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.DTO.CategorySalesDTO;
import cn.csu.mypetstore_springboot.DTO.OrderCountDTO;
import cn.csu.mypetstore_springboot.DTO.TotalRevenueDTO;
import cn.csu.mypetstore_springboot.Services.AccountService;
import cn.csu.mypetstore_springboot.Services.CategoryService;
import cn.csu.mypetstore_springboot.Services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    private final OrderService orderService;
    private final AccountService accountService;
    private final CategoryService categoryService;

    public AdminDashboardController(OrderService orderService, AccountService accountService, CategoryService categoryService) {
        this.orderService = orderService;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @RequestMapping("/view")
    public String adminDashBoardView(Model model) {
        model.addAttribute("pageTitle", "Dashboard");
        return "admin/Dashboard";
    }

    @RequestMapping("/data/orderCount/{timeScale}")
    public ResponseEntity<List<OrderCountDTO>> orderCountData(@PathVariable String timeScale) {
        List<OrderCountDTO> orderCountData = orderService.getOrderCountData(timeScale);
        return ResponseEntity.ok(orderCountData);
    }

    @RequestMapping("/data/accountCount/{timeScale}")
    public ResponseEntity<List<OrderCountDTO>> accountCountData(@PathVariable String timeScale) {
        accountService.getAccountCountData(timeScale);
        return ResponseEntity.ok(new ArrayList<>());
    }

    @RequestMapping("/data/accountCount")
    public ResponseEntity<Long> accountCount() {
        Long accountCount = accountService.getAccountCount();
        return ResponseEntity.ok(accountCount);
    }

    @RequestMapping("/data/totalRevenue/{timeScale}")
    public ResponseEntity<List<TotalRevenueDTO>> totalRevenueData(@PathVariable String timeScale) {
        List<TotalRevenueDTO> totalRevenueData = orderService.getTotalRevenueData(timeScale);
        return ResponseEntity.ok(totalRevenueData);
    }

    @RequestMapping("/data/categorySales/{timeScale}")
    public ResponseEntity<List<CategorySalesDTO>> categorySalesData(@PathVariable String timeScale) {
        List<CategorySalesDTO> totalRevenueData = new ArrayList<>();
        return ResponseEntity.ok(totalRevenueData);
    }

}
