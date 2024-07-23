package pl.storeez.domain.clothes;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClothesRepository extends CrudRepository<Clothes, Long> {
    List<Clothes> findAllByPromotedIsTrue();

    @Query("SELECT c FROM Clothes c WHERE LOWER(c.subcategory.category.name) = LOWER(:categoryName)")
    List<Clothes> findAllByCategoryNameIgnoreCase(@Param("categoryName") String categoryName);

    List<Clothes> findAllBySubcategoryNameIgnoreCase(String subcategoryName);
    List<Clothes> findAllBySubcategoryId(Long subcategoryId);
}
