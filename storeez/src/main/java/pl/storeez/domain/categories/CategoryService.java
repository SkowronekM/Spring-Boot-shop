package pl.storeez.domain.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.storeez.domain.categories.dto.CategoryDto;
import pl.storeez.domain.categories.dto.SubcategoryDto;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<CategoryDto> findCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name).map(CategoryDtoMapper::map);
    }

    @Transactional
    public void addCategory(CategoryDto categoryDto) {
        Category categoryToSave = new Category();
        categoryToSave.setId(categoryDto.getId());
        categoryToSave.setName(categoryDto.getName());
        categoryRepository.save(categoryToSave);
    }
}
