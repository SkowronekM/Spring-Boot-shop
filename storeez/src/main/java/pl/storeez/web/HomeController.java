package pl.storeez.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.storeez.domain.clothes.ClothesService;
import pl.storeez.domain.clothes.dto.ClothesDto;

import java.util.List;

@Controller
public class HomeController {
    private final ClothesService clothesService;

    public HomeController(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ClothesDto> promotedClothes = clothesService.findAllPromotedClothes();
        model.addAttribute("heading", "Featured products");
        model.addAttribute("clothes", promotedClothes);
        
        return "main-site";
    }
}
