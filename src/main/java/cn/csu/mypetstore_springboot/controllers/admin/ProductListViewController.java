package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.ProductService;
import cn.csu.mypetstore_springboot.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/products")
public class ProductListViewController {
    final static int PRODUCTS_PER_PAGE = 16;
    private static final String PRODUCT_LIST_PAGE = "admin/ProductsView";
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProductListViewController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/view")
    public String productListView(Model model) {
        model.addAttribute("pageTitle", "Products view");
        model.addAttribute("productsList", new ArrayList<Product>());
        return PRODUCT_LIST_PAGE;
    }

    @RequestMapping("/update")
    public ResponseEntity<String> updateProductsByIds(@RequestBody Map<String, Map<String, String>> changedAttrMap) {
        logger.info("Modified id: " + changedAttrMap.toString());
        return productService.updateProductsByIds(changedAttrMap);
    }

    @RequestMapping("/view/table")
    public String getProductTable(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            Model model
    ) {
        List<Product> products = productService.getProducts(pageNumber, PRODUCTS_PER_PAGE);
        model.addAttribute("productsList", products);

        return "/admin/ProductTable";
    }

    @RequestMapping("/search/view/table")
    public String getSearchProductTable(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "searchFor") String searchFor,
            Model model
    ) {
        List<Product> products = productService.searchProducts(page, PRODUCTS_PER_PAGE, searchFor, keyword);
        model.addAttribute("productsList", products);
        return "/admin/ProductTable";
    }

    @RequestMapping("/view/maxPageNumber")
    public ResponseEntity<Long> getMaxPageNumber() {
        Long maxPages = productService.getMaxPageNumber(PRODUCTS_PER_PAGE);
        return ResponseEntity.ok(maxPages);
    }


    @RequestMapping("search/view/maxPageNumber")
    public ResponseEntity<Long> getMaxPageNumber(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                                 @RequestParam(value = "searchFor") String searchFor) {
        Long maxPageNumber = productService.getMaxPageNumber(PRODUCTS_PER_PAGE, keyword, searchFor);
        return ResponseEntity.ok(maxPageNumber);
    }
}
