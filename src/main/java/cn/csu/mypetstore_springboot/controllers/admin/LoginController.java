package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.VerificationCodeService;
import cn.csu.mypetstore_springboot.Services.admin.LoginService;
import cn.csu.mypetstore_springboot.domain.Admin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/login")
public class LoginController {
    private final VerificationCodeService verificationCodeService;
    private final LoginService loginService;


    public LoginController(VerificationCodeService verificationCodeService, LoginService loginService) {
        this.verificationCodeService = verificationCodeService;
        this.loginService = loginService;
    }

    @RequestMapping
    public ResponseEntity<String> login(String password, String username, String vCode, HttpSession session, HttpServletRequest request) {
        return loginService.login(username, password, vCode, request, session);
    }

    @RequestMapping("/form/view")
    public String logInView(Model model) {
        model.addAttribute("pageTitle", "LoginForm");
        return "admin/LoginForm";
    }

    @RequestMapping("/form/v-code")
    public ResponseEntity<byte[]> getVCode(HttpSession session) {
        // For demonstration purposes, let's assume you have generated a byte array representing the image
        String code = verificationCodeService.randGenerateCode();
        session.setAttribute("v-code", code);
        byte[] imageBytes = verificationCodeService.getImageBytes(code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set appropriate content type

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
