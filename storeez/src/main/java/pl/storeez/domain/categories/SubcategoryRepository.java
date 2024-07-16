package pl.storeez.domain.categories;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubcategoryRepository extends CrudRepository<Subcategory, Long> {
    Optional<Subcategory> findByNameIgnoreCase(String name);
}
