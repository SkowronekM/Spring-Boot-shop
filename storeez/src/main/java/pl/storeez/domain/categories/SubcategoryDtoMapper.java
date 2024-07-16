package pl.storeez.domain.categories;

import pl.storeez.domain.categories.dto.SubcategoryDto;

public class SubcategoryDtoMapper {


    static SubcategoryDto map(Subcategory subcategory) {
        Long categoryId = subcategory.getCategory().getId();

        return new SubcategoryDto(
                subcategory.getId(),
                subcategory.getName(),
                categoryId
        );
    }
}
