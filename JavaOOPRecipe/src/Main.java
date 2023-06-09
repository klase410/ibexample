import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RecipeBook recipeBook = new RecipeBook();

        while (true) {
            System.out.println("Enter a command (add, edit, delete, view, exit): ");
            String command = scanner.nextLine();

            switch (command) {
                case "add":
                    System.out.println("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.println("Enter instructions: ");
                    String instructions = scanner.nextLine();
                    recipeBook.addRecipe(new Recipe(name, category, instructions));
                    break;
                case "edit":
                    System.out.println("Enter index: ");
                    int editIndex = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter name: ");
                    String editName = scanner.nextLine();
                    System.out.println("Enter category: ");
                    String editCategory = scanner.nextLine();
                    System.out.println("Enter instructions: ");
                    String editInstructions = scanner.nextLine();
                    recipeBook.editRecipe(editIndex, new Recipe(editName, editCategory, editInstructions));
                    break;
                case "delete":
                    System.out.println("Enter index: ");
                    int deleteIndex = Integer.parseInt(scanner.nextLine());
                    recipeBook.deleteRecipe(deleteIndex);
                    break;
                case "view":
                    for (Recipe recipe : recipeBook.getAllRecipes()) {
                        System.out.println("Name: " + recipe.getName());
                        System.out.println("Category: " + recipe.getCategory());
                        System.out.println("Instructions: " + recipe.getInstructions());
                        System.out.println();
                    }
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        }
    }
}
