package pancakes.repository;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pancakes.model.entity.IngredientRef;
import pancakes.model.entity.PancakeEntity;
import pancakes.model.entity.PancakeOrderEntity;
import pancakes.repository.interfaces.OrderRepository;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class JDBCOrderRepository implements OrderRepository {

    private JdbcOperations jdbcOperations;

    public JDBCOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public PancakeOrderEntity save(PancakeOrderEntity order) {

        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Pancake_Order "
                                + "(delivery_name, delivery_address, phone_numb, placed_at) "
                                + "values (?,?,?,?)",
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
                );

        //т.к. понадобится идентификатор сохраненного заказа
        pscf.setReturnGeneratedKeys(true);
        order.setPlacedAt(new Date());

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                order.getDeliveryName(),
                                order.getDeliveryAddress(),
                                order.getPhoneNum(),
                                order.getPlacedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        //После сохранения заказа (jdbcOperations.update)
        //в GeneratedKeyHolder будет храниться значение поля id,
        //назначенное базой данных, которое затем следует скопировать в свойство id
        //объекта PancakeOrderEntity
        jdbcOperations.update(psc, keyHolder);

        long orderId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        order.setId(orderId);

        List<PancakeEntity> pancakes = order.getPancakes();

        int i=0;
        for (PancakeEntity pancake : pancakes) {
            savePancake(orderId, i++, pancake);
        }
        return order;
    }

    private long savePancake(Long orderId, int orderKey, PancakeEntity pancake) {

        pancake.setCreatedAt(new Date());

        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Pancake "
                                + "(name, created_at, pancake_order, pancake_order_key) "
                                + "values (?, ?, ?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
                );

        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                pancake.getName(),
                                pancake.getCreatedAt(),
                                orderId,
                                orderKey));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);

        long pancakeId = keyHolder.getKey().longValue();
        pancake.setId(pancakeId);

        saveIngredientRefs(pancakeId, pancake.getIngredients());

        return pancakeId;
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientsRefs) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientsRefs) {
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, taco, taco_key) "
                            + "values (?, ?, ?)",
                    ingredientRef.getIngredient(), tacoId, key++);
        }
    }
}
