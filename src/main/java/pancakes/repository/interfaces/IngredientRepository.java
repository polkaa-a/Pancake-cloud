package pancakes.repository.interfaces;

import pancakes.model.entity.IngredientEntity;

import java.util.Optional;

public interface IngredientRepository {
    Iterable<IngredientEntity> findAll();
    Optional<IngredientEntity> findById(String id);
    IngredientEntity save(IngredientEntity ingredient);
}
