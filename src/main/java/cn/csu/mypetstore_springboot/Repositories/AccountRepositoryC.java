package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryC {
    private final JdbcTemplate jdbcTemplate;

    public AccountRepositoryC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * <b>!Important:</b> This function use `String.formatted` to insert the colName. Must avoid sql injection
     *
     * @param colName The col to search
     * @param keyword Search keyword
     * @return List of Account
     */
    public List<Account> searchAccountsByContains(String colName, String keyword, int limit, int offset) {
        String sql = """
                SELECT * FROM account WHERE %s LIKE ? LIMIT ? OFFSET ?;""";
        sql = sql.formatted(colName);
        return jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(Account.class),
                "%" + keyword + "%",
                limit,
                offset
        );
    }

    public Long countAccountsByContains(String colName, String keyword) {
        String sql = """
                SELECT COUNT(id) FROM account WHERE %s LIKE ? ;""";
        sql = sql.formatted(colName);
        return jdbcTemplate.queryForObject(sql, Long.class, "%" + keyword + "%");
    }
}
