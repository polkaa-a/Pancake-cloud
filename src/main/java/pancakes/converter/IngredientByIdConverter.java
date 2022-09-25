package pancakes.converter;

import java.util.HashMap;
import java.util.Map;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pancakes.model.entity.IngredientEntity;

@Component
public class IngredientByIdConverter implements Converter<String, IngredientEntity> {
    private final Map<String, IngredientEntity> ingredientMap = new HashMap<>();
    public IngredientByIdConverter() {
        ingredientMap.put("CP", new IngredientEntity("CP", "Crispy Pancake", IngredientEntity.Type.DOUGH));
        ingredientMap.put("SC", new IngredientEntity("SC", "Soft Pancake", IngredientEntity.Type.DOUGH));
        ingredientMap.put("SS", new IngredientEntity("SS", "Sweet Strawberries", IngredientEntity.Type.FILLING));
        ingredientMap.put("CM", new IngredientEntity("CM", "Condensed Milk", IngredientEntity.Type.FILLING));
        ingredientMap.put("HC", new IngredientEntity("HC", "Ham and Cheese", IngredientEntity.Type.FILLING));
        ingredientMap.put("SCR", new IngredientEntity("SCR", "Sour Cream", IngredientEntity.Type.FILLING));
        ingredientMap.put("SB", new IngredientEntity("SB", "Sweet Blueberries", IngredientEntity.Type.FILLING));
    }
    @Override
    public IngredientEntity convert(String id) {
        return ingredientMap.get(id);
    }
}
