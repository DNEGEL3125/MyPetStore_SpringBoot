package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.PetBreed;
import cn.csu.mypetstore_springboot.utils.DynamicSqlConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PetBreedRepositoryC {
    private final JdbcTemplate jdbcTemplate;

    public PetBreedRepositoryC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * <b>!Important:</b> This function use `String.formatted` to insert the colName. Must avoid sql injection
     *
     * @param colName The col to search
     * @param keyword Search keyword
     * @return List of PetBreed
     */
    public List<PetBreed> searchPetBreedsByContains(String colName, String keyword, int limit, int offset) throws NoSuchFieldException {
        String sql = """
                SELECT * FROM pet_breed WHERE %s LIKE ? LIMIT ? OFFSET ?;""";
        sql = DynamicSqlConstructor.constructMemberSql(sql, colName, PetBreed.class);
        return jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(PetBreed.class),
                "%" + keyword + "%",
                limit,
                offset
        );
    }

    public Long countPetBreedsByContains(String colName, String keyword) throws NoSuchFieldException {
        String sql = """
                SELECT COUNT(1) FROM pet_breed WHERE %s LIKE ?;""";
        sql = DynamicSqlConstructor.constructMemberSql(sql, colName, PetBreed.class);
        return jdbcTemplate.queryForObject(sql, Long.class, "%" + keyword + "%");
    }
}
