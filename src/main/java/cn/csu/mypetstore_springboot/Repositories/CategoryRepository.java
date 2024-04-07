package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getCategoryById(Long id);

    @Transactional
    @Query(value = "SELECT * FROM category LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Category> getCategoriesByLimitAndOffset(int limit, int offset);
}
