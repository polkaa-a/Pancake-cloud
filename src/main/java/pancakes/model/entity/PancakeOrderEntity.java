package pancakes.model.entity;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class PancakeOrderEntity {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Date placedAt;

    private final String phoneNumPattern = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    @NotBlank(message="Delivery name is required")
    private String deliveryName;

    @NotBlank(message="Delivery address is required")
    private String deliveryAddress;

    @Pattern(regexp=phoneNumPattern, message="Invalid number")
    private String phoneNum;

    private List<PancakeEntity> pancakes = new ArrayList<>();

    public void addPancake(PancakeEntity pancake) {
        this.pancakes.add(pancake);
    }
}
