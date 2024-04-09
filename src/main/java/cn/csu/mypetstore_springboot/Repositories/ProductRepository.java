package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product getProductById(Long id);

    List<Product> getProductsByPetBreedId(Long petBreed_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE item SET quantity = :quantity WHERE id = :id", nativeQuery = true)
    void updateQuantityById(Long id, @Param("quantity") int quantity);
}
