package pancakes.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import pancakes.model.entity.IngredientEntity;
import pancakes.model.entity.IngredientEntity.Type;
import pancakes.model.entity.PancakeEntity;
import pancakes.model.entity.PancakeOrderEntity;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
/*
@SessionAttributes -- объект pancakeOrderEntity должен поддерживаться на уровне сеанса.
Это важно, потому что создание панкейка также является первым шагом в создании заказа,
и созданный нами заказ необходимо будет перенести в сеанс, охватывающий несколько запросов.
*/
@SessionAttributes("pancakeOrderEntity")
public class DesignPancakeController {
    /*
    Этот метод  будет вызываться в процессе обработки запроса
    и создавать список объектов Ingredient, который затем будет помещен в модель

    Model – это объект, в котором данные пересылаются между контроллером и любым представлением,
    ответственным за преобразование этих данных в разметку HTML

    В конечном итоге данные, помещенные в атрибуты модели, копируются в атрибуты запроса сервлета,
    где представление найдет их и использует для отображения страницы в браузере пользователя
    */
    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<IngredientEntity> ingredients = Arrays.asList(
                new IngredientEntity("CP", "Crispy Pancake", Type.DOUGH),
                new IngredientEntity("SC", "Soft Pancake", Type.DOUGH),
                new IngredientEntity("SS", "Sweet Strawberries", Type.FILLING),
                new IngredientEntity("CM", "Condensed Milk", Type.FILLING),
                new IngredientEntity("HC", "Ham and Cheese", Type.FILLING),
                new IngredientEntity("SCR", "Sour Cream", Type.FILLING),
                new IngredientEntity("SB", "Sweet Blueberries", Type.FILLING)
        );

        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    /*
    Еще два метода, также снабженных аннотацией @ModelAttribute

    Эти методы просто создают новые объекты pancakeOrderEntity и pancakeEntity
    для размещения в модели

    Объект pancakeOrderEntity, упомянутый выше в аннотации @SessionAttributes,
    хранит состояние собираемого заказа, пока клиент выбирает ингредиенты
    для панкейка несколькими запросами

    Объект pancakeEntity помещается в модель, чтобы представление, отображаемое в ответ
    на запрос GET с путем /design, имело объект для отображения
    */

    @ModelAttribute(name = "pancakeOrderEntity")
    public PancakeOrderEntity order() {
        return new PancakeOrderEntity();
    }
    @ModelAttribute(name = "pancakeEntity")
    public PancakeEntity pancake() {
        return new PancakeEntity();
    }
    @GetMapping
    public String showDesignForm() {
        return "design";
    }
    private Iterable<IngredientEntity> filterByType(List<IngredientEntity> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processPancake(@Valid PancakeEntity pancakeEntity, Errors errors, @ModelAttribute PancakeOrderEntity pancakeOrderEntity) {

        if (errors.hasErrors()) {
            return "design";
        }

        pancakeOrderEntity.addPancake(pancakeEntity);
        log.info("Processing pancake: {}", pancakeEntity);


        return "redirect:/orders/current";
    }
}
