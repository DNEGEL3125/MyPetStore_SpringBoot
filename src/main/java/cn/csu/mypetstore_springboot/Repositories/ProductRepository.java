package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Available
 */
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> getProductsByCategoryId(String categoryId);

    Product getProductByProductId(String productId);

    @Transactional
    @Query(value = """
            select product_id, NAME, description, category_id
             from PRODUCT WHERE lower(name) like :keyword""", nativeQuery = true)
    List<Product> searchProductsByName(String keyword);
}
