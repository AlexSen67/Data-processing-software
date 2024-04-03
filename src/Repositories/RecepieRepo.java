//Copyright © Alexandre Sen / Solano Fortes Vasconcelos

package Repositories;

import models.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class RecepieRepo {
    private List<Recipe> recipeList;
    public RecepieRepo() {
        recipeList = new ArrayList<>();
    }

     // Méthode pour ajouter une recette à la collection
     public void addRecipe(Recipe recipe) {
        recipeList.add(recipe);
    }

    // Méthode pour initialiser la collection avec les données extraites du fichier XML
    public void init() {
        try {
            File file = new File("src\\recipes.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList recipeNodeList = doc.getElementsByTagName("rcp:recipe");

            for (int i = 0; i < recipeNodeList.getLength(); i++) {
                Element recipeElement = (Element) recipeNodeList.item(i);
                Recipe recipe = createRecipeFromElement(recipeElement);
                this.addRecipe(recipe);
            }

            System.out.println("Collection de recettes initialisée avec succès à partir du fichier XML.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour créer une instance de Recipe à partir d'un élément XML de recette
    private Recipe createRecipeFromElement(Element recipeElement) {
        // Extraire les attributs et les éléments de la balise de recette
        String id = recipeElement.getAttribute("id");
        String title = recipeElement.getElementsByTagName("rcp:title").item(0).getTextContent();
        String date = recipeElement.getElementsByTagName("rcp:date").item(0).getTextContent();
    
        NodeList ingredientList = recipeElement.getElementsByTagName("rcp:ingredient");
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientList.getLength(); i++) {
            Element ingredientElement = (Element) ingredientList.item(i);
            String ingredientName = ingredientElement.getAttribute("name");
            String amount = ingredientElement.getAttribute("amount");
            String unit = ingredientElement.getAttribute("unit");
            Ingredient ingredient = new Ingredient(ingredientName, amount, unit);
            ingredients.add(ingredient);
        }
    
        // Extraction du texte de préparation
        String preparationText = recipeElement.getElementsByTagName("rcp:preparation").item(0).getTextContent().trim();
    
        // Division du texte de préparation en étapes individuelles
        String[] preparationStepsArray = preparationText.split("\\r?\\n");
        List<String> preparationSteps = new ArrayList<>();
        for (String step : preparationStepsArray) {
            // Ajouter seulement les étapes non vides
            if (!step.trim().isEmpty()) {
                preparationSteps.add(step.trim());
            }
        }
    
        String comment = "";
        if (recipeElement.getElementsByTagName("rcp:comment").getLength() > 0) {
            comment = recipeElement.getElementsByTagName("rcp:comment").item(0).getTextContent();
        }
    
        Element nutritionElement = (Element) recipeElement.getElementsByTagName("rcp:nutrition").item(0);
        int calories = Integer.parseInt(nutritionElement.getAttribute("calories"));
        String fat = nutritionElement.getAttribute("fat");
        String carbohydrates = nutritionElement.getAttribute("carbohydrates");
        String protein = nutritionElement.getAttribute("protein");
    
        // Créer une instance de recette avec les informations extraites
        Recipe recipe = new Recipe();
        recipe.setId(id);
        recipe.setTitle(title);
        recipe.setDate(date);
        recipe.setIngredients(ingredients);
        recipe.setPreparationSteps(preparationSteps);
        recipe.setComment(comment);
    
        Nutrition nutrition = new Nutrition();
        nutrition.setCalories(calories);
        nutrition.setFat(fat);
        nutrition.setCarbohydrates(carbohydrates);
        nutrition.setProtein(protein);
        recipe.setNutrition(nutrition);
    
        return recipe;
    }
    
    public List<String> listTitleRecipe(){
        return this.recipeList.stream()
            .map(Recipe::getTitle)
            .toList();
    }

    public int sumOfEggs(){
        return this.recipeList.stream()
        .flatMap(recipe -> recipe.getIngredients().stream()) // Convertit la liste d'ingrédients de chaque recette en un flux unique d'ingrédients
            .filter(ingredient -> ingredient.getName().equalsIgnoreCase("eggs")) // Filtrer les ingrédients pour ceux qui sont des œufs
            .mapToInt(ingredient -> Integer.parseInt(ingredient.getAmount())) // Mapper chaque ingrédient à son nombre
            .sum(); // Somme des nombres d'œufs
    }

    public List<Recipe> recipeWithOliveOil() {
        
        return this.recipeList.stream()
            .filter(recipe -> recipe.getIngredients().stream()
                .anyMatch(ingredient -> ingredient.getName().equalsIgnoreCase("olive oil")))
            .toList();
    }


    public Map<String, Integer> sumOfEggsInRecipe() {
        return recipeList.stream()
                .collect(Collectors.toMap(
                        Recipe::getTitle,
                        recipe -> recipe.getIngredients().stream()
                                .filter(ingredient -> ingredient.getName().equalsIgnoreCase("eggs"))
                                .mapToInt(ingredient -> Integer.parseInt(ingredient.getAmount()))
                                .sum()
                ));
    }
    


    public List<Recipe> recipeInf500Cal(){
        return this.recipeList.stream()
            .filter(recipe -> recipe.getNutrition().getCalories() < 500)
             .toList();
            
    }

    public double zuppaInglesSugarCount(){
        
        return this.recipeList.stream()
            .filter(recipe -> recipe.getTitle().equals("Zuppa Inglese"))
            .flatMap(recipe->recipe.getIngredients().stream())
            .filter(ingredient -> ingredient.getName().equalsIgnoreCase("sugar"))
            .mapToDouble(ingredient -> Double.parseDouble(ingredient.getAmount()))
            .sum();
            

    }
    public List<String> StepsOfZuppaInglese(){
        List<String> stepOfZuppaIngleseRecipe = new ArrayList<>();
        Recipe zuppaIngleseRecipe = this.recipeList.stream()
                                        .filter(recipe -> recipe.getTitle().equals("Zuppa Inglese"))
                                        .findFirst()
                                        .orElse(null);
    
        if( zuppaIngleseRecipe != null && zuppaIngleseRecipe.getPreparationSteps().size() >= 2){
            stepOfZuppaIngleseRecipe.add( zuppaIngleseRecipe.getPreparationSteps().get(0));
            stepOfZuppaIngleseRecipe.add( zuppaIngleseRecipe.getPreparationSteps().get(2));
        }

        return stepOfZuppaIngleseRecipe;
    }

    public List<Recipe> eachRecipesWith5preps(){
        return this.recipeList.stream()
            .filter(recipe -> recipe.getPreparationSteps().size() > 5)
            .toList();
    }

    public List<Recipe> eachRecipesWithoutButter() {
        return this.recipeList.stream()
            .filter(recipe -> recipe.getIngredients().stream()
                .noneMatch(ingredient -> ingredient.getName().equals("butter")))
                .toList();
    }
    

    public List<Recipe> getRecipesWithCommonIngredients() {
    // Récupérer la recette Zuppa Inglese
        Recipe zuppaIngleseRecipe = this.recipeList.stream()
                .filter(recipe -> recipe.getTitle().equals("Zuppa Inglese"))
                .findFirst()
                .orElse(null);

        if (zuppaIngleseRecipe != null) {
            // Récupérer les ingrédients de la recette Zuppa Inglese
            List<String> zuppaIngleseIngredients = zuppaIngleseRecipe.getIngredients().stream()
                    .map(Ingredient::getName)
                    .collect(Collectors.toList());

            // Filtrer les recettes ayant des ingrédients en commun avec la recette Zuppa Inglese
            return this.recipeList.stream()
                    .filter(recipe -> recipe != zuppaIngleseRecipe) // Exclure la recette Zuppa Inglese 
                    .filter(recipe -> recipe.getIngredients().stream()
                            .anyMatch(ingredient -> zuppaIngleseIngredients.contains(ingredient.getName())))

                    .collect(Collectors.toList());
        } else {
            System.out.println("La recette Zuppa Inglese n'a pas été trouvée.");
            return Collections.emptyList();
        }
    }

    public Recipe MostCaloricRecipe() {
        // Recherche de la recette la plus calorique
        Recipe mostCaloricRecipe = this.recipeList.stream()
                .max(Comparator.comparingInt(recipe -> recipe.getNutrition().getCalories()))
                .orElse(null);

        if (mostCaloricRecipe != null) {
            return mostCaloricRecipe;
        } else {
            return null;
        }
    }
   
    public String findMostFrequentUnit() {
       
        Map<String, Long> unitCounts = recipeList.stream()
                .flatMap(recipe -> recipe.getIngredients().stream())
                .filter(ingredient -> ingredient.getUnit() != null && !ingredient.getUnit().isEmpty())
                .collect(Collectors.groupingBy(Ingredient::getUnit, Collectors.counting()));
    
       
        return unitCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    

    public Map<String, Integer> countIngredientsPerRecipe(){
        return recipeList.stream()
            .collect(Collectors.toMap(Recipe::getTitle, recipe -> recipe.getIngredients().size()));
    }

    public Recipe findMostFatRecipe() {
        Recipe mostFatRecipe  = this.recipeList.stream()
            .max(Comparator.comparingInt(recipe -> Integer.parseInt(recipe.getNutrition().getFat().replaceAll("%", ""))))// on supprime le symbole avec replaceAll("%", "") avant de convertir la chaîne résultante en un entier
            .orElse(null);

        if (mostFatRecipe != null) {
            return mostFatRecipe;
        } else {
            return null;
        }
    }

    public String findMostUsedIngredient() {
        // Étape 1 : Regrouper les ingrédients par leur nom et compter les occurrences
        Map<String, Long> ingredientCounts = recipeList.stream()
                .flatMap(recipe -> recipe.getIngredients().stream())
                .collect(Collectors.groupingBy(Ingredient::getName, Collectors.counting()));
    
        // Étape 2 : Trouver l'ingrédient avec le plus grand nombre d'occurrences
        return ingredientCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public List<Recipe> sortByIngredientCount() {
        // Étape 1 : Créer une comparaison basée sur le nombre d'ingrédients de chaque recette
        Comparator<Recipe> byIngredientCount = Comparator.comparingInt(recipe -> recipe.getIngredients().size());
    
        // Étape 2 : Utiliser cette comparaison pour trier les recettes
        return recipeList.stream()
                .sorted(byIngredientCount)
                .collect(Collectors.toList());
    }

    public Map<String, Integer> countCalPerRecipe(){
        return recipeList.stream()
            .collect(Collectors.toMap(Recipe::getTitle, recipe -> recipe.getNutrition().getCalories()));
    }

    public Recipe findEasiestRecipe(){
        return this.recipeList.stream()
                    .min(Comparator.comparingInt(recipe -> recipe.getPreparationSteps().size()))
                    .orElse(null);
    }

   

                            
                        
}
