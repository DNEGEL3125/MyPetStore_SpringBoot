package cn.csu.mypetstore_springboot.Services.admin;

import cn.csu.mypetstore_springboot.Repositories.AdminRepository;
import cn.csu.mypetstore_springboot.domain.Admin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminLoginService {
    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    private static Admin defaultAdmin = new Admin();


    public AdminLoginService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;

        Admin adminByUsername = adminRepository.getAdminByUsername("ADMIN");
        if (adminByUsername == null) {
            defaultAdmin.setPassword(passwordEncoder.encode("admin5"));
            defaultAdmin.setUsername("ADMIN");
            defaultAdmin = adminRepository.save(defaultAdmin);
        } else {
            defaultAdmin = adminByUsername;
        }
    }

    public ResponseEntity<String> login(String username, String password, String verificationCode, HttpServletRequest request, HttpSession session) {
        String ipAddress = request.getRemoteAddr();
        String rightVCode = (String) session.getAttribute("v-code");
        // 验证码错误
        if (!verificationCode.equals(rightVCode)) {
            log.warn(
                    "User with IP {} failed to log in using (username={}, password={}, verification code={}) because given verification code '{}' != '{}'",
                    ipAddress,
                    username,
                    password,
                    verificationCode,
                    verificationCode,
                    rightVCode);
            return ResponseEntity.badRequest().body("Wrong verification code");
        }

        // 根据username查询管理员
        Admin adminByUsername = adminRepository.getAdminByUsername(username);

        // 没有username对应的管理员
        if (adminByUsername == null) {
            log.warn(
                    "User with IP {} failed to log in using (username={}, password={}) because given username '{}' doesn't exist",
                    ipAddress,
                    username,
                    password,
                    password);
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }

        // 密码是否匹配
        boolean isPasswordMatch = verifyPassword(password, adminByUsername.getPassword());
        if (!isPasswordMatch) {
            log.warn(
                    "User with IP {} failed to log in using (username={}, password={}) because given password '{}' is wrong",
                    ipAddress,
                    username,
                    password,
                    password);
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }

        // 利用session记录登录的管理员
        session.setAttribute("admin", adminByUsername);
        log.info("{} has logged in", adminByUsername);

        return ResponseEntity.ok("Log in successfully!");
    }

    public boolean verifyPassword(String plaintextPassword, String hashedPassword) {
        if (plaintextPassword == null) {
            return false;
        }
        return passwordEncoder.matches(plaintextPassword, hashedPassword);
    }

}
