package pl.storeez.domain.subcategories;

import pl.storeez.domain.subcategories.dto.SubcategoryDto;

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
