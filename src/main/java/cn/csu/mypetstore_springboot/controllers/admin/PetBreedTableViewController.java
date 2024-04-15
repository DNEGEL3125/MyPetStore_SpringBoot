package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.CategoryService;
import cn.csu.mypetstore_springboot.Services.PetBreedService;
import cn.csu.mypetstore_springboot.domain.Category;
import cn.csu.mypetstore_springboot.domain.PetBreed;
import cn.csu.mypetstore_springboot.enums.HeaderSelection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/petBreeds")
public class PetBreedTableViewController {
    final static int PRODUCTS_PER_PAGE = 8;
    private static final String PET_BREED_LIST_PAGE = "admin/PetBreedsView";
    private final CategoryService categoryService;
    private final PetBreedService petBreedService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public PetBreedTableViewController(CategoryService categoryService, PetBreedService petBreedService) {
        this.categoryService = categoryService;
        this.petBreedService = petBreedService;
    }

    @RequestMapping("/view")
    public String petBreedListView(Model model) {
        model.addAttribute("pageTitle", "Pet Breeds view");
        model.addAttribute("petBreedsList", new ArrayList<PetBreed>());
        model.addAttribute("headerSelection", HeaderSelection.Products);
        return PET_BREED_LIST_PAGE;
    }

    @RequestMapping("/update")
    public ResponseEntity<String> updatePetBreedsByIds(@RequestBody Map<String, PetBreed> changedAttrMap) {
        logger.info("Modified id: " + changedAttrMap.toString());
        return petBreedService.updatePetBreedsByIds(changedAttrMap);
    }

    @RequestMapping("/update/{petBreedId}/image")
    public ResponseEntity<String> updatePetBreedImage(@RequestParam("image") MultipartFile image, @PathVariable Long petBreedId) {
        return petBreedService.updatePetBreedImage(image, petBreedId);
    }

    @RequestMapping("/view/table/row/empty")
    public String getPetBreedTableEmptyRow(
            Model model
    ) {
        List<PetBreed> petBreeds = new ArrayList<>();
        List<Category> categories = categoryService.getCategories();
        petBreeds.add(new PetBreed());
        model.addAttribute("petBreedsList", petBreeds);
        model.addAttribute("categoriesList", categories);
        model.addAttribute("editable", true);

        return "admin/PetBreedTableRow";
    }

    @RequestMapping("/view/table")
    public String getPetBreedTable(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            Model model
    ) {
        List<PetBreed> petBreeds = petBreedService.getPetBreeds(pageNumber, PRODUCTS_PER_PAGE);
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("petBreedsList", petBreeds);
        model.addAttribute("categoriesList", categories);
        model.addAttribute("editable", true);

        return "admin/PetBreedTable";
    }

    @RequestMapping("/search/view/table")
    public String getSearchPetBreedTable(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "searchFor") String searchFor,
            Model model
    ) {
        List<PetBreed> petBreeds = petBreedService.searchPetBreeds(page, PRODUCTS_PER_PAGE, searchFor, keyword);
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("petBreedsList", petBreeds);
        model.addAttribute("categoriesList", categories);
        model.addAttribute("editable", true);
        return "admin/PetBreedTable";
    }

    @RequestMapping("/view/maxPageNumber")
    public ResponseEntity<Long> getMaxPageNumber() {
        Long maxPages = petBreedService.getMaxPageNumber(PRODUCTS_PER_PAGE);
        return ResponseEntity.ok(maxPages);
    }


    @RequestMapping("/search/view/maxPageNumber")
    public ResponseEntity<Long> getMaxPageNumber(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                                 @RequestParam(value = "searchFor") String searchFor) {
        Long maxPageNumber = petBreedService.getMaxPageNumber(PRODUCTS_PER_PAGE, keyword, searchFor);
        return ResponseEntity.ok(maxPageNumber);
    }

    @RequestMapping("/delete/{petBreedId}")
    public ResponseEntity<String> deletePetBreed(@PathVariable Long petBreedId) {
        return petBreedService.deletePetBreed(petBreedId);
    }
}
