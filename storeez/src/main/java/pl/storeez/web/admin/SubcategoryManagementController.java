package pl.storeez.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.storeez.domain.categories.Category;
import pl.storeez.domain.categories.CategoryService;
import pl.storeez.domain.categories.SubcategoryService;
import pl.storeez.domain.categories.dto.SubcategoryDto;

@Controller
@RequestMapping("/admin")
public class SubcategoryManagementController {
    private CategoryService categoryService;
    private SubcategoryService subcategoryService;

    public SubcategoryManagementController(CategoryService categoryService, SubcategoryService subcategoryService) {
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/add-subcategory")
    public String addSubcategoryForm(Model model) {
        model.addAttribute("subcategory", new SubcategoryDto());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "admin/add-subcategory-form";
    }

    @PostMapping("/add-subcategory")
    public String addSubcategory(SubcategoryDto subcategoryDto, Category category, Model model, RedirectAttributes redirectAttributes) {
        subcategoryService.addSubcategory(subcategoryDto);
        redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTR, "Subcategory %s has been added"
                .formatted(subcategoryDto.getName()));

        return "redirect:/admin";

    }


}
