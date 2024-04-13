package cn.csu.mypetstore_springboot;

import cn.csu.mypetstore_springboot.Services.AccountService;
import cn.csu.mypetstore_springboot.domain.Account;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class MyPetStoreSpringBootApplicationTests {


    @Autowired
    private AccountService accountService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    void contextLoads() {
        Long accountCount = accountService.getAccountCount();
        logger.info(accountCount.toString());
    }

}
