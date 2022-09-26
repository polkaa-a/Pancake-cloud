package pancakes.controller;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import pancakes.model.entity.IngredientEntity;
import pancakes.model.entity.IngredientEntity.Type;
import pancakes.model.entity.PancakeEntity;
import pancakes.model.entity.PancakeOrderEntity;
import pancakes.repository.interfaces.IngredientRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("pancakeOrderEntity")
public class DesignPancakeController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignPancakeController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<IngredientEntity> ingredients = ingredientRepo.findAll();
        Type[] types = IngredientEntity.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

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
    private Iterable<IngredientEntity> filterByType(Iterable<IngredientEntity> ingredients, Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(i -> i.getType().equals(type))
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
