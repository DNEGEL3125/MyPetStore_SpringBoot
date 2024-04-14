package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/add")
public class AddAdminController {
    private final AdminService adminService;

    public AddAdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping("/modal")
    public String addAdminModal() {
        return "admin/AddAdminModal";
    }

    @RequestMapping
    public ResponseEntity<String> addAdmin(@RequestParam String username, @RequestParam String password) {
        return adminService.addAdmin(username, password);
    }
}
