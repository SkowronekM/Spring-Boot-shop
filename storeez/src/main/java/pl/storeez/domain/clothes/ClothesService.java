package pl.storeez.domain.clothes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.storeez.domain.clothes.dto.ClothesEditDto;
import pl.storeez.domain.clothes.dto.ClothesSaveDto;
import pl.storeez.domain.subcategories.Subcategory;

import pl.storeez.domain.subcategories.SubcategoryRepository;
import pl.storeez.domain.clothes.dto.ClothesDto;
import pl.storeez.storage.FileStorageService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;


@Service
public class ClothesService {
    private final ClothesRepository clothesRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final FileStorageService fileStorageService;

    public ClothesService(ClothesRepository clothesRepository, SubcategoryRepository subcategoryRepository, FileStorageService fileStorageService) {
        this.clothesRepository = clothesRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.fileStorageService = fileStorageService;
    }

    public Iterable<Clothes> findAllClothes() {
        return clothesRepository.findAll();
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
    public void addClothes(ClothesSaveDto clothesDto) {
        if (clothesDto.getSubcategory() == null) {
            throw new IllegalArgumentException("Subcategory cannot be null or empty");
        }

        Subcategory subcategory = subcategoryRepository.findByName(clothesDto.getSubcategory())
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));

        Clothes clothesToSave = ClothesDtoMapper.mapToEntity(clothesDto, subcategory);

        if (clothesDto.getImage() != null && !clothesDto.getImage().isEmpty()) {
            String savedFileName = fileStorageService.saveImage(clothesDto.getImage());
            clothesToSave.setImage(savedFileName);
        }
        clothesRepository.save(clothesToSave);
    }

    @Transactional
    public void updateClothes(ClothesEditDto clothesDto) {
        Subcategory subcategory = subcategoryRepository.findById(clothesDto.getSubcategory())
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));

        Clothes clothesToSave;

        if (clothesDto.getId() != null) {
            clothesToSave = clothesRepository.findById(clothesDto.getId())
                    .orElseThrow(() -> new RuntimeException("Clothes not found"));

            clothesToSave.setName(clothesDto.getName());
            clothesToSave.setBrand(clothesDto.getBrand());
            clothesToSave.setDescription(clothesDto.getDescription());
            clothesToSave.setPrice(clothesDto.getPrice());
            clothesToSave.setPriceAfterDiscount(clothesToSave.getPriceAfterDiscount());
            clothesToSave.setDiscount(clothesDto.getDiscount());
            clothesToSave.setStock(clothesDto.getStock());
            clothesToSave.setPromoted(clothesDto.isPromoted());
            clothesToSave.setSize(clothesDto.getSize());
            clothesToSave.setColor(clothesDto.getColor());
            clothesToSave.setMaterial(clothesDto.getMaterial());
            clothesToSave.setSubcategory(subcategory);

            if (clothesDto.getImage() != null && !clothesDto.getImage().isEmpty()) {
                String savedFileName = fileStorageService.saveImage(clothesDto.getImage());
                clothesToSave.setImage(savedFileName);
            }
            clothesRepository.save(clothesToSave);
        }
    }

    @Transactional
    public void deleteClothesBySubcategory(Long id) {
        List<Clothes> clothes = clothesRepository.findAllBySubcategoryId(id);
        clothesRepository.deleteAll(clothes);
    }

    @Transactional
    public void deleteClothesById(Long id) {
        clothesRepository.deleteById(id);
    }
}
