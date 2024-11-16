# Online Bookstore

This is the backend application for the Online Bookstore, built using Spring Boot. It provides RESTful APIs for managing books, shopping carts, and Users.

## Prerequisites
- Java 17
- Maven
- H2 Database

## Setup Instructions
1. **Clone the repository:**
   ```bash
   git clone https://github.com/cssaranya/OnlineBookstore.git
   cd OnlineBookstore
   
2. Configure the database if needed : Update the src/main/resources/application.properties file with your database configuration.
   For H2, the default configuration is already provided and the H2 database is available at http://localhost:8080/h2-console with below properties after step 4.
   - JDBC URL =jdbc:h2:mem:onlinebookstoredb
   - Driver Class = org.h2.Driver
   - User Name = user
   - Password = password   
   
3. **Build the project:**
   ```bash
   mvn clean install
   
4. **Run the application:**
   ```bash
   mvn spring-boot:run
The application will start on http://localhost:8080.

5. **Running Tests**
    ```bash
    mvn test

## API Endpoints
The endpoints can be accessed at http://localhost:8080/swagger-ui.html <br> 
*All endpoints except user registration need authentication. The credentials have to be added using the Authorize icon on the top right*

1. **User Management**
- POST /users/register - Register new user
- GET /users/getById/{id} - Retrieve a user by ID
- PUT /users/update/{id} - Update a user

2. **Book Management**
- GET /books/allBooks - Retrieve all books
- GET /books//bookById/{id} - Retrieve a book by ID
- POST /books/saveBook - Add a new book
- GET /books//bookById/{title} - Retrieve a book by title
- GET /books//bookById/{author} - Retrieve a book by author

3. **Shopping Cart Management**
- GET /cart/getCart/{userId} - Retrieve the shopping cart of a user
- POST /cart/createCart/{userId} - Create a cart for the user
- POST /cart/add/{userId}/items/{bookId} - Add a book item to the cart
- PUT /cart/update/items/{itemId} - Update item quantity in the cart
- DELETE /cart/delete/items/{itemId} - Remove an item from the cart

4. **Order Management**
- GET /orders/getOrder/{userId} - Retrieve all user orders
- POST /orders/createOrder/{userId} - Create a new order
- GET /orders/orderDetails/{orderId} - Retrieve details of an order

## Usage
1. Register a new user or log in with existing credentials (User and Password = Saranya).
2. Modify you user.
3. Add books to your shopping cart.
4. View and manage your shopping cart.



   