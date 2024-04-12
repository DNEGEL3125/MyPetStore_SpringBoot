package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.PetBreed;
import cn.csu.mypetstore_springboot.domain.Product;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryC {
    private final JdbcTemplate jdbcTemplate;

    public final static RowMapper<Product> productRowMapper = BeanPropertyRowMapper.newInstance(Product.class);
    public final static RowMapper<PetBreed> petBreedRowMapper = BeanPropertyRowMapper.newInstance(PetBreed.class);


    public ProductRepositoryC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * <b>!Important:</b> This function use `String.formatted` to insert the colName. Must avoid sql injection
     *
     * @param colName The col to search
     * @param keyword Search keyword
     * @return List of Product
     */
    public List<Product> searchProductsByContains(String colName, String keyword, int limit, int offset) {
        String sql = """
                SELECT * FROM product WHERE %s LIKE ? LIMIT ? OFFSET ?;""";

        sql = sql.formatted(colName);

        return jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(Product.class),
                "%" + keyword + "%",
                limit,
                offset
        );
    }

    public Long countProductsByContains(String colName, String keyword) {
        String sql = """
                SELECT COUNT(product_id) FROM product WHERE %s LIKE ? LIMIT ? OFFSET ?;""";
        sql = sql.formatted(colName);
        return jdbcTemplate.queryForObject(sql, Long.class, "%" + keyword + "%");
    }

    public List<Product> searchProductsByPetBreedContains(String colName, String keyword, int limit, int offset) {
        String sql = """
                SELECT product.*, pet_breed.* FROM product
                INNER JOIN pet_breed ON product.pet_breed_id = pet_breed.id
                WHERE pet_breed.%s LIKE ? LIMIT ? OFFSET ?;""";
        sql = sql.formatted(colName);
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Product product = productRowMapper.mapRow(rs, rowNum);

                    assert product != null;
                    product.setPetBreed(petBreedRowMapper.mapRow(rs, rowNum));

                    return product;

                },
                "%" + keyword + "%",
                limit,
                offset
        );
    }

    public Long countProductsByPetBreedContains(String colName, String keyword) {
        String sql = """
                SELECT COUNT(product.product_id) FROM product
                INNER JOIN pet_breed ON product.pet_breed_id = pet_breed.id
                WHERE pet_breed.%s LIKE ?;""";
        sql = sql.formatted(colName);
        return jdbcTemplate.queryForObject(sql, Long.class, "%" + keyword + "%");
    }
}
