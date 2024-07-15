package pl.storeez.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import pl.storeez.domain.categories.CategoryDtoMapper;
import pl.storeez.domain.categories.CategoryService;
import pl.storeez.domain.categories.dto.CategoryDto;
import pl.storeez.domain.clothes.ClothesDtoMapper;
import pl.storeez.domain.clothes.ClothesService;
import pl.storeez.domain.clothes.dto.ClothesDto;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CategoryController {
    private final ClothesService clothesService;
    private final CategoryService categoryService;

    public CategoryController(ClothesService clothesService, CategoryService categoryService) {
        this.clothesService = clothesService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{name}")
    public String getCategory(@PathVariable String name, Model model) {
        CategoryDto category = categoryService.findCategoryByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<ClothesDto> clothes = clothesService.findClothesByCategoryName(name);

        model.addAttribute("heading", category.getName());
        model.addAttribute("clothes", clothes);
        return "main-site";
    }
}
