package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Category;
import cn.csu.mypetstore_springboot.domain.PetBreed;
import cn.csu.mypetstore_springboot.domain.Product;
import cn.csu.mypetstore_springboot.utils.DynamicSqlConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryC {
    private final JdbcTemplate jdbcTemplate;

    public final static RowMapper<Product> productRowMapper = BeanPropertyRowMapper.newInstance(Product.class);
    public final static RowMapper<PetBreed> petBreedRowMapper = BeanPropertyRowMapper.newInstance(PetBreed.class);
    public final static RowMapper<Category> categoryRowMapper = BeanPropertyRowMapper.newInstance(Category.class);


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
    public List<Product> searchProductsByContains(String colName, String keyword, int limit, int offset) throws NoSuchFieldException {
        String sql = """
                SELECT * FROM product WHERE %s LIKE ? LIMIT ? OFFSET ?;""";

        sql = DynamicSqlConstructor.constructMemberQuerySql(sql, colName, Product.class);

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Product product = productRowMapper.mapRow(rs, rowNum);
                    PetBreed petBreed = petBreedRowMapper.mapRow(rs, rowNum);
                    Category category = categoryRowMapper.mapRow(rs, rowNum);

                    assert product != null;
                    assert petBreed != null;
                    petBreed.setCategory(category);
                    product.setPetBreed(petBreed);


                    return product;
                },
                "%" + keyword + "%",
                limit,
                offset
        );
    }

    public Long countProductsByContains(String colName, String keyword) throws NoSuchFieldException {
        String sql = """
                SELECT COUNT(product_id) FROM product WHERE %s LIKE ?;""";
        sql = DynamicSqlConstructor.constructMemberQuerySql(sql, colName, Product.class);
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
