[GitHub] The primary objective of this assignment is to build a backend Java web application — the Reservation Service API — for managing room reservations. The service uses Spring Boot, Spring Web, Hibernate/JPA, and HikariCP for efficient database interaction. It exposes REST endpoints for creating, updating, approving, and canceling reservations.

Getting started: You can run the application locally by opening a terminal. 

Step 1: Install JDK 21+ and Maven. 

Step 2: Open a terminal and point to the project root folder (where pom.xml is located). 

Step 3: Build the project and create the JAR artifact with mvn clean package, which produces target/{artifact-name}.jar. 

Step 4: Run the application using mvn spring-boot:run or java -jar target/{artifact-name}.jar. Verify the API is reachable at http://localhost:8080/reservation.

Database setup: The project requires a relational database (PostgreSQL/MySQL). DDL scripts are located in src/main/resources/ddl.sql.

Configuration: Database credentials must be provided externally (not hardcoded). The application reads connection settings from environment variables or application.properties. Required environment variables are: DB_URL (JDBC URL, e.g. jdbc:postgresql://localhost:5432/reservation_db), DB_LOGIN (database username), and DB_PASSWORD (database password). On Windows PowerShell you can set them with:
setx DB_LOGIN "your_login"  
setx DB_PASSWORD "your_password"  
setx DB_URL "jdbc:postgresql://localhost:5432/reservation_db"

API Endpoints: GET /reservation/reservation/{id} retrieves a reservation by ID, GET /reservation lists reservations with optional filters (roomId, userId, pagination), POST /reservation creates a reservation, PUT /reservation/{id} updates a reservation, DELETE /reservation/{id}/cancel cancels a reservation, and POST /reservation/{id}/approve approves a reservation.