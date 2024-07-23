package pl.storeez.domain.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.storeez.domain.categories.dto.SubcategoryDto;
import pl.storeez.domain.clothes.ClothesRepository;

import java.util.Optional;

@Service
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubcategoryService(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public Optional<SubcategoryDto> findSubcategoryByName(String name) {
        return subcategoryRepository.findByNameIgnoreCase(name).map(SubcategoryDtoMapper::map);
    }

    public Iterable<Subcategory> getAllSubcategories(){
        return subcategoryRepository.findAll();
    }

    @Transactional
    public SubcategoryDto addSubcategory(SubcategoryDto subcategoryDto) {
        Category category = categoryRepository.findById(subcategoryDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found!"));

        Subcategory subcategoryToSave = new Subcategory();
        subcategoryToSave.setName(subcategoryDto.getName());
        subcategoryToSave.setCategory(category);
        subcategoryToSave = subcategoryRepository.save(subcategoryToSave);

        subcategoryDto.setId(subcategoryToSave.getId());
        return subcategoryDto;
    }
}
