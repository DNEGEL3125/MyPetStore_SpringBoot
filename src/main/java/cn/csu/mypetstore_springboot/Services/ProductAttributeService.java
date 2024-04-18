package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.ProductAttributeRepository;
import cn.csu.mypetstore_springboot.domain.ProductAttribute;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;

    public ProductAttributeService(ProductAttributeRepository productAttributeRepository) {
        this.productAttributeRepository = productAttributeRepository;
    }

    public List<ProductAttribute> getProductAttributesByProductId(Long productId) {
        return productAttributeRepository.getProductAttributesByProductId(productId);
    }

    public ResponseEntity<String> updateProductAttributes(Map<String, String> productAttrContentChangedMap) {
        for (var entry : productAttrContentChangedMap.entrySet()) {
            String newContent = entry.getValue();
            String entryKey = entry.getKey();

            // Create new attribute
            if (entryKey.contains("new")) {
                if (newContent == null || newContent.isEmpty()) {
                    continue;
                }
                String[] parts = entryKey.split("new");
                Long productId = Long.valueOf(parts[0]);
                ProductAttribute productAttribute = new ProductAttribute();
                productAttribute.setProductId(productId);
                productAttribute.setContent(newContent);

                productAttributeRepository.saveAndFlush(productAttribute);
                continue;
            }

            Long productAttributeId = Long.valueOf(entry.getKey());

            if (newContent == null || newContent.isEmpty()) {
                productAttributeRepository.deleteById(productAttributeId);
            } else {
                productAttributeRepository.updateProductAttributesById(productAttributeId, newContent);
            }
        }

        return ResponseEntity.ok("Attributes updated successfully");
    }
}
