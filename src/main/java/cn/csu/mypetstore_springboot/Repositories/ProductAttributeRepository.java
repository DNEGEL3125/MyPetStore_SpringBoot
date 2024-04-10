package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    List<ProductAttribute> getProductAttributesByProductId(Long productId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE product_attribute SET content = :content WHERE id = :id", nativeQuery = true)
    void updateProductAttributesById(Long id, @Param("content") String content);
}
