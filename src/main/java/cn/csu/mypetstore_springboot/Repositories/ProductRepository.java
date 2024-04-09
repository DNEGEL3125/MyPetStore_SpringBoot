package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getProductByProductId(Long productId);

    List<Product> getProductsByPetBreedId(Long petBreed_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE product SET quantity = :quantity WHERE product_id = :productId", nativeQuery = true)
    void updateQuantityByProductId(Long productId, @Param("quantity") int quantity);

    @Transactional
    @Query(value = "SELECT * FROM product LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Product> getProductsByLimitAndOffset(int limit, int offset);
}
