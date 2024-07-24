package pl.storeez.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.storeez.domain.categories.*;
import pl.storeez.domain.categories.dto.CategoryDto;
import pl.storeez.domain.clothes.ClothesService;
import pl.storeez.domain.subcategories.Subcategory;
import pl.storeez.domain.subcategories.SubcategoryRepository;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryManagementController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ClothesService clothesService;

    public CategoryManagementController(CategoryService categoryService, CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository, ClothesService clothesService) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.clothesService = clothesService;
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

        return "redirect:/admin/manage-categories";
    }

    @GetMapping("/manage-categories")
    public String manageCategoryTable(Model model) {
        Iterable<Category> categories = categoryRepository.findAllByOrderByIdAsc();
        model.addAttribute("categories", categories);

        return "admin/manage-categories";
    }

    @GetMapping("/edit-category/{id}")
    public String editCategoryTable(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("category", category);

        return "admin/edit-category";
    }

    @PostMapping("/edit-category")
    public String editCategory(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTR, "Category name %s has been changed".formatted(category.getName()));

        return "redirect:/admin/manage-categories";
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        List<Subcategory> subcategories = subcategoryRepository.findByCategoryId(id);
        for (Subcategory subcategory : subcategories) {
            clothesService.deleteClothesBySubcategory(subcategory.getId());
            subcategoryRepository.delete(subcategory);
        }

        categoryRepository.delete(category);
        redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTR, "Category %s and all links have been deleted".formatted(category.getName()));

        return "redirect:/admin/manage-categories";
    }
}
