
											Programmation Fonctionnelle - L3 Informatique

											    Mini Compte Rendu - Répartition des taches 

											  (Solano Fortes Vasconcelos - Alexandre Sen)



 // -- || SI VOUS ETES SUR WINDOWS, UTILISEZ LE FICHIER 'launcher.bat' POUR LANCER NOTRE FICHIER JAR, OU SOIT, OUVREZ CMD ET TAPEZ LA COMMANDE SUIVANTE: 'java --enable-preview -jar Projet.jar' || -- \\



 // -- || Pour commencer, appuyer sur un des deux modes d'affichage pour afficher les requetes disponibles || -- \\


 -- Objectif: --

Le but de ce mini projet est de créer une application Java qui permet de manipuler les informations provenant d’un fichier de données en s’appuyant sur un raisonnement fonctionnel à travers l’API Stream.
Le fichier “recipes.xml” décrit un ensemble de recettes, et l’objectif est d’effectuer des traitements efficaces sur ces données en utilisant le pattern Map/Filter/Reduce.


Alexandre: Analyse du fichier XML pour extraire les données et mieux les organiser pendant la création des méthodes.
           Implémentation/Création de la classe Recepie dans le package models.
	   Implémentation/Creation de la classe RecepieRepo dans le package repositories.
	   Implémentation/Creation de la classe Nutrition.
	   Implémentation/Creation de la classe Recipe.
           Tests des différents classes.
	   Implémentation des différentes méthodes.
	   Création et programmation de l'interface Graphique en Swing pour afficher les différentes methodes créés dans la classe RecepieRepo
	   Debuggage
		
	   
		

Solano: Analyse du fichier XML pour extraire les données et mieux les organiser pendant la création des méthodes.
	Implémentation du package présentation avec la classe recepiePrincipale.
	Implémentation/Creation de la classe Ingredients.
	Création et programmation de l'interface Graphique en Swing pour afficher les différentes methodes créés dans la classe RecepieRepo (On a eu des problemes avec JavaFX, meme si celui ci est beaucoup mieux, et recommandé, l'implémentation de ceci n'est pas facile et casse tete, donc on a préféré opter pour une implémentation simple et éfficace mais limité en terme de design et support, le Java Swing).
	Creation de l'éxécutable.
	Tests des différents classes.
	Implémentation des différentes méthodes
	Debuggage




Lors de la creation l'interface utilisateur, nous avons fait face à quelques difficultés, notamment au niveau du graphique qui nous a prit pas mal de temps, mais grace aux différents sites et l'aide de l'IA nous avons régler les soucis.
Malheureusement nous n'avons pas pu faire la question 20 et 21.




 -- L’API Graphique - Java Swing -- 

Pour l’API Graphique nous avons décidé d’utiliser le Java Swing pour faire un design assez simple qui nous permet de traiter les données.

Nous avons créé un petit panel nommé “Mode de Présentation” qui permet de choisir si on veut afficher les données en textuel ou si nous voulons afficher les données à l’aide d’un graphique.

Le mode graphique ne sera pas disponible pour toutes les données car pour certaines questions, le besoin d’un graphique est inutile donc on optera pour un résultat textuel.

En fonction du mode de presentation choisi, on aura un menu différent, pour le menu des traitements textuels, toutes les requetes sont possibles, mais pour le menu des traitements graphique, il n'y a que certaines requetes possibles ce qui est logique.

Pour les différents traitements possibles, nous avons opté pour un petit menu scrollable. Lorsqu’on appuie sur le menu, on pourra faire défiler les traitements possibles, ensuite on appuie sur le bouton Exécuter qui exécutera le traitement choisi.



 -- Conclusion: --

En conclusion, ce projet nous à permit d’acquérir des compétences importantes dans le développement logiciel en Java en particulier dans le domaine des applications graphiques et du traitement de données.

De plus, en termes d’application graphique, nous pouvons observer que le Java Swing est très limité en termes de design et style, le JavaFX aurait été plus intéressant dans ce projet, mais suite à quelques problèmes nous avons préféré utiliser le Java Swing.
