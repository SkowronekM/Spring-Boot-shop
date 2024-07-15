package pl.storeez.domain.categories;

import org.springframework.stereotype.Service;
import pl.storeez.domain.categories.dto.CategoryDto;

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
}
