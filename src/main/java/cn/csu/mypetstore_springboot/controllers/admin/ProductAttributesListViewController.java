package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.ProductAttributeService;
import cn.csu.mypetstore_springboot.domain.ProductAttribute;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/product-attributes")
public class ProductAttributesListViewController {
    private final ProductAttributeService productAttributeService;

    public ProductAttributesListViewController(ProductAttributeService productAttributeService) {
        this.productAttributeService = productAttributeService;
    }

    @RequestMapping("/productId/{productId}")
    public ResponseEntity<List<ProductAttribute>> getProductAttributesByProductId(@PathVariable Long productId) {
        List<ProductAttribute> productAttributesByProductId = productAttributeService.getProductAttributesByProductId(productId);
        return ResponseEntity.ok(productAttributesByProductId);
    }

    @RequestMapping("/view/list/productId/{productId}")
    public String getProductAttributeList(@PathVariable Long productId, Model model) {
        List<ProductAttribute> productAttributesByProductId = productAttributeService.getProductAttributesByProductId(productId);
        model.addAttribute("attributesList", productAttributesByProductId);
        return "admin/ProductAttributeList";
    }

    @RequestMapping("/update")
    public ResponseEntity<String> updateProductAttributes(@RequestBody Map<String, String> productAttrContentChangedMap) {
        return productAttributeService.updateProductAttributes(productAttrContentChangedMap);
    }
}
