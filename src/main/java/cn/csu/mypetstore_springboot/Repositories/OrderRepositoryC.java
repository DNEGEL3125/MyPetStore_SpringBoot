package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepositoryC {
    private final JdbcTemplate jdbcTemplate;

    public final static RowMapper<Order> orderRowMapper = BeanPropertyRowMapper.newInstance(Order.class);


    public OrderRepositoryC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * <b>!Important:</b> This function use `String.formatted` to insert the colName. Must avoid sql injection
     *
     * @param colName The col to search
     * @param keyword Search keyword
     * @return List of Order
     */
    public List<Order> searchOrdersByContains(String colName, String keyword, int limit, int offset) {
        String sql = """
                SELECT * FROM `pet_order` WHERE %s LIKE ? LIMIT ? OFFSET ?;""";

        sql = sql.formatted(colName);

        return jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(Order.class),
                "%" + keyword + "%",
                limit,
                offset
        );
    }

    public List<Order> getOrderBy(String colName, Object searchFor, int limit, int offset) {
        String sql = """
                SELECT * FROM `pet_order` WHERE %s = %s LIMIT ? OFFSET ?;""";

        sql = sql.formatted(colName, searchFor);

        return jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(Order.class),
                limit,
                offset
        );
    }

    public Long countOrdersByContains(String colName, String keyword) {
        String sql = """
                SELECT COUNT(order_id) FROM `pet_order` WHERE %s LIKE ?;""";
        sql = sql.formatted(colName);
        return jdbcTemplate.queryForObject(sql, Long.class, "%" + keyword + "%");
    }

    public Long countOrderBy(String colName, Object keyword) {
        String sql = """
                SELECT COUNT(order_id) FROM `pet_order` WHERE %s = ?;""";
        sql = sql.formatted(colName);
        return jdbcTemplate.queryForObject(sql, Long.class, keyword);
    }
}
