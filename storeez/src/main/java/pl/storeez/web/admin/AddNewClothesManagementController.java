package pl.storeez.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.storeez.domain.categories.CategoryService;
import pl.storeez.domain.clothes.dto.ClothesSaveDto;
import pl.storeez.domain.subcategories.SubcategoryService;
import pl.storeez.domain.clothes.ClothesService;
import pl.storeez.domain.clothes.dto.ClothesDto;

@Controller
@RequestMapping("/admin")
public class AddNewClothesManagementController {
    private final ClothesService clothesService;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    public AddNewClothesManagementController(ClothesService clothesService, CategoryService categoryService, SubcategoryService subcategoryService) {
        this.clothesService = clothesService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/add-clothes")
    public String addClothesForm(Model model) {
        ClothesSaveDto clothes = new ClothesSaveDto();
        model.addAttribute("clothes", clothes);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("subcategories", subcategoryService.getAllSubcategories());
        return "admin/add-clothes-form";
    }

    @PostMapping("/add-clothes")
    public String addClothes(ClothesSaveDto clothesDto, RedirectAttributes redirectAttributes) {

        clothesService.addClothes(clothesDto);
        redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTR, "Item %s has been added".formatted(clothesDto.getName()));

        return "redirect:/admin";
    }
}
