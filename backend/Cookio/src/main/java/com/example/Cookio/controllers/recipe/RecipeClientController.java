package com.example.Cookio.controllers.recipe;

import com.example.Cookio.dto.recipe.RecipeDTONoUser;
import com.example.Cookio.dto.recipe.RecipeDTOWithUsers;
import com.example.Cookio.models.Recipe;
import com.example.Cookio.services.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client/recipes")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
// UNTESTED
public class RecipeClientController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeClientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTONoUser>> getAllRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<RecipeDTONoUser> recipes = recipeService.getAllRecipesNoIngredientsNoUsers(page, size);
        return ResponseEntity.ok(recipes);
    }


    @PostMapping("/create")
    public ResponseEntity<RecipeDTOWithUsers> createRecipe(@RequestBody Recipe recipe) {
        RecipeDTOWithUsers savedRecipe = recipeService.createRecipe(recipe);
        return ResponseEntity.ok(savedRecipe);
    }

    // Endpoint to get a recipe by ID +
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTOWithUsers> getRecipeById(@PathVariable int id) {
        Optional<RecipeDTOWithUsers> recipe = recipeService.getRecipeById(id);
        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to search recipes by title +
    @GetMapping("/search/title")
    public ResponseEntity<List<RecipeDTOWithUsers>> findRecipesByTitle(@RequestParam String title) {
        List<RecipeDTOWithUsers> recipes = recipeService.findRecipesByTitle(title);
        return ResponseEntity.ok(recipes);
    }

    // Endpoint to search recipes by description +
    @GetMapping("/search/description")
    public ResponseEntity<List<RecipeDTOWithUsers>> findRecipesByDescription(@RequestParam String description) {
        List<RecipeDTOWithUsers> recipes = recipeService.findRecipesByDescription(description);
        return ResponseEntity.ok(recipes);
    }

    // Endpoint to search recipes by ingredients -
    @GetMapping("/search/ingredients")
    public ResponseEntity<List<RecipeDTOWithUsers>> findRecipesByIngredients(@RequestParam String ingredients) {
        List<RecipeDTOWithUsers> recipes = recipeService.findRecipesByIngredients(ingredients);
        return ResponseEntity.ok(recipes);
    }
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<RecipeDTOWithUsers>> findRecipesByAuthor(@PathVariable int authorId) {
        List<RecipeDTOWithUsers> recipes = recipeService.findRecipesByAuthorId(authorId);
        return ResponseEntity.ok(recipes);
    }

    // +
    @GetMapping("/search/category")
    public ResponseEntity<List<RecipeDTOWithUsers>> findRecipesByCategory(@RequestParam String category) {
        List<RecipeDTOWithUsers> recipes = recipeService.findRecipesByCategory(category);
        return ResponseEntity.ok(recipes);
    }

    // Find recipes by type +
    @GetMapping("/search/type/{typeId}")
    public ResponseEntity<List<RecipeDTOWithUsers>> findRecipesByType(@PathVariable int typeId) {
        List<RecipeDTOWithUsers> recipes = recipeService.findRecipesByType(typeId);
        return ResponseEntity.ok(recipes);
    }

    // Find recipes by cuisine +
    @GetMapping("/search/cuisine/{cuisineId}")
    public ResponseEntity<List<RecipeDTOWithUsers>> findRecipesByCuisine(@PathVariable int cuisineId) {
        List<RecipeDTOWithUsers> recipes = recipeService.findRecipesByCuisine(cuisineId);
        return ResponseEntity.ok(recipes);
    }

    // NEW: Find recipes by ingredient ID
    @GetMapping("/search/ingredients/{ingredientId}")
    public ResponseEntity<List<RecipeDTOWithUsers>> findRecipesByIngredient(@PathVariable int ingredientId) {
        List<RecipeDTOWithUsers> recipes = recipeService.findRecipesByIngredient(ingredientId);
        if (recipes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recipes);
    }


}
