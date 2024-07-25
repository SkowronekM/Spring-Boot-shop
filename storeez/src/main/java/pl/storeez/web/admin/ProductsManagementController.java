package pl.storeez.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.storeez.domain.clothes.Clothes;
import pl.storeez.domain.clothes.ClothesDtoMapper;
import pl.storeez.domain.clothes.ClothesRepository;
import pl.storeez.domain.clothes.ClothesService;
import pl.storeez.domain.clothes.dto.ClothesDto;
import pl.storeez.domain.clothes.dto.ClothesEditDto;
import pl.storeez.domain.subcategories.SubcategoryService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/admin")
public class ProductsManagementController {
    private final SubcategoryService subcategoryService;
    private final ClothesService clothesService;
    private final ClothesRepository clothesRepository;

    public ProductsManagementController(SubcategoryService subcategoryService, ClothesService clothesService, ClothesRepository clothesRepository) {
        this.subcategoryService = subcategoryService;
        this.clothesService = clothesService;
        this.clothesRepository = clothesRepository;
    }

    @GetMapping("/view-products")
    public String viewProductsTable(Model model) {
        Iterable<Clothes> clothes = clothesService.findAllClothes();

        List<ClothesDto> clothesDtoList = StreamSupport.stream(clothes.spliterator(), false)
                .map(ClothesDtoMapper::map)
                .collect(Collectors.toList());

        for (ClothesDto clothesDto : clothesDtoList) {
            clothesService.getDiscountedPrice(clothesDto);
        }

        model.addAttribute("clothes", clothesDtoList);

        return "admin/view-products";
    }

    @GetMapping("/edit-product/{id}")
    public String editProductTable(@PathVariable Long id, Model model) {
        Clothes clothes = clothesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clothes not found!"));
        model.addAttribute("clothes", clothes);
        model.addAttribute("subcategories", subcategoryService.getAllSubcategories());

        return "admin/edit-product";
    }

    @PostMapping("/edit-product")
    public String editProduct(@ModelAttribute ClothesEditDto clothes, RedirectAttributes redirectAttributes) {

        clothesService.updateClothes(clothes);

        redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTR, "Product %s has been changed"
                .formatted(clothes.getName()));

        return "redirect:/admin/view-products";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ClothesDto clothes = clothesService.findClothesById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));;
        clothesService.deleteClothesById(id);

        redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTR, "Product %s has been deleted".formatted(clothes.getName()));

        return "redirect:/admin/view-products";
    }
}
