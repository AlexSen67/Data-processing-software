//Copyright © Alexandre Sen / Solano Fortes Vasconcelos
package Presentation;

import javax.swing.*;

import Repositories.RecepieRepo;
import models.Recipe;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.List;


public class RecipePrincipale extends JFrame {
    private JComboBox<String> traitementsComboBox;
    private JTextArea recipeDetailsTextArea;
    private RecepieRepo recepieRepo = new RecepieRepo();
    
   

    public RecipePrincipale() {
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);

        recepieRepo.init();

        // Panel pour le mode de présentation
        JPanel presentationPanel = new JPanel();
        presentationPanel.setBorder(BorderFactory.createTitledBorder("Mode de présentation"));
        JRadioButton textuelButton = new JRadioButton("Textuel");
        JRadioButton graphiqueButton = new JRadioButton("Graphique");
        ButtonGroup presentationGroup = new ButtonGroup();
        presentationGroup.add(textuelButton);
        presentationGroup.add(graphiqueButton);
        presentationPanel.add(textuelButton);
        presentationPanel.add(graphiqueButton);

        // Panel pour les traitements possibles
        JPanel traitementsPanel = new JPanel();
        traitementsPanel.setBorder(BorderFactory.createTitledBorder("Traitements possibles"));
        traitementsComboBox = new JComboBox<>();
        JButton executerButton = new JButton("Exécuter");
        traitementsPanel.add(traitementsComboBox);
        traitementsPanel.add(executerButton);

