package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin getAdminByUsername(String username);
}
