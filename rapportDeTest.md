# Rapport de Tests

## Entête

- Groupe de TD : 2-1
- Date de démarrage : 15 juin 2023
- Version : 1.0

## Sommaire
1. [Identification des points de vigilance de l'application](#partie1)
   
2. [Implémentation des tests fonctionnels](#partie2)
    1. [Tester les fonctions de l'API](#partie1.1)
    2. [Implémentation des différentes classes et fonctions du projet](#partie1.2)

## 1 - Identification des points de vigilance de l'application <a name="partie1"></a>


**Contrôlabilité** : Assurez-vous que l'application permet de contrôler facilement les fonctionnalités et les scénarios du jeu lors des tests, en fournissant des interfaces claires et des mécanismes de contrôle appropriés.

**Conservabilité** : Documentez les tests de manière détaillée et maintenez une infrastructure de tests solide pour faciliter la reproduction des tests et suivre l'évolution de l'application au fil du temps.

**Intégrité** : Vérifiez la fiabilité et la cohérence des tests en utilisant des assertions précises pour s'assurer que le comportement de l'application est conforme aux règles du jeu et aux attentes des utilisateurs.

**Extensibilité** : Anticipez les évolutions potentielles du jeu et concevez les tests de manière à pouvoir facilement les adapter en cas de modifications ultérieures.

**Automatisation des tests** : Mettez en place des tests automatisés, tels que des tests unitaires, des tests d'intégration et des tests fonctionnels, pour améliorer l'efficacité et la répétabilité des tests.

**Traçabilité des tests** : Établissez une relation claire entre les tests et les exigences du projet afin de garantir une couverture adéquate des fonctionnalités critiques du jeu.


## 2 - Implémentation des tests fonctionnels <a name="partie2"></a>

#### 1.1 - Tester les fonctions de l'API : <a name="partie1.1"></a>
- **Tester les scénarios d'utilisation courants et limites**:
    1. **Classe `Connector`:**
        - *Fonction `keepDices`:*
            - Vérifier que l'on peut:
                1. Garder un dé valide (pas déjà pris et présent dans les dés lancés)
            - Vérifier que l'on ne peut pas :
                1. Garder un dé déjà pris précédemment
                2. Garder un dé non présent dans les dés lancés
        - *Fonction `rollDices`:*
            - Vérifier que la taille de la liste renvoyée par la fonction est bien égale à `8 - nombre_de_dés_choisis`
        - *Fonction `takePickomino`:*
            - Dans la partie centrale, on peut :
                1. Piocher un pickomino ayant la valeur égale à la somme des dés
                2. Piocher le pickomino ayant la valeur maximale parmi les pickominos disponibles ayant une valeur inférieur à la somme des dés
            - Dans la partie centrale, on ne peut pas :
                1. Piocher un pickomino ayant un valeur supérieure à la somme des dés
                2. Piocher un pickomino qui a une valeur inférieure à la somme des dés mais qui n'a pas la valeur maximale
            - Dans la pile des autres, on peut :
                1. Voler le pickomino en haut de la pile d'un autre si la somme des dés est égale à la valeur du pickomino volé
            - Dans la pile des autres, on ne peut pas:
                1. Voler l'un des pickominos dans la pile d'un autre s'il n'est pas en haut de la pile
                2. Voler le pickomino en haut de la pile si la somme des dés n'est pas égale à celui-ci 
                3. Voler le pickomino en haut de sa propre pile
            - Piocher un pickomino sans avoir gardé de vers

    2. **Classe `Game`:**
        - *Fonction `accessiblePickos`:*
            - Vérifier que tous les pickominos sont dans la liste à l'initialisation d'une partie
            - Vérifier qu'un pickomino pioché par un joueur n'est plus dans la liste
            - Vérifier que lorsqu'un joueur perd, le pickomino en haut de sa pile est remise au milieu et que le pickomino ayant la valeur maximale présente dans la partie centrale disparaît
        - *Fonction `pickosStackTops`:*
            - Vérifier que la longeur de la liste est égale au nombre de joueur de la partie
            - Vérifier qu'à l'initialisation d'un partie, la liste est remplie de `0`
            - Vérifier que le pickomino en haut de la pile d'un joueur est actualisé quand:
                1. Le joueur pioche un pickomino
                2. Le joueur perd
                3. Le joueur se fait voler son pickomino
            - Vérifier que si un joueur n'a plus de pickominos, la valeur du haut de sa pile est égale à `0`
        - *Fonction `score`:*
            - Vérifier que la longeur de la liste est égale au nombre de joueur de la partie
            - Vérifier que le score de chaque joueur est égal à `0` à l'initialisation de la partie
            - Vérifier que le score d'un joueur est actualisé quand:
                1. Le joueur pioche un pickomino
                2. Le joueur perd
                3. Le joueur se fait voler son pickomino
    
    3. Vérifier que la partie se termine quand il n'y a plus de pickominos dans la partie centrale
    4. Vérifier que la fonction `finalScore` n'est accessible que lorsque la partie est finie

#### 1.2 - Implémentation des différentes classes et fonctions du projet : <a name="partie1.2"></a>

- **Classe `Player`:**
    - *Fonction `toString`:*
        - Vérifier que les informations entrées dans le constructeur concordent bien avec celles renvoyées

- **Classe `PickominoGame`:**
    - Vérifier que les joueurs sont bien assignés
    - Vérifier que le joueur courant est bien égal au premier joueur de la liste
    - *Fonction `throwDices`:*
        - Vérifier que l'on ne peut jeter des dés que lorsque l'état est égal à `STATUS.ROLL_DICE`
    - *Fonction `getPlayers`:*
        - Vérifier que la liste des joueurs renvoyée a les bons noms et dans le bon ordre
    - *Fonction `getPlayerById`:*
        - Vérifier que le joueur renvoyé est le bon
    - *Fonction `getDiceScore`:*
        - *La fonction nous permet d'avoir le score total des dés gardés*
        - Vérifier que la somme renvoyée concorde bien avec les dés gardés
        - Vérifier qu'une liste vide renvoie `0`
    - *Fonciton `getCurrentPlayer`*:
        - Vérifier que la fonction renvoie une erreur lorsqu'une partie n'est pas lancée
        - Vérifier que le joueur courant change bien au fil de la partie
