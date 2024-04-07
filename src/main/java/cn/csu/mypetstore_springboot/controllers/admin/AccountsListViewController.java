package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.AccountService;
import cn.csu.mypetstore_springboot.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/accounts")
public class AccountsListViewController {
    final static int ACCOUNTS_PER_PAGE = 16;
    private static final String ACCOUNT_LIST_PAGE = "admin/AccountsView";
    private final AccountService accountService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AccountsListViewController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/view")
    public String accountListView(Model model) {
        model.addAttribute("pageTitle", "Accounts view");
        model.addAttribute("accountsList", new ArrayList<Account>());
        return ACCOUNT_LIST_PAGE;
    }

    @RequestMapping("/update")
    public ResponseEntity<String> updateAccountsByIds(@RequestBody Map<String, Map<String, String>> changedAttrMap) {
        logger.info("Modified id: " + changedAttrMap.toString());
        return accountService.updateAccountsByIds(changedAttrMap);
    }

    @RequestMapping("/view/table")
    public String getAccountTable(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            Model model
    ) {
        List<Account> accountsByPage = accountService.getAccounts(pageNumber, ACCOUNTS_PER_PAGE);
        model.addAttribute("accountsList", accountsByPage);

        return "/admin/AccountTable";
    }

    @RequestMapping("/search/view/table")
    public String getSearchAccountTable(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "searchFor") String searchFor,
            Model model
    ) {
        List<Account> accountsByPage = accountService.searchAccounts(page, ACCOUNTS_PER_PAGE, searchFor, keyword);
        model.addAttribute("accountsList", accountsByPage);
        return "/admin/AccountTable";
    }

    @RequestMapping("/view/maxPageNumber")
    public ResponseEntity<Long> getMaxPageNumber() {
        Long maxPages = accountService.getMaxPageNumber(ACCOUNTS_PER_PAGE);
        return ResponseEntity.ok(maxPages);
    }


    @RequestMapping("search/view/maxPageNumber")
    public ResponseEntity<Long> getMaxPageNumber(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                            @RequestParam(value = "searchFor") String searchFor) {
        Long maxPages = accountService.getMaxPageNumber(ACCOUNTS_PER_PAGE, keyword, searchFor);
        return ResponseEntity.ok(maxPages);
    }
}