        // Panel pour afficher les détails de la recette
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Détails du Traitement"));
        recipeDetailsTextArea = new JTextArea(10, 50);
        recipeDetailsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(recipeDetailsTextArea);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);
        

        // Ajout des panels à la fenêtre
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(presentationPanel, BorderLayout.NORTH);
        getContentPane().add(traitementsPanel, BorderLayout.WEST);
        getContentPane().add(detailsPanel, BorderLayout.CENTER);

        // Ajout des écouteurs d'événements
        executerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTraitement = (String) traitementsComboBox.getSelectedItem();
                System.out.println("Traitement sélectionné : " + selectedTraitement);
                // Appel des méthodes correspondant au traitement sélectionné et mise à jour de l'affichage des détails de la recette
                updateRecipeDetails(selectedTraitement);
            }
        });

        textuelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textuelButton.isSelected()) {
                    System.out.println("Mode de présentation : Textuel");
                    recipeDetailsTextArea.setText("");
                    // Mettre à jour les options de la liste déroulante pour le mode textuel
                    traitementsComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"lister les titres des recettes", "calculer le nombre total d’œufs utilisés", "retourner les recettes utilisant l’huile d’olive"
                ,"calculer le nombre d’œufs par recette","les recettes avec moins de 500 calories","quantité de sucre utilisée par la recette Zuppa Inglese","les 2 premières étapes de la recette Zuppa Inglese","les recettes qui nécessitent plus de 5 étapes"
            ,"les recettes qui ne contiennent pas de beure","les recettes qui sont en commun la recette Zuppa Inglese","la recette la plus calorique","l’unité la plus fréquente","calcule le nombre d’ingrédients par recette","la recette comportant le plus de fat","l’ingrédient le plus utilisé","affiche les recettes triées par nombre d’ingrédients","la recette la plus facile (avec le moins d’étape)"}));
                }
            }
        });

        graphiqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (graphiqueButton.isSelected()) {
                    System.out.println("Mode de présentation : Graphique");
                    recipeDetailsTextArea.setText("");
                    // Mettre à jour les options de la liste déroulante pour le mode graphique
                    traitementsComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"recettes avec nombre d'ingrédients(Graph)", "recettes avec Calories(Graph)", "nombre d’œufs par recette(Graph)"}));
                }
            }
        });
    }
    

    

    // Méthode pour mettre à jour l'affichage des détails de la recette
    public void updateRecipeDetails(String traitement) {
        if (traitement.equals("lister les titres des recettes")) {
            // Afficher les titres des recettes
            List<String> titles = recepieRepo.listTitleRecipe();
            StringBuilder details = new StringBuilder("Liste des titres des recettes :\n");
            for (String title : titles) {
                details.append(title).append("\n");
            }
            recipeDetailsTextArea.setText(details.toString());
        } else if (traitement.equals("calculer le nombre total d’œufs utilisés")) {
            // Calculer le nombre total d'œufs utilisés
            int nbEggs = recepieRepo.sumOfEggs();
            StringBuilder details = new StringBuilder("Nombre total d’œufs utilisés : ");
            details.append(nbEggs).append("\n");
            recipeDetailsTextArea.setText(details.toString());
        } else if (traitement.equals("retourner les recettes utilisant l’huile d’olive")) {
            // Retourner les recettes utilisant l'huile d'olive
            List<Recipe> recipes = recepieRepo.recipeWithOliveOil();
            StringBuilder details = new StringBuilder("Recettes utilisant l’huile d’olive :\n");
            for (Recipe recipe : recipes) {
                // Ajouter les détails de chaque recette à la chaîne de détails
                details.append("Titre : ").append(recipe.getTitle()).append("\n");
                details.append("Ingrédients : ").append(recipe.getIngredients()).append("\n");
                
                // Ajouter les étapes de la recette avec "Step i:"
                List<String> steps = recipe.getPreparationSteps();
                for (int i = 0; i < steps.size(); i++) {
                    details.append("Étape ").append(i + 1).append(": ").append(steps.get(i)).append("\n");
                }
                
                details.append("Nutrition : ").append(recipe.getNutrition()).append("\n");
                details.append("Commentaire : ").append(recipe.getComment()).append("\n");
                details.append("\n");

                details.append("------------------------------------------------------------------------------------------------"+ "\n");
                details.append("\n");
            }
            recipeDetailsTextArea.setText(details.toString());
        }else if (traitement.equals("calculer le nombre d’œufs par recette")) {
            
            Map<String, Integer> mapRecipe = recepieRepo.sumOfEggsInRecipe();
            StringBuilder details = new StringBuilder("Nombre d’œufs par recette :\n");
            for (Map.Entry<String, Integer> entry : mapRecipe.entrySet()) {
               
                details.append("Recette : ").append(entry.getKey()).append(", Nombre d’œufs : ").append(entry.getValue()).append("\n");
            }
            
            recipeDetailsTextArea.setText(details.toString());
        }
        else if (traitement.equals("les recettes avec moins de 500 calories")) {
            
            List<Recipe> recipes = recepieRepo.recipeInf500Cal();
            StringBuilder details = new StringBuilder("Nombre d’œufs par recette :\n");
               
                for (Recipe recipe : recipes) {
                    // Ajouter les détails de chaque recette à la chaîne de détails
                    details.append("Titre : ").append(recipe.getTitle()).append("\n");
                    details.append("Ingrédients : ").append(recipe.getIngredients()).append("\n");
                    
                    // Ajouter les étapes de la recette avec "Step i:"
                    List<String> steps = recipe.getPreparationSteps();
                    for (int i = 0; i < steps.size(); i++) {
                        details.append("Étape ").append(i + 1).append(": ").append(steps.get(i)).append("\n");
                    }
                    
                    details.append("Nutrition : ").append(recipe.getNutrition()).append("\n");
                    details.append("Commentaire : ").append(recipe.getComment()).append("\n");
                    details.append("\n");
    
                    details.append("------------------------------------------------------------------------------------------------"+ "\n");
                    details.append("\n");
                }
            
            
            recipeDetailsTextArea.setText(details.toString());
        }else if (traitement.equals("quantité de sucre utilisée par la recette Zuppa Inglese")) {
            double count = recepieRepo.zuppaInglesSugarCount();
            StringBuilder details = new StringBuilder("");
            
            details.append("Quantité  de sucre utilisée par la recette Zuppa Inglese : ").append(count);
            
            recipeDetailsTextArea.setText(details.toString());

        }else if (traitement.equals("les 2 premières étapes de la recette Zuppa Inglese")) {
            List<String> s = recepieRepo.StepsOfZuppaInglese();
            StringBuilder details = new StringBuilder("les 2 premières étapes de la recette Zuppa Inglese :\n");
            
            details.append("Etape 1 : ").append(s.get(0)).append("\n");
            details.append("Etape 2 : ").append(s.get(1));
            
            recipeDetailsTextArea.setText(details.toString());
        }else if (traitement.equals("les recettes qui nécessitent plus de 5 étapes")) {
            
            List<Recipe> recipes = recepieRepo.eachRecipesWith5preps();
            StringBuilder details = new StringBuilder("les recettes qui nécessitent plus de 5 étapes :\n");
               
                for (Recipe recipe : recipes) {
                    // Ajouter les détails de chaque recette à la chaîne de détails
                    details.append("Titre : ").append(recipe.getTitle()).append("\n");
                    details.append("Ingrédients : ").append(recipe.getIngredients()).append("\n");
                    
                    // Ajouter les étapes de la recette avec "Step i:"
                    List<String> steps = recipe.getPreparationSteps();
                    for (int i = 0; i < steps.size(); i++) {
                        details.append("Étape ").append(i + 1).append(": ").append(steps.get(i)).append("\n");
                    }
                    
                    details.append("Nutrition : ").append(recipe.getNutrition()).append("\n");
                    details.append("Commentaire : ").append(recipe.getComment()).append("\n");
                    details.append("\n");
    
                    details.append("------------------------------------------------------------------------------------------------"+ "\n");
                    details.append("\n");
                }
        
            
            recipeDetailsTextArea.setText(details.toString());
        }else if (traitement.equals("les recettes qui ne contiennent pas de beure")) {
          
            List<Recipe> recipes = recepieRepo.eachRecipesWithoutButter();
            StringBuilder details = new StringBuilder("les recettes qui ne contiennent pas de beure :\n");
               
                for (Recipe recipe : recipes) {
                    // Ajouter les détails de chaque recette à la chaîne de détails
                    details.append("Titre : ").append(recipe.getTitle()).append("\n");
                    details.append("Ingrédients : ").append(recipe.getIngredients()).append("\n");
                    
                    // Ajouter les étapes de la recette avec "Step i:"
                    List<String> steps = recipe.getPreparationSteps();
                    for (int i = 0; i < steps.size(); i++) {
                        details.append("Étape ").append(i + 1).append(": ").append(steps.get(i)).append("\n");
                    }
                    
                    details.append("Nutrition : ").append(recipe.getNutrition()).append("\n");
                    details.append("Commentaire : ").append(recipe.getComment()).append("\n");
                    details.append("\n");
    
                    details.append("------------------------------------------------------------------------------------------------"+ "\n");
                    details.append("\n");
                }
        
            
            recipeDetailsTextArea.setText(details.toString());
        }
        else if (traitement.equals("la recette la plus calorique")) {
           
            Recipe recipe = recepieRepo.MostCaloricRecipe();
            StringBuilder details = new StringBuilder("la recette la plus calorique :\n");
               
             // Ajouter les détails de chaque recette à la chaîne de détails
             details.append("Titre : ").append(recipe.getTitle()).append("\n");
             details.append("Ingrédients : ").append(recipe.getIngredients()).append("\n");
             
             // Ajouter les étapes de la recette avec "Step i:"
             List<String> steps = recipe.getPreparationSteps();
             for (int i = 0; i < steps.size(); i++) {
                 details.append("Étape ").append(i + 1).append(": ").append(steps.get(i)).append("\n");
             }
             
             details.append("Nutrition : ").append(recipe.getNutrition()).append("\n");
             details.append("Commentaire : ").append(recipe.getComment()).append("\n");
             details.append("\n");

             details.append("------------------------------------------------------------------------------------------------"+ "\n");
             details.append("\n");
        
            
            recipeDetailsTextArea.setText(details.toString());
        }else if (traitement.equals("l’unité la plus fréquente")) {
            
            String s = recepieRepo.findMostFrequentUnit();
            StringBuilder details = new StringBuilder("");
               
            details.append("l’unité la plus fréquente : ").append(s);
        
            
            recipeDetailsTextArea.setText(details.toString());
        }else if (traitement.equals("calcule le nombre d’ingrédients par recette")) {
            
            Map<String, Integer> mapRecipe = recepieRepo.countIngredientsPerRecipe();
            StringBuilder details = new StringBuilder("nombre d’ingrédients par recette :\n");
            for (Map.Entry<String, Integer> entry : mapRecipe.entrySet()) {
               
                details.append("Recette : ").append(entry.getKey()).append(", Nombre d'ingrédients : ").append(entry.getValue()).append("\n");
            }
            
            recipeDetailsTextArea.setText(details.toString());
        }else if (traitement.equals("la recette comportant le plus de fat")) {
           
            Recipe recipe = recepieRepo.findMostFatRecipe();
            StringBuilder details = new StringBuilder("la recette la plus fat :\n");
               
             // Ajouter les détails de chaque recette à la chaîne de détails
             details.append("Titre : ").append(recipe.getTitle()).append("\n");
             details.append("Ingrédients : ").append(recipe.getIngredients()).append("\n");
             
             // Ajouter les étapes de la recette avec "Step i:"
             List<String> steps = recipe.getPreparationSteps();
             for (int i = 0; i < steps.size(); i++) {
                 details.append("Étape ").append(i + 1).append(": ").append(steps.get(i)).append("\n");
             }
             
             details.append("Nutrition : ").append(recipe.getNutrition()).append("\n");
             details.append("Commentaire : ").append(recipe.getComment()).append("\n");
             details.append("\n");

             details.append("------------------------------------------------------------------------------------------------"+ "\n");
             details.append("\n");
        
            
            recipeDetailsTextArea.setText(details.toString());
        }
        else if (traitement.equals("l’ingrédient le plus utilisé")) {
            
            String s = recepieRepo.findMostUsedIngredient();
            StringBuilder details = new StringBuilder("");
               
            details.append("l’ingrédient le plus utilisé : ").append(s);
        
            
            recipeDetailsTextArea.setText(details.toString());
        }else if (traitement.equals("affiche les recettes triées par nombre d’ingrédients")) {
          
            List<Recipe> recipes = recepieRepo.sortByIngredientCount();
            StringBuilder details = new StringBuilder("les recettes triées par nombre d’ingrédients :\n");
               
                for (Recipe recipe : recipes) {
                    // Ajouter les détails de chaque recette à la chaîne de détails
                    details.append("Recette : ").append(recipe.getTitle()).append(", Nombre d’ingrédients : ").append(recipe.getIngredients().size()).append("\n");
                    
                }
        
            
            recipeDetailsTextArea.setText(details.toString());
        }else if (traitement.equals("la recette la plus facile (avec le moins d’étape)")) {
          
            Recipe recipe = recepieRepo.findEasiestRecipe();
            StringBuilder details = new StringBuilder("la recette la plus facile :\n");
               
             // Ajouter les détails de chaque recette à la chaîne de détails
             details.append("Titre : ").append(recipe.getTitle()).append("\n");
             details.append("Ingrédients : ").append(recipe.getIngredients()).append("\n");
             
             // Ajouter les étapes de la recette avec "Step i:"
             List<String> steps = recipe.getPreparationSteps();
             for (int i = 0; i < steps.size(); i++) {
                 details.append("Étape ").append(i + 1).append(": ").append(steps.get(i)).append("\n");
             }
             
             details.append("Nutrition : ").append(recipe.getNutrition()).append("\n");
             details.append("Commentaire : ").append(recipe.getComment()).append("\n");
             details.append("\n");

             details.append("------------------------------------------------------------------------------------------------"+ "\n");
             details.append("\n");
        
            
            recipeDetailsTextArea.setText(details.toString());
        }else if (traitement.equals("recettes avec nombre d'ingrédients(Graph)")) {
            
            Map<String, Integer> data = recepieRepo.countIngredientsPerRecipe();
        
          
            Diagramme diagramme = new Diagramme(data);
        
            // Créer un nouveau cadre pour afficher le diagramme
            JFrame frame = new JFrame("Diagramme des recettes par nombre D'ingrédients");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.getContentPane().add(diagramme);
            frame.setVisible(true);
            frame.setResizable(false);
        }

        else if (traitement.equals("recettes avec Calories(Graph)")) {
          
            Map<String, Integer> data = recepieRepo.countCalPerRecipe();
        
            
            Diagramme diagramme = new Diagramme(data);
        
            // Créer un nouveau cadre pour afficher le diagramme
            JFrame frame = new JFrame("Diagramme des recettes par Calories");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.getContentPane().add(diagramme);
            frame.setVisible(true);
            frame.setResizable(false);
        }

        else if (traitement.equals("nombre d’œufs par recette(Graph)")) {
      
            Map<String, Integer> data = recepieRepo.sumOfEggsInRecipe();
        

            Diagramme diagramme = new Diagramme(data);
        
            // Créer un nouveau cadre pour afficher le diagramme
            JFrame frame = new JFrame("Diagramme d’œufs par recette");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.getContentPane().add(diagramme);
            frame.setVisible(true);
            frame.setResizable(false);
        }
        
        
        
        
    }


    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RecipePrincipale menu = new RecipePrincipale();
                menu.setVisible(true);
            }
        });
    }
}
