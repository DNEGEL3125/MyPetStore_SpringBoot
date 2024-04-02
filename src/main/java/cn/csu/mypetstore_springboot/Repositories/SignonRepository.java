package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Account;
import cn.csu.mypetstore_springboot.domain.Signon;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Available
 */
@Repository
public interface SignonRepository extends JpaRepository<Signon, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE SIGNON SET PASSWORD = :password WHERE USERNAME = :username", nativeQuery = true)
    void updatePasswordByUsername(@Param("username") String username, @Param("password") String password);
}
