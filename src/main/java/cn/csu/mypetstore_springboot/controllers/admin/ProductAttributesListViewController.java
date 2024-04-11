package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.ProductAttributeService;
import cn.csu.mypetstore_springboot.domain.ProductAttribute;
import cn.csu.mypetstore_springboot.utils.BasicTypeHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO Delete product
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

    @RequestMapping("/view/list/empty")
    public String getProductAttributeList(Model model) {
        model.addAttribute("attributesList", new ArrayList<ProductAttribute>());
        return "admin/ProductAttributeList";
    }


    @RequestMapping("/view/list/productId/{productId}")
    public String getProductAttributeList(@PathVariable String productId, Model model) {
        List<ProductAttribute> productAttributes = new ArrayList<>();
        if (BasicTypeHelper.isUnsignedNumber(productId))
            productAttributes = productAttributeService
                    .getProductAttributesByProductId(Long.valueOf(productId));
        model.addAttribute("attributesList", productAttributes);
        model.addAttribute("productId", productId);
        return "admin/ProductAttributeList";
    }

    @RequestMapping("/update")
    public ResponseEntity<String> updateProductAttributes(@RequestBody Map<String, String> productAttrContentChangedMap) {
        return productAttributeService.updateProductAttributes(productAttrContentChangedMap);
    }
}
