package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.LineItem;
import cn.csu.mypetstore_springboot.domain.PetBreed;
import cn.csu.mypetstore_springboot.domain.Product;
import cn.csu.mypetstore_springboot.utils.DynamicSqlConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LineItemRepositoryC {
    private final JdbcTemplate jdbcTemplate;

    public final static RowMapper<Product> productRowMapper = BeanPropertyRowMapper.newInstance(Product.class);
    public final static RowMapper<PetBreed> petBreedRowMapper = BeanPropertyRowMapper.newInstance(PetBreed.class);
    public final static RowMapper<LineItem> lineItemRowMapper = BeanPropertyRowMapper.newInstance(LineItem.class);


    public LineItemRepositoryC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * <b>!Important:</b> This function use `String.formatted` to insert the colName. Must avoid sql injection
     *
     * @param colName The col to search
     * @param keyword Search keyword
     * @return List of LineItem
     */
    public List<LineItem> searchLineItemsByContains(Long orderId, String colName, String keyword, int limit, int offset) throws NoSuchFieldException {

        String sql = """
                SELECT line_item.*, pet_breed.*, product.list_price, product.unit_cost FROM `line_item` WHERE order_id = ? AND %s LIKE ? LIMIT ? OFFSET ?;""";

        sql = DynamicSqlConstructor.constructMemberQuerySql(sql, colName, LineItem.class);

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    LineItem lineItem = lineItemRowMapper.mapRow(rs, rowNum);
                    Product product = productRowMapper.mapRow(rs, rowNum);

                    assert product != null;
                    product.setPetBreed(petBreedRowMapper.mapRow(rs, rowNum));
                    assert lineItem != null;
                    lineItem.setProduct(product);

                    return lineItem;

                },
                orderId,
                "%" + keyword + "%",
                limit,
                offset
        );
    }

    public List<LineItem> getLineItemBy(String colName, Object searchFor, int limit, int offset) {
        String sql = """
                SELECT * FROM `line_item` WHERE %s = %s LIMIT ? OFFSET ?;""";

        sql = sql.formatted(colName, searchFor);

        return jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(LineItem.class),
                limit,
                offset
        );
    }

    public Long countLineItemsByContains(Long orderId, String colName, String keyword) throws NoSuchFieldException {
        String sql = """
                SELECT COUNT(1) FROM `line_item` WHERE order_id = ? AND %s LIKE ?;""";
        sql = DynamicSqlConstructor.constructMemberQuerySql(sql, colName, LineItem.class);
        return jdbcTemplate.queryForObject(sql, Long.class, orderId, "%" + keyword + "%");
    }

    public Long countLineItemBy(String colName, Object keyword) {
        String sql = """
                SELECT COUNT(1) FROM `line_item` WHERE %s = ?;""";
        sql = sql.formatted(colName);
        return jdbcTemplate.queryForObject(sql, Long.class, keyword);
    }
}
