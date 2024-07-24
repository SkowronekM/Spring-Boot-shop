package pl.storeez.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import pl.storeez.domain.categories.CategoryService;
import pl.storeez.domain.subcategories.SubcategoryService;
import pl.storeez.domain.categories.dto.CategoryDto;
import pl.storeez.domain.subcategories.dto.SubcategoryDto;
import pl.storeez.domain.clothes.ClothesService;
import pl.storeez.domain.clothes.dto.ClothesDto;

import java.util.List;

@Controller
public class CategoryController {
    private final ClothesService clothesService;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    public CategoryController(ClothesService clothesService, CategoryService categoryService, SubcategoryService subcategoryService) {
        this.clothesService = clothesService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }

    @GetMapping("/category/{name}")
    public String getCategory(@PathVariable String name, Model model) {
        CategoryDto category = categoryService.findCategoryByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<ClothesDto> clothes = clothesService.findClothesByCategoryName(name);

        model.addAttribute("heading", category.getName());
        model.addAttribute("clothes", clothes);
        return "main-site";
    }
    
    @GetMapping("/category/{name}/{subcategory}s")
    public String getSubcategory(@PathVariable String name, @PathVariable String subcategory, Model model) {
        CategoryDto category = categoryService.findCategoryByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        SubcategoryDto subcategoryName = subcategoryService.findSubcategoryByName(subcategory)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<ClothesDto> clothes = clothesService.findClothesBySubcategoryName(subcategory);

        model.addAttribute("heading", subcategoryName.getName()+'s');
        model.addAttribute("clothes", clothes);
        return "main-site";
    }
}
