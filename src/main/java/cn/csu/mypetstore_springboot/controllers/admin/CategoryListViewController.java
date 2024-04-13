package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.CategoryService;
import cn.csu.mypetstore_springboot.domain.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryListViewController {
    private final static int CATEGORIES_PER_PAGE = 16;

    private final CategoryService categoryService;

    private final static String CATEGORY_LIST_PAGE = "admin/CategoriesView";

    public CategoryListViewController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/view")
    public String categoryListView(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            Model model) {
        List<Category> categories = categoryService.getCategories(page, CATEGORIES_PER_PAGE);
        model.addAttribute("pageTitle", "Categories view");
        model.addAttribute("categoriesList", categories);
        return CATEGORY_LIST_PAGE;
    }
}
