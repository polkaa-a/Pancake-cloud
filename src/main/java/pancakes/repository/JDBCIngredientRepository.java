package pancakes.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pancakes.model.entity.IngredientEntity;
import pancakes.repository.interfaces.IngredientRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JDBCIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;
    public JDBCIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<IngredientEntity> findAll() {
        return jdbcTemplate.query(
                "select id, name, type from Ingredient",
                this::mapRowToIngredient);
    }
    @Override
    public Optional<IngredientEntity> findById(String id) {
        List<IngredientEntity> results = jdbcTemplate.query(
                "select id, name, type from Ingredient where id=?",
                this::mapRowToIngredient,
                id);
        return results.size() == 0 ?
                Optional.empty() :
                Optional.of(results.get(0));
    }

    @Override
    public IngredientEntity save(IngredientEntity ingredient) {
        jdbcTemplate.update(
                "insert into Ingredient (id, name, type) values (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }

    private IngredientEntity mapRowToIngredient(ResultSet row, int rowNum)
            throws SQLException {
        return new IngredientEntity(
                row.getString("id"),
                row.getString("name"),
                IngredientEntity.Type.valueOf(row.getString("type")));
    }
}
