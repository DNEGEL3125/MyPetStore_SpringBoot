package cn.csu.mypetstore_springboot.Repositories;


import cn.csu.mypetstore_springboot.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getAccountByUsername(String username);


    @Transactional
    @Modifying
    @Query(value = "UPDATE ACCOUNT SET" +
            "      EMAIL = #{#account.email}," +
            "      FIRST_NAME = #{#account.firstName}," +
            "      LAST_NAME = #{#account.lastName}," +
            "      STATUS = #{#account.status}," +
            "      address1 = #{#account.address1}," +
            "      address2 = #{#account.address2}," +
            "      CITY = #{#account.city}," +
            "      STATE = #{#account.state}," +
            "      ZIP = #{#account.zip}," +
            "      COUNTRY = #{#account.country}," +
            "      PHONE = #{#account.phone}" +
            "    WHERE USERID = :username", nativeQuery = true)
    void updateAccountByUsername(String username, Account account);
}
