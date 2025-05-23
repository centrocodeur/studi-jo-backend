# Application Backend Gestion et de réservation de ticket
 
Une application backend pour permettre à l'administrateur de site de créer les tickets, de les modifier et de les supprimer
Une application qui permet aux autilisateurs de reserver un billet électronique.
Cette application est créée avec **Spring Boot ** et une base de données ** MySql
Cette application permet d'effectuer les opérations CRUD(Create, Read, Update, Delete) sur les tickets.

## Technologies utilisées

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **MySqL**
- **Springdoc OpenAPI (Swagger UI)**
- **jjwt-api**
- **javaMail API**

## Fonctionnalitées

 # Administrateur
- **Créer un billet** : Créer un nouveau billet pour une compétition
- **Modifier un billet**: Modifier un billet déjà présent sur le site via son `id`
- **Supprimer un billet**: Supprimer un billet de la base de données.

# Utilisateur
- **S'inscrire sur le site**: l'utilisateur doit s'inscrire en utilisant fournissant son nom,prénom, email et un mot de passe
- **Connexion**: Après l'inscription l'utilisateur peut se connecter à utisant son email et son mot de passe
- **Réserver un billet**: l'utilsateur peut réserver un billet et recevoir par email.

## Endpoints de l'API

Custmomer 

| Méthode | Endpoint      | Description                    | Exemple de corps de requête |
|---------|---------------|--------------------------------|---------------------------------------------------------------------------------------------------|
| GET     | `/api/customer/ticket_cart/{userId}`           | Rechercher un ticket par identifiant                                                              |
| GET     | `/api/customer/ticket/coupon/{userId}/{code}`  | Utiliser un coupon de réduction                                                                   |
| POST    | `/api/customer/ticket_cart`                    | Ajouter un ticket au panier `{"userId": 1, "ticketId":20}`                                        |
| POST    | `/api/customer/ticket/addition`                | Augmenter le nombre de ticket du panier | `{"userId": 1, "ticketId":20}`                          |
| POST    | `/api/customer/ticket/deduction`               | Diminuer le nombre de ticket du panier | `{"userId": 1, "ticketId":20}`                           |
| POST    | `/api/customer/ticket/placeOrder`              | Payer de ticket du panier | `{"userId": 1, "payement":"VISA_CARD", "orderDescription": "Billet"}` |
| DELETE  | `/api/customer/ticket_cart/{userId}`           | Supprimer un ticket du panier                                                                     |
| GET     | `/api/customer/myOrders /{userId}`             | Supprimer un ticket du panier                                                                     | 
|--------------------------------------------------------------------------------------------------------------------------------------------------------------|

Authentification

| Méthode | Endpoint      | Description                    | Exemple de corps de requête |  Format
|---------|---------------|--------------------------------|---------------------------------------------------------------------------------------------------|
| POST     | `/sign-up`                        | Inscription    | `{"firstname": "Dupont", "lastname": "Eric", "email":"dupan@gmail.com", "password": "rete233"}` |
| POST     |`/authenticate`                    |  Authentication  |`{"username":"dupan@gmail.com" , "password": "rete233"}`  |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------


## Configuration

### Fichier `application.properties`

```properties
spring.datasource.url=
spring.datasource.password=
spring.datasource.username=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```


## 🚀 Lancer l'application

1. Clone ce repository.
2. Assure-toi d’avoir **Java 17** et **Maven** installés sur ta machine.
3. Exécute la commande suivante pour démarrer l’application :
   ```
   ./mvnw spring-boot:run
   ```
4. L’application sera disponible sur `http://localhost:8080`.

## 🧭 Documentation API

La documentation interactive de l’API est disponible via **Swagger UI** à l’adresse suivante :  
`http://localhost:8080/swagger-ui.html`
