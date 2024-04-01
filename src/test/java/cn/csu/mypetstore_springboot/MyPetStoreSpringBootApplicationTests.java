package cn.csu.mypetstore_springboot;

import cn.csu.mypetstore_springboot.Services.AccountService;
import cn.csu.mypetstore_springboot.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class MyPetStoreSpringBootApplicationTests {


    private final AccountService accountService;


    MyPetStoreSpringBootApplicationTests(AccountService accountService) {
        this.accountService = accountService;
    }

    @Test
    void contextLoads() {

        Account root = accountService.accountRepository.getAccountByUsername("root");

    }

}
