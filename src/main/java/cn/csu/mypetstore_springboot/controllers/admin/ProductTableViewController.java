package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.PetBreedService;
import cn.csu.mypetstore_springboot.Services.ProductService;
import cn.csu.mypetstore_springboot.domain.PetBreed;
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

// TODO 添加和删除Product的功能
// TODO 显示并能够修改Product的attributes
@Controller
@RequestMapping("/admin/products")
public class ProductTableViewController {
    final static int PRODUCTS_PER_PAGE = 16;
    private static final String PRODUCT_LIST_PAGE = "admin/ProductsView";
    private final ProductService productService;
    private final PetBreedService petBreedService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProductTableViewController(ProductService productService, PetBreedService petBreedService) {
        this.productService = productService;
        this.petBreedService = petBreedService;
    }

    @RequestMapping("/view")
    public String productListView(Model model) {
        model.addAttribute("pageTitle", "Products view");
        model.addAttribute("productsList", new ArrayList<Product>());
        return PRODUCT_LIST_PAGE;
    }

    @RequestMapping("/update")
    public ResponseEntity<String> updateProductsByIds(@RequestBody Map<String, Product> changedAttrMap) {
        logger.info("Modified id: " + changedAttrMap.toString());
        return productService.updateProductsByIds(changedAttrMap);
    }

    @RequestMapping("/view/table/row/empty")
    public String getProductTableEmptyRow(
            Model model
    ) {
        List<Product> products = new ArrayList<>();
        List<PetBreed> petBreeds = petBreedService.getPetBreeds();
        products.add(new Product());
        model.addAttribute("productsList", products);
        model.addAttribute("petBreedsList", petBreeds);

        return "admin/ProductTableRow";
    }

    @RequestMapping("/view/table")
    public String getProductTable(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            Model model
    ) {
        List<Product> products = productService.getProducts(pageNumber, PRODUCTS_PER_PAGE);
        List<PetBreed> petBreeds = petBreedService.getPetBreeds();
        model.addAttribute("productsList", products);
        model.addAttribute("petBreedsList", petBreeds);

        return "admin/ProductTable";
    }

    @RequestMapping("/search/view/table")
    public String getSearchProductTable(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "searchFor") String searchFor,
            Model model
    ) {
        List<Product> products = productService.searchProducts(page, PRODUCTS_PER_PAGE, searchFor, keyword);
        List<PetBreed> petBreeds = petBreedService.getPetBreeds();
        model.addAttribute("productsList", products);
        model.addAttribute("petBreedsList", petBreeds);
        return "admin/ProductTable";
    }

    @RequestMapping("/view/maxPageNumber")
    public ResponseEntity<Long> getMaxPageNumber() {
        Long maxPages = productService.getMaxPageNumber(PRODUCTS_PER_PAGE);
        return ResponseEntity.ok(maxPages);
    }


    @RequestMapping("/search/view/maxPageNumber")
    public ResponseEntity<Long> getMaxPageNumber(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                                 @RequestParam(value = "searchFor") String searchFor) {
        Long maxPageNumber = productService.getMaxPageNumber(PRODUCTS_PER_PAGE, keyword, searchFor);
        return ResponseEntity.ok(maxPageNumber);
    }
}
