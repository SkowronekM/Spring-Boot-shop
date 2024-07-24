package pl.storeez.domain.categories;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends CrudRepository<Subcategory, Long> {
    Optional<Subcategory> findByName(String name);
    Optional<Subcategory> findByNameIgnoreCase(String name);
    List<Subcategory> findAllByOrderByIdAsc();
    List<Subcategory> findByCategoryId(Long categoryId);
}
