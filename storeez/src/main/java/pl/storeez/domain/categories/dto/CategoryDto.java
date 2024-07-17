package pl.storeez.domain.categories.dto;

import java.util.List;

public class CategoryDto {
    private Long id;
    private String name;
    private List<SubcategoryDto> subcategories;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, List<SubcategoryDto> subcategories) {
        this.id = id;
        this.name = name;
        this.subcategories = subcategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubcategoryDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubcategoryDto> subcategories) {
        this.subcategories = subcategories;
    }
}
