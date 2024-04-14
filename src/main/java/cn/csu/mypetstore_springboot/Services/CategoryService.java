package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.DTO.CategorySalesDTO;
import cn.csu.mypetstore_springboot.DTO.OrderCountDTO;
import cn.csu.mypetstore_springboot.DTO.TotalRevenueDTO;
import cn.csu.mypetstore_springboot.Repositories.CategoryRepository;
import cn.csu.mypetstore_springboot.domain.Account;
import cn.csu.mypetstore_springboot.domain.Category;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(Integer page, int limit) {
        if (page == null) {
            page = 1;
        }
        int offset = (page - 1) * limit;

        return categoryRepository.getCategoriesByLimitAndOffset(limit, offset);
    }

    public List<CategorySalesDTO> getCategorySalesData(String timeScale) {
        List<CategorySalesDTO> categorySalesData = new ArrayList<>();
        LocalDate dateNow = LocalDate.now();
        int currentYear = dateNow.getYear();

        if (timeScale == null) {
            return categoryRepository.getCategorySalesData();
        }

        if (timeScale.toLowerCase().equals("year")) {//                categorySalesData = categoryRepository.getCategorySalesData();
        }

        return categorySalesData;
    }

}
