# P7 - Poseidon Capital Solutions

Application web de gestion de données financières, développée avec **Spring Boot**, **Thymeleaf** et **Bootstrap**.

---

## 🛠 Technologies utilisées

- **Spring Boot** 4  
- **Java** 17  
- **Thymeleaf** (moteur de templates)  
- **Bootstrap** v4.3.1  

---

## ⚡ Lancement de l'application

### 1️) Créer la variable d'environnement pour la base de données

> **Windows** :

1. Chercher "Variables d'environnement" dans la barre de recherche Windows  
2. Cliquer sur **Variables d'environnement...**  
3. Dans **Variables système**, ajouter une nouvelle variable :  
   - **Nom de la variable** : `spring.datasource.password`  
   - **Valeur** : `admin`  

---

### 2️) Lancer l'application

1. Ouvrir l'invite de commande et se placer dans le répertoire du projet.  
2. Exécuter la commande :  
```bash
mvn spring-boot:run
