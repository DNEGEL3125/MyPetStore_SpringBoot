package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.AccountRepository;
import cn.csu.mypetstore_springboot.Repositories.AccountRepositoryC;
import cn.csu.mypetstore_springboot.domain.Account;
import cn.csu.mypetstore_springboot.utils.CamelToSnakeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    private final static String ONLY_NUMBER_AND_ALPHA_REGEX = "[a-zA-Z0-9_]+";
    private final AccountRepository accountRepository;
    private final AccountRepositoryC accountRepositoryC;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public AccountService(AccountRepository accountRepository, AccountRepositoryC accountRepositoryC) {
        this.accountRepository = accountRepository;
        this.accountRepositoryC = accountRepositoryC;
    }

    public List<Account> getAccounts(Integer page, int limit) {
        if (page == null) {
            page = 1;
        }
        int offset = (page - 1) * limit;

        return accountRepository.getAccountsByLimitAndOffset(limit, offset);
    }


    public List<Account> searchAccounts(Integer page, int limit, String searchFor, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return getAccounts(page, limit);
        }

        if (page == null) {
            page = 1;
        }
        int offset = (page - 1) * limit;

        boolean isValid = searchFor.matches(ONLY_NUMBER_AND_ALPHA_REGEX);
        if (!isValid) {
            logger.info("searchFor='%s' in searchAccounts doesn't match %s"
                    .formatted(searchFor, ONLY_NUMBER_AND_ALPHA_REGEX));
            return new ArrayList<>();
        }

        String colName = CamelToSnakeConverter.camelToSnake(searchFor);

        return accountRepositoryC.searchAccountsByContains(colName, keyword, limit, offset);
    }

    /**
     * @param changedAttrMap accountId to attrName to changedVal
     * @return Update result
     */
    public ResponseEntity<String> updateAccountsByIds(Map<String, Map<String, String>> changedAttrMap) {
        try {
            for (var accountIdKeyEntry : changedAttrMap.entrySet()) {
                Long accountId = Long.valueOf(accountIdKeyEntry.getKey());
                for (var entry : accountIdKeyEntry.getValue().entrySet()) {
                    String attr = entry.getKey();

                    Field attributeField = Account.class.getDeclaredField(attr);
                    attributeField.setAccessible(true); // Make the field accessible

                    Class<?> attributeType = attributeField.getType(); // Get the attribute type

                    Object newVal;
                    if (attributeType == String.class) {
                        newVal = entry.getValue();
                    } else if (attributeType == Integer.class) {
                        newVal = Integer.parseInt(entry.getValue());
                    } else if (attributeType == Long.class) {
                        newVal = Long.parseLong(entry.getValue());
                    } else {
                        throw new RuntimeException("Can't resolve the entry %s".formatted(entry));
                    }

                    Account accountById = accountRepository.getAccountById(accountId);
                    // accountById[attr] = newVal;
                    attributeField.set(accountById, newVal);

                    // Update the account
                    accountRepository.save(accountById);
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Accounts updated successfully");
    }

    public Long getMaxPageNumber(int limit) {
        return (accountRepository.count() - 1) / limit + 1;
    }

    public Long getMaxPageNumber(int limit, String keyword, String searchFor) {
        if (keyword == null || keyword.isEmpty()) {
            return getMaxPageNumber(limit);
        }

        boolean isValid = searchFor.matches(ONLY_NUMBER_AND_ALPHA_REGEX);
        if (!isValid) {
            logger.info("searchFor='%s' in searchAccounts doesn't match %s"
                    .formatted(searchFor, ONLY_NUMBER_AND_ALPHA_REGEX));
            return 1L;
        }

        String colName = CamelToSnakeConverter.camelToSnake(searchFor);

        Long countedAccountsByContains = accountRepositoryC.countAccountsByContains(colName, keyword);
        return (countedAccountsByContains - 1) / limit + 1;
    }

    public static void main(String[] args) {
    }
}
