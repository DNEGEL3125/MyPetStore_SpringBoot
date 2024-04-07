package cn.csu.mypetstore_springboot.controllers.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
public class RegisterController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/register")
    public String register() {
        logger.info("Someone trying to register");
        return "admin/RegisterForm";
    }

    @RequestMapping("/register/form")
    public String registerForm() {
        return "admin/RegisterForm";
    }

    @RequestMapping("/register/v-code")
    public byte[] getVerificationCode() {
        return new byte[0];
    }
}
