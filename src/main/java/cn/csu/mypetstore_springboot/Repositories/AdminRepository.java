package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Transactional
    @Query(value = "SELECT * FROM admin WHERE username = :username", nativeQuery = true)
    Admin getAdminByUsername(String username);

    boolean existsAdminByUsername(String username);
}
