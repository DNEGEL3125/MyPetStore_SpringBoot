package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.DTO.CategorySalesDTO;
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


    @Query("""
            SELECT NEW cn.csu.mypetstore_springboot.DTO.CategorySalesDTO(
            c.categoryName,
            SUM(o.totalPrice)
            ) FROM Order o
            INNER JOIN LineItem li ON o.orderId = li.orderId
            INNER JOIN Product product ON li.productId = product.productId
             INNER JOIN PetBreed pb ON product.petBreedId = pb.id
              INNER JOIN Category c ON pb.categoryId = c.id
              GROUP BY c.id""")
    List<CategorySalesDTO> getCategorySalesData();
}
