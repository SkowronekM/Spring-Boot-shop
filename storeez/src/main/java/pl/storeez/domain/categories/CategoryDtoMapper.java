package pl.storeez.domain.categories;

import pl.storeez.domain.categories.dto.CategoryDto;
import pl.storeez.domain.subcategories.dto.SubcategoryDto;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryDtoMapper {
    static CategoryDto map(Category category) {
        List<SubcategoryDto> subcategoryDtos = category.getSubcategories()
                .stream()
                .map(subcategory -> new SubcategoryDto(subcategory.getId(), subcategory.getName(), subcategory.getCategory().getId()))
                .collect(Collectors.toList());

        return new CategoryDto(
                category.getId(),
                category.getName(),
                subcategoryDtos
        );
    }
}