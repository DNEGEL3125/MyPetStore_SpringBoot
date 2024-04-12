package cn.csu.mypetstore_springboot.Services.admin;

import cn.csu.mypetstore_springboot.Repositories.AdminRepository;
import cn.csu.mypetstore_springboot.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class LoginService {
    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;

    private static Admin defaultAdmin = new Admin();

    public LoginService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
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

    public Admin login(String username, String password) {
        Admin adminByUsername = adminRepository.getAdminByUsername(username);
        if (adminByUsername == null) {
            return null;
        }
        boolean isPasswordMatch = verifyPassword(password, adminByUsername.getPassword());
        if (!isPasswordMatch) {
            return null;
        }
        return adminByUsername;
    }

    public boolean verifyPassword(String plaintextPassword, String hashedPassword) {
        if (plaintextPassword == null) {
            return false;
        }
        return passwordEncoder.matches(plaintextPassword, hashedPassword);
    }

}
