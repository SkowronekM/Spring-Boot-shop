package pl.storeez.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.storeez.domain.categories.CategoryService;
import pl.storeez.domain.categories.dto.CategoryDto;

@Controller
@RequestMapping("/admin")
public class CategoryManagementController {
    private final CategoryService categoryService;

    public CategoryManagementController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/add-category")
    public String addCategoryForm(Model model) {
        CategoryDto category = new CategoryDto();
        model.addAttribute("category", category);

        return "admin/add-category-form";
    }

    @PostMapping("/add-category")
    public String addCategory(CategoryDto category, RedirectAttributes redirectAttributes) {
        categoryService.addCategory(category);
        redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTR, "Category %s has been added".formatted(category.getName()));

        return "redirect:/admin";
    }
}
