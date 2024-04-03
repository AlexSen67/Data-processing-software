package models;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String id;
    private String title;
    private String date;
    private List<Ingredient> ingredients;
    private List<String> preparationSteps;
    private String comment;
    private Nutrition nutrition;

    public Recipe() {
        this.ingredients = new ArrayList<>();
        this.preparationSteps = new ArrayList<>();
        this.nutrition = new Nutrition();
    }

    // Getters and setters for all attributes

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public List<String> getPreparationSteps() {
        return preparationSteps;
    }

    public void setPreparationSteps(List<String> preparationSteps) {
        this.preparationSteps = preparationSteps;
    }

    public void addPreparationStep(String step) {
        this.preparationSteps.add(step);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", ingredients=" + ingredients +
                ", preparationSteps=" + preparationSteps +
                ", comment='" + comment + '\'' +
                ", nutrition=" + nutrition +
                '}';
    }
}
