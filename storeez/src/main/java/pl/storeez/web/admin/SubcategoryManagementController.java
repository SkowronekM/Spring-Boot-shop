package pl.storeez.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.storeez.domain.categories.*;
import pl.storeez.domain.subcategories.dto.SubcategoryDto;
import pl.storeez.domain.clothes.ClothesService;
import pl.storeez.domain.subcategories.Subcategory;
import pl.storeez.domain.subcategories.SubcategoryRepository;
import pl.storeez.domain.subcategories.SubcategoryService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class SubcategoryManagementController {
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final SubcategoryRepository subcategoryRepository;
    private final ClothesService clothesService;

    public SubcategoryManagementController(CategoryService categoryService, SubcategoryService subcategoryService, SubcategoryRepository subcategoryRepository, ClothesService clothesService) {
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
        this.subcategoryRepository = subcategoryRepository;
        this.clothesService = clothesService;
    }

    @GetMapping("/add-subcategory")
    public String addSubcategoryForm(Model model) {
        model.addAttribute("subcategory", new SubcategoryDto());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "admin/add-subcategory-form";
    }

    @PostMapping("/add-subcategory")
    public String addSubcategory(SubcategoryDto subcategoryDto, RedirectAttributes redirectAttributes) {
        subcategoryService.addSubcategory(subcategoryDto);
        redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTR, "Subcategory %s has been added"
                .formatted(subcategoryDto.getName()));

        return "redirect:/admin/manage-subcategories";
    }

    @GetMapping("/manage-subcategories")
    public String manageSubcategoryTable(Model model) {
        List<Subcategory> subcategories = subcategoryRepository.findAllByOrderByIdAsc();
        model.addAttribute("subcategories", subcategories);

        return "admin/manage-subcategories";
    }

    @GetMapping("/edit-subcategory/{id}")
    public String editSubcategoryTable(@PathVariable Long id, Model model) {
        Subcategory subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        model.addAttribute("subcategory", subcategory);

        return "admin/edit-subcategory";
    }

    @PostMapping("/edit-subcategory")
    public String editSubcategory(@ModelAttribute Subcategory subcategory, RedirectAttributes redirectAttributes) {
        subcategoryRepository.save(subcategory);
        redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTR, "Subcategory name %s has been changed"
                .formatted(subcategory.getName()));

        return "redirect:/admin/manage-subcategories";
    }

    @GetMapping("/delete-subcategory/{id}")
    public String deleteSubcategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Subcategory subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        clothesService.deleteClothesBySubcategory(id);
        subcategoryRepository.delete(subcategory);
        redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTR, "Subcategory %s and all links have been deleted".formatted(subcategory.getName()));

        return "redirect:/admin/manage-subcategories";
    }

}
