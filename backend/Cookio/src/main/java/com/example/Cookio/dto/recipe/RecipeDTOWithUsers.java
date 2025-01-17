package com.example.Cookio.dto.recipe;

import com.example.Cookio.dto.user.UserDTO;
import com.example.Cookio.models.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class RecipeDTOWithUsers {
    private int id;
    private String title;
    private String description;
    private String instructions; // New code
    private String image;
    private int prepTime;
    private int cookTime;
    private int servings;
    private String category;
    private UserDTO author;
    private Type type;
    private Cuisine cuisine;
    private Set<UserDTO> users;
    private Set<Ingredient> ingredients;

    public static RecipeDTOWithUsers fromRecipe(Recipe recipe) {

        UserDTO convertedAuthor = UserDTO.fromUser(recipe.getAuthor());

        Set<UserDTO> convertedUsers = (recipe.getUsers() == null || recipe.getUsers().isEmpty()) ?
                null :
                recipe.getUsers().stream()
                        .map(UserDTO::fromUser)  // Convert each User to UserDTO
                        .collect(Collectors.toSet());  // Collect the converted UserDTOs

        return new RecipeDTOWithUsers(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getInstructions(), // new code
                recipe.getImage(),
                recipe.getPrepTime(),
                recipe.getCookTime(),
                recipe.getServings(),
                recipe.getCategory(),
                convertedAuthor,
                recipe.getType(),
                recipe.getCuisine(),
                convertedUsers,
                recipe.getIngredients()
        );
    }
}