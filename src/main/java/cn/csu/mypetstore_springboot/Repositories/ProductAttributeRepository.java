package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
    List<ProductAttribute> getProductAttributesByProductId(Long productId);
}
