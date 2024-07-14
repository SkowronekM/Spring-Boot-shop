package pl.storeez.domain.clothes;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClothesRepository extends CrudRepository<Clothes, Long> {
    List<Clothes> findAllByPromotedIsTrue();
}
