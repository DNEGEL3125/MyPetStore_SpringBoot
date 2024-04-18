package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.ProductRepository;
import cn.csu.mypetstore_springboot.Repositories.ProductRepositoryC;
import cn.csu.mypetstore_springboot.domain.Product;
import cn.csu.mypetstore_springboot.utils.CamelToSnakeConverter;
import cn.csu.mypetstore_springboot.utils.ObjectFieldCopier;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProductService {
    private final static String AVAILABLE_SEARCH_FOR_REGEX = "[a-zA-Z0-9_.]+";
    private final ProductRepository productRepository;
    private final ProductRepositoryC productRepositoryC;


//    Logger logger = LoggerFactory.getLogger(this.getClass());

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
            log.info("searchFor='%s' in searchProducts doesn't match %s"
                    .formatted(searchFor, AVAILABLE_SEARCH_FOR_REGEX));
            return new ArrayList<>();
        }


        try {
            return productRepositoryC.searchProductsByContains(searchFor, keyword, limit, offset);
        } catch (NoSuchFieldException e) {
            log.error(e.toString());
            return new ArrayList<>();
        }
    }

    /**
     * for i in range(fields.len){
     * if(a.fields[i] == null){
     * b.fields[i] = a.fields[i];
     * }
     * }
     *
     * @param changedAttrMap productId to attrName to changedVal
     * @return Update result
     */
    public ResponseEntity<String> updateProductsByIds(Map<String, Product> changedAttrMap) {
        try {
            for (var productIdKeyEntry : changedAttrMap.entrySet()) {
                String entryKey = productIdKeyEntry.getKey();
                Product changedAttrs = productIdKeyEntry.getValue();

                // New product
                if (entryKey.contains("new")) {
                    productRepository.saveAndFlush(changedAttrs);
                    continue;
                }

                long productId = Long.parseLong(entryKey);

                // New product
                if (productId < 0) {
                    productRepository.save(changedAttrs);
                    continue;
                }

                Product product = productRepository.getProductByProductId(productId);

                ObjectFieldCopier.copyFieldsIfNotNullRecursively(changedAttrs, product);

                // Update the account
                productRepository.save(product);
            }
        } catch (Exception e) {
            log.error(e.toString());
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
            log.info("searchFor='%s' in getMaxPageNumber doesn't match %s"
                    .formatted(searchFor, AVAILABLE_SEARCH_FOR_REGEX));
            return 1L;
        }

        String colName = CamelToSnakeConverter.camelToSnake(searchFor);

        Long recordsCount = 1L;
        try {
            recordsCount = productRepositoryC.countProductsByContains(searchFor, keyword);
        } catch (NoSuchFieldException e) {
            log.error("getMaxPageNumber " + e.getMessage());
        }

//        if (colName.startsWith("pet_breed.")) {
//            colName = colName.substring("pet_breed.".length());
//            recordsCount = productRepositoryC.countProductsByPetBreedContains(colName, keyword);
//        } else {
//            recordsCount = productRepositoryC.countProductsByContains(colName, keyword);
//        }


        return (recordsCount - 1) / limit + 1;
    }

    public ResponseEntity<String> deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return ResponseEntity.ok("The product has been successfully deleted");
    }
}
