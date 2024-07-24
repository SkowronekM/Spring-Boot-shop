package pl.storeez.domain.clothes;

import pl.storeez.domain.subcategories.Subcategory;
import pl.storeez.domain.clothes.dto.ClothesDto;

public class ClothesDtoMapper {
    public static ClothesDto map(Clothes clothes) {
        return new ClothesDto(
                clothes.getId(),
                clothes.getName(),
                clothes.getBrand(),
                clothes.getDescription(),
                clothes.getPrice(),
                clothes.getDiscount(),
                clothes.getPriceAfterDiscount(),
                clothes.getStock(),
                clothes.getSubcategory().getCategory().getName(),
                clothes.getSubcategory().getName(),
                clothes.isPromoted(),
                clothes.getSize(),
                clothes.getColor(),
                clothes.getMaterial()
        );
    }

    public static Clothes mapToEntity(ClothesDto clothesDto, Subcategory subcategory) {
        Clothes clothes = new Clothes();
        clothes.setId(clothesDto.getId());
        clothes.setName(clothesDto.getName());
        clothes.setBrand(clothesDto.getBrand());
        clothes.setDescription(clothesDto.getDescription());
        clothes.setPrice(clothesDto.getPrice());
        clothes.setDiscount(clothesDto.getDiscount());
        clothes.setPriceAfterDiscount(clothesDto.getPriceAfterDiscount());
        clothes.setStock(clothesDto.getStock());
        clothes.setPromoted(clothesDto.isPromoted());
        clothes.setSize(clothesDto.getSize());
        clothes.setColor(clothesDto.getColor());
        clothes.setMaterial(clothesDto.getMaterial());
        clothes.setSubcategory(subcategory);
        return clothes;
    }
}
