package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Interceptors.RequestStopInterceptor;
import cn.csu.mypetstore_springboot.Services.admin.AdminLoginService;
import cn.csu.mypetstore_springboot.domain.Admin;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

    private final RequestStopInterceptor requestStopInterceptor;
    private final AdminLoginService adminLoginService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ServerController(RequestStopInterceptor requestStopInterceptor, AdminLoginService adminLoginService) {
        this.requestStopInterceptor = requestStopInterceptor;
        this.adminLoginService = adminLoginService;
    }

    @PostMapping("/admin/stopRequests")
    public ResponseEntity<String> stopRequests(String password, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            return ResponseEntity.badRequest().body("Not login");
        } else if (!adminLoginService.verifyPassword(password, admin.getPassword())) {
            return ResponseEntity.badRequest().body("Password is wrong");
        }
        logger.info("`%s` pause the server".formatted(admin));
        requestStopInterceptor.stopRequests();

        return ResponseEntity.ok("Success stop requests");
    }

    @PostMapping("/admin/startRequests")
    public void startRequests() {
        requestStopInterceptor.startRequests();
    }
}
