package pancakes.model.entity;
import lombok.Data;

@Data
public class IngredientEntity {
    private final String id;
    private final String name;
    private final Type type;

    public enum Type {
        DOUGH, FILLING
    }
}
