import java.util.ArrayList;
import java.util.List;

public class RecipeBook {
    private List<Recipe> recipes = new ArrayList<>();

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void editRecipe(int index, Recipe recipe) {
        recipes.set(index, recipe);
    }

    public void deleteRecipe(int index) {
        recipes.remove(index);
    }

    public Recipe getRecipe(int index) {
        return recipes.get(index);
    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }
}