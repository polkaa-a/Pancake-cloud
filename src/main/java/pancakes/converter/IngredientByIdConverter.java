package pancakes.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pancakes.model.entity.IngredientEntity;
import pancakes.repository.interfaces.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, IngredientEntity> {

    private IngredientRepository ingredientRepo;
    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }
    @Override
    public IngredientEntity convert(String id) {
        return ingredientRepo.findById(id).orElse(null);
    }
}
