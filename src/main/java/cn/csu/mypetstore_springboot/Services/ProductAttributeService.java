package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.ProductAttributeRepository;
import cn.csu.mypetstore_springboot.domain.ProductAttribute;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;

    public ProductAttributeService(ProductAttributeRepository productAttributeRepository) {
        this.productAttributeRepository = productAttributeRepository;
    }

    public List<ProductAttribute> getProductAttributesByProductId(Long productId) {
        return productAttributeRepository.getProductAttributesByProductId(productId);
    }
}
