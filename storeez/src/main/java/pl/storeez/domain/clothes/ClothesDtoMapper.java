package pl.storeez.domain.clothes;

import pl.storeez.domain.clothes.dto.ClothesDto;

public class ClothesDtoMapper {
    static ClothesDto map(Clothes clothes) {
        return new ClothesDto(
                clothes.getId(),
                clothes.getName(),
                clothes.getBrand(),
                clothes.getDescription(),
                clothes.getPrice(),
                clothes.getStock(),
                clothes.getSubcategory().getCategory().getName(),
                clothes.getSubcategory().getName(),
                clothes.isPromoted(),
                clothes.getSize(),
                clothes.getColor(),
                clothes.getMaterial()
        );

    }
}
