package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.AccountRepository;
import cn.csu.mypetstore_springboot.Repositories.SignonRepository;
import cn.csu.mypetstore_springboot.domain.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    public AccountRepository accountRepository;
    private final SignonRepository signonRepository;

    public AccountService(AccountRepository accountRepository, SignonRepository signonRepository) {
        this.accountRepository = accountRepository;
        this.signonRepository = signonRepository;
    }


}
