public class Recipe {
    private String name;
    private String category;
    private String instructions;

    public Recipe(String name, String category, String instructions) {
        this.name = name;
        this.category = category;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    }
