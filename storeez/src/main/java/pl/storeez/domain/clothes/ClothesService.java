package pl.storeez.domain.clothes;

import org.springframework.stereotype.Service;
import pl.storeez.domain.clothes.dto.ClothesDto;

import java.util.List;
import java.util.Optional;

@Service
public class ClothesService {
    private final ClothesRepository clothesRepository;

    public ClothesService(ClothesRepository clothesRepository) {
        this.clothesRepository = clothesRepository;
    }

    public List<ClothesDto> findAllPromotedClothes() {
        return clothesRepository.findAllByPromotedIsTrue()
                .stream()
                .map(ClothesDtoMapper::map)
                .toList();
    }

    public Optional<ClothesDto> findClothesById(long id) {
        return clothesRepository.findById(id).map(ClothesDtoMapper::map);
    }
}
