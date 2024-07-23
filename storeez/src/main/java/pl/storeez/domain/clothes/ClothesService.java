package pl.storeez.domain.clothes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.storeez.domain.categories.Subcategory;

import pl.storeez.domain.categories.SubcategoryRepository;
import pl.storeez.domain.clothes.dto.ClothesDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;


@Service
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final SubcategoryRepository subcategoryRepository;

    public ClothesService(ClothesRepository clothesRepository, SubcategoryRepository subcategoryRepository) {
        this.clothesRepository = clothesRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    public List<ClothesDto> findAllPromotedClothes() {
        return clothesRepository.findAllByPromotedIsTrue()
                .stream()
                .map(ClothesDtoMapper::map)
                .toList();
    }

    public Optional<ClothesDto> findClothesById(long id) {
        return clothesRepository.findById(id).map(ClothesDtoMapper::map);
    }

    public List<ClothesDto> findClothesByCategoryName(String category) {
        return clothesRepository.findAllByCategoryNameIgnoreCase(category).stream()
                .map(ClothesDtoMapper::map)
                .toList();
    }

    public List<ClothesDto> findClothesBySubcategoryName(String subcategory) {
        return clothesRepository.findAllBySubcategoryNameIgnoreCase(subcategory).stream()
                .map(ClothesDtoMapper::map)
                .toList();
    }

    public BigDecimal getDiscountedPrice(ClothesDto clothesDto) {
        BigDecimal price = clothesDto.getPrice();
        BigDecimal discountPercentage = new BigDecimal(clothesDto.getDiscount());
        BigDecimal discountedPrice = price;

        if (discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
            // count discount value
            BigDecimal discountAmount = price.multiply(discountPercentage.divide(new BigDecimal("100")));
            discountedPrice = price.subtract(discountAmount);

            // round to 2 places
            discountedPrice = discountedPrice.setScale(2, RoundingMode.HALF_UP);
        }

        clothesDto.setPriceAfterDiscount(discountedPrice);

        // find clothes by id and update price after discount
        Clothes clothes = clothesRepository.findById(clothesDto.getId())
                .orElseThrow(() -> new RuntimeException("Clothes not found"));
        clothes.setPriceAfterDiscount(discountedPrice);
        clothesRepository.save(clothes);

        return discountedPrice;
    }

    @Transactional
    public void addClothes(ClothesDto clothesDto) {
        if (clothesDto.getSubcategory() == null || clothesDto.getSubcategory().isEmpty()) {
            throw new IllegalArgumentException("Subcategory cannot be null or empty");
        }

        Subcategory subcategory = subcategoryRepository.findByName(clothesDto.getSubcategory())
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));

        Clothes clothesToSave = ClothesDtoMapper.mapToEntity(clothesDto, subcategory);
        clothesRepository.save(clothesToSave);
    }
}
