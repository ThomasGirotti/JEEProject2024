# Projet Budae

Budae est un jeu de type gacha qui offre aux utilisateurs la possibilité de découvrir, collectionner et échanger des personnages réels et fictifs, chacun ayant une valeur prédéfinie.  
Toutes les heures, chaque joueur peut effectuer un "roll", qui fait apparaître 10 personnages aléatoires issus de la base de données.  
Les utilisateurs peuvent ensuite "claim" leurs personnages préférés pour les ajouter à leur collection.  
Les personnages déjà possédé par l'utilisateur peuvent être "boost" une fois toutes les 60 minutes pour augmenter leur valeur de 10%.  

En complément de cette mécanique principale, l'application propose :

Un "hôtel de trade" pour échanger des personnages librement. Les utilisateurs peuvent proposer des personnages à l'échange pour que les autres joueurs proposent à leurs tours des personnages en échange. Le joueur qui a proposé l'échange peut alors accepter ou refuser les propositions reçues.  
Un classement des meilleurs personnages et des meilleurs joueurs.
Un système de "follow" permettant de suivre d'autres utilisateurs.

Ce jeu combine la gestion, l'échange et la compétition pour offrir une expérience de collection multijoueur à ses utilisateurs.

## Fonctionnalités

- **Rolls Aléatoires**: Obtenez un personnage aléatoire, rolls disponibles toutes les heures
- **Claim de personnages**: Ajoutez un personnage roll à votre collection, claim disponible toutes les 120 minutes
- **Boosts des personnages**: Boost de la valeur des personnages que vous possédez déjà, boost disponible toutes les 60 minutes
- **Tri de la collection**: Triez votre collection de manière personnalisé, par valeur de personnage ou par nom de personnage
- **Echanges entre joueurs**: Proposez des personnages à l'échange et acceptez les propositions des autres joueurs
- **Follow d'autres joueurs**: Suivez d'autres utilisateurs pour y accéder rapidement et surveiller leur collection

## Installation

### Pré-requis

- Java 17
- Un navigateur internet (Google Chrome de préférence)

### Étapes

1. Clonez le dépôt:

   ```bash
   git clone https://github.com/ThomasGirotti/JEEProject2024.git
   ```

#### Option 1

2. Ouvrez le dépôt cloné:

   ```bash
   cd JEEProject2024/collection
   ```

3. Lancez l'application (Windows ou Linux) :

   ```cmd
   mvnw.cmd spring-boot:run
   ```

   ```bash
   mvn spring-boot:run
   ```

4. Ouvrez votre navigateur et rendez-vous sur :

   ```bash
   http://localhost:8080
   ```

#### Option 2

2. Ouvrez le projet dans votre IDE (comme VSCode)
3. Naviguez jusqu'à `JEEProject2024\collection\src\main\java\com\jeemudae\collection`
4. Exécutez `CollectionManagerApplication.java`
5. Ouvrez votre navigateur et rendez-vous sur :

   ```bash
   http://localhost:8080
   ```

## Utilisation

### Connectez-vous ou créez un compte

#### Option 1: Utilisateur Existant

Connectez-vous avec les identifiants pré-créés :

- Nom d'utilisateur: player
- Mot de passe: player

#### Option 2: Nouvel Utilisateur

Créez un compte avec les informations nécessaires, puis connectez-vous.

#### Option 3: Administrateur Existant

Afin de rajouter ou supprimer des personnages, connectez-vous avec le compte administrateur :

- Nom d'utilisateur: budae
- Mot de passe: budaeadmin-1656

## Auteurs

CY Tech Pau

- Thomas Girotti
- Lilian Delétoile
- Volodia Gromykhov
- Marc-Antoine Orecchioni

## Auto-évaluation

### Fonctionnalités : **4 points**

- L'application couvre l'ensemble des fonctionnalités demandées dans la grille d'évaluation : *cinq entités connectées par divers types de relations (1-1, 1-N, N-1, N-N), interface graphique avec gestion des associations, et logique métier intégrée.*

- L'application permet d'insérer, supprimer, et chercher une entité en base de données :
  - **Création** : *Ajout d'utilisateurs et de personnages.*
  - **Mise à jour** : *Modification des personnages et des collections.*
  - **Suppression** : *Suppression des personnages.*
  - **Recherche** : *Consultation des utilisateurs.*

- La gestion des relations entre entités est incluse : *il est possible de relier une entité à une autre en base de données.*

---

### Technique : **4 points**

Pour réaliser notre projet nous avons créé un certain nombre d'entité avec un total de 5 :

`character_set` qui définie l'ensemble des personnes et définie leurs comportements  
`collection_sets` qui permet de stocker les tous les personnages dans une liste  
`trades` qui gère la fonctionnalité de trade des personnages entre les joueurs  
`users` qui stocke les informations des comptes  
`user_follow` qui permet de gérer le follow entre les users  

Nous avons utiliser les différents outils demandé afin de mener a bien la finalité de ce projet à savoir :

- Springboot
- Spring Data JPA
- H2 Data Base
- Thymleaf
- Modéle MVC (model, vue, controller)

On a implémenté différente fonction d'un CRUD avec :

- Ajouter/supprimer des personages dans la BDD
- Ajouter/supprimer/modifier les personnages appartenant aux joueurs par le biais de roll, vente et trade
- Ajouter/supprimer le choix de follow ou non les autres joueurs
- Ajouter des utilisateurs

- Les contrôleurs utilisent différentes méthodes HTTP selon les besoins : *GET pour afficher les pages et POST pour gérer l'ajout, la modification et la suppression des données.*

- Les données affichées dans les vues sont strictement celles transmises par les contrôleurs, garantissant une séparation claire entre la logique des données et leur affichage.

---

### Qualité : **4 points**

- L'application présente un design soigné, grâce à l'intégration de *Tailwind CSS*.

- Le code source est disponible sur un dépôt Git (GitHub/GitLab) et suit une structure organisée : *arborescence claire, utilisation de branches et dossiers dédiés.*

- Le dépôt montre une contribution de chaque membre via des commits : *toutes les tâches ont été réparties équitablement, et la collaboration a assuré une progression collective.*

---

### Conclusion

Points forts :

- Nous avons réalisé un projet complet et fonctionnel, qui couvre l'ensemble des fonctionnalités demandées.
- L'application est intuitive et agréable à utiliser, et offre une expérience de jeu multijoueur complète.
- Nous avons suivi les bonnes pratiques de développement et de gestion de projet, et avons travaillé en équipe pour mener à bien ce projet.
- La logique métier est bien séparée de l'interface graphique (modèle MVC), intégrant les différentes entités et relations demandées.

Points à améliorer :

- Peaufiner le design de l'application pour une expérience utilisateur optimale.
- Répartir les tâches de manière plus équilibrée pour garantir une progression collective.
- Regrouper les méthodes similaires dans des classes utilitaires pour une meilleure organisation du code.
