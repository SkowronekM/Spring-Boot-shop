package pl.storeez.domain.categories;

import org.springframework.stereotype.Service;
import pl.storeez.domain.categories.dto.SubcategoryDto;

import java.util.Optional;

@Service
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;

    public SubcategoryService(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    public Optional<SubcategoryDto> findSubcategoryByName(String name) {
        return subcategoryRepository.findByNameIgnoreCase(name).map(SubcategoryDtoMapper::map);
    }
}
