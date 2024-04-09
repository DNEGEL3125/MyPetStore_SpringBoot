package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.ProductRepository;
import cn.csu.mypetstore_springboot.Repositories.ProductRepositoryC;
import cn.csu.mypetstore_springboot.domain.Product;
import cn.csu.mypetstore_springboot.utils.CamelToSnakeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    private final static String AVAILABLE_SEARCH_FOR_REGEX = "[a-zA-Z0-9_.]+";
    private final ProductRepository productRepository;
    private final ProductRepositoryC productRepositoryC;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProductService(ProductRepository productRepository, ProductRepositoryC productRepositoryC) {
        this.productRepository = productRepository;
        this.productRepositoryC = productRepositoryC;
    }


    public List<Product> getProducts(Integer page, int limit) {
        if (page == null) {
            page = 1;
        }
        int offset = (page - 1) * limit;

        return productRepository.getProductsByLimitAndOffset(limit, offset);
    }


    public List<Product> searchProducts(Integer page, int limit, String searchFor, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return getProducts(page, limit);
        }

        if (page == null) {
            page = 1;
        }
        int offset = (page - 1) * limit;

        boolean isValid = searchFor.matches(AVAILABLE_SEARCH_FOR_REGEX);
        if (!isValid) {
            logger.info("searchFor='%s' in searchProducts doesn't match %s"
                    .formatted(searchFor, AVAILABLE_SEARCH_FOR_REGEX));
            return new ArrayList<>();
        }

        String colName = CamelToSnakeConverter.camelToSnake(searchFor);

        if (colName.startsWith("pet_breed.")) {
            colName = colName.substring("pet_breed.".length());
            return productRepositoryC.searchProductsByPetBreedContains(colName, keyword, limit, offset);
        }


        return productRepositoryC.searchProductsByContains(colName, keyword, limit, offset);
    }

    /**
     * @param changedAttrMap productId to attrName to changedVal
     * @return Update result
     */
    public ResponseEntity<String> updateProductsByIds(Map<String, Map<String, String>> changedAttrMap) {
        try {
            for (var productIdKeyEntry : changedAttrMap.entrySet()) {
                Long productId = Long.valueOf(productIdKeyEntry.getKey());
                for (var entry : productIdKeyEntry.getValue().entrySet()) {
                    String attr = entry.getKey();

                    Field attributeField = Product.class.getDeclaredField(attr);
                    attributeField.setAccessible(true); // Make the field accessible

                    Class<?> attributeType = attributeField.getType(); // Get the attribute type

                    Object newVal;
                    if (attributeType == String.class) {
                        newVal = entry.getValue();
                    } else if (attributeType == Integer.class || attributeType == int.class) {
                        newVal = Integer.parseInt(entry.getValue());
                    } else if (attributeType == Long.class || attributeType == long.class) {
                        newVal = Long.parseLong(entry.getValue());
                    } else {
                        throw new RuntimeException("Can't resolve the entry %s".formatted(entry));
                    }

                    Product productById = productRepository.getProductByProductId(productId);
                    // accountById[attr] = newVal;
                    attributeField.set(productById, newVal);

                    // Update the account
                    productRepository.save(productById);
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Products updated successfully");
    }

    public Long getMaxPageNumber(int limit) {
        return (productRepository.count() - 1) / limit + 1;
    }

    public Long getMaxPageNumber(int limit, String keyword, String searchFor) {
        if (keyword == null || keyword.isEmpty()) {
            return getMaxPageNumber(limit);
        }

        boolean isValid = searchFor.matches(AVAILABLE_SEARCH_FOR_REGEX);
        if (!isValid) {
            logger.info("searchFor='%s' in getMaxPageNumber doesn't match %s"
                    .formatted(searchFor, AVAILABLE_SEARCH_FOR_REGEX));
            return 1L;
        }

        String colName = CamelToSnakeConverter.camelToSnake(searchFor);

        Long recordsCount;

        if (colName.startsWith("pet_breed.")) {
            colName = colName.substring("pet_breed.".length());
            recordsCount = productRepositoryC.countProductsByPetBreedContains(colName, keyword);
        } else {
            recordsCount = productRepositoryC.countProductsByContains(colName, keyword);
        }


        return (recordsCount - 1) / limit + 1;
    }
}
