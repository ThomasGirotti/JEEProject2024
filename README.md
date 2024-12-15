# Projet Budae

Budae est une application de collection de personnages.
L'application peut gérer plusieurs utilisateurs ayant chacun leur collection.
Chaque Utilisateur peut roll des personnages de manière aléatoire et les claim.
Un système d'échange est également intégré entre joueurs.

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

#### Option 1:
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

#### Option 2:
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

