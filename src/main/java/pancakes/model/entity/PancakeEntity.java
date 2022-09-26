package pancakes.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/* @Data -- сгенерирует геттеры и сеттеры для всех полей,
toString и переопределит equals и hashCode*/
@Data
public class PancakeEntity {

    private Long id;
    private Date createdAt = new Date();

    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;

    @NotNull
    @Size(min=2, message="You must choose at least 2 ingredients (dough and any filling)")
    private List<IngredientRef> ingredients = new ArrayList<>();
}
