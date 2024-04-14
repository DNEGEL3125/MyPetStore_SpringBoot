package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.AdminRepository;
import cn.csu.mypetstore_springboot.domain.Admin;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> addAdmin(String username, String password) {
        if (username == null || username.length() < 1) {
            return ResponseEntity.badRequest().body("The username is too short.");
        }
        if (username.length() > 32) {
            return ResponseEntity.badRequest().body("The username is too long.");
        }
        if (password.length() < 6) {
            return ResponseEntity.badRequest().body("The password is too short.");
        }
        if (password.length() > 64) {
            return ResponseEntity.badRequest().body("The password is too long.");
        }

        boolean isUsernameExists = adminRepository.existsAdminByUsername(username);
        if (isUsernameExists) {
            return ResponseEntity.badRequest().body("The username already exists.");
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        adminRepository.save(admin);

        return ResponseEntity.ok("Successfully created administrator '%s'"
                .formatted(username));
    }
}
