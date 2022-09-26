package pancakes.repository.interfaces;

import pancakes.model.entity.PancakeOrderEntity;
public interface OrderRepository {
    PancakeOrderEntity save(PancakeOrderEntity order);
}
