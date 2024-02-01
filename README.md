# E-Commerce Backend
This project is an e-commerce backend system designed with a layered architecture approach, using Java, ORM (Object-Relational Mapping), JPA (Java Persistence API), and Hibernate. The system is structured to manage and organize complex software projects by dividing the application into distinct layers, each responsible for specific aspects of the application functionality.

<h3>E-commerce Swagger API<h3>
<img src="https://github.com/AbdullahSalihOner/ECommerceBackend/blob/master/ProjectImages/Resim1.png" style = "width:80%"></img>

## Layered Architecture
The layered architecture model enhances maintainability and scalability by separating concerns into the following layers:

- Presentation (Controller) Layer: Manages the user interface and interactions. In web applications, it involves technologies like HTML, CSS, and JavaScript, while desktop applications might use Java-based interfaces. This layer handles user inputs, validates them, and forwards them to the Business layer.

- Business Layer: Governs core functionalities and business rules. It responds to requests from the Presentation layer, performs data operations, and prepares outcomes for either the user or the Data Access layer.

- Data Access (Repository) Layer: Communicates with databases or other data sources, managing database transactions, transferring data to the Business layer, and handling errors.

## Additional Components
- Configuration Layer: Contains various configuration settings such as database connections, security settings, and logging. This layer might also include the setup for tools like Swagger for easier endpoint management.

- DTO (Data Transfer Object) Layer: Used for transferring and presenting data objects, especially in formats not aligned with database tables like JSON or XML, facilitating client-server data exchange.

- Exceptions Layer: Includes custom exception classes managed by the application for handling error scenarios, improving error control across layers.

- Model Layer: Contains the application's business logic and data model, including database operations, data validation, and business rules.

- Repository Layer: Deals with database operations like data insertion, retrieval, updates, and deletions.

- Service Layer: The core layer containing business logic. It implements business rules, performs data access operations, and executes various functionalities. This layer is crucial for adding functionality to the project, with its services being called by the API layer.

## Configuration and Management Files
- application.properties: A configuration file used in Spring Boot applications for defining application settings.

- pom.xml: The Maven configuration file for managing project dependencies, plugins, and additional build configurations.

## Annotations Used
### JPA Annotations
- @Entity: Marks a class as a JPA entity, meaning it should be mapped to a database table. This annotation indicates that the class's instances will be persisted in the database.
- @Table: Specifies the table in the database with which the entity is associated. It allows specifying details like the table name, catalog, schema, and unique constraints.
- @Id: Designates the primary key of an entity. Each JPA entity must have at least one field annotated with @Id to define the entity's unique identifier.
- @GeneratedValue: Specifies the strategy for generating primary key values. It supports auto-increment, sequence, table, etc., strategies for generating primary keys.
### Lombok Annotations
- @AllArgsConstructor: A Lombok annotation that generates a constructor with one parameter for each field in the class. This simplifies the instantiation of objects.
- @NoArgsConstructor: Generates a no-argument constructor for the class, enhancing the ease of instantiation, especially for frameworks that require an empty constructor.
- @Data: A convenient shortcut that bundles the features of @ToString, @EqualsAndHashCode, @Getter, @Setter, and @RequiredArgsConstructor annotations in one. It is used to automatically generate boilerplate code like getters, setters, equals, hashCode, and toString methods.
### Spring Framework Annotations
- @Service: Indicates that a class is a service component in the Spring framework. It is used to mark the class as a service provider, making it eligible for business logic and service-layer functionalities.
- @Repository: Marks a class as a repository, which acts as a mechanism for encapsulating storage, retrieval, and search behavior. It abstracts access to the data layer.
- @Controller: Indicates that a class serves as a controller in a Spring MVC application. It is a specialization of @Component and is used to mark a class as a web request handler.
- @RestController: A convenience annotation that combines @Controller and @ResponseBody. It indicates that the class is a controller and every method returns a domain object instead of a view. It simplifies the creation of RESTful web services.
- @RequestMapping: Used for mapping web requests onto specific handler classes or handler methods. It can specify the HTTP method to listen for and the request URL among other parameters.
- @GetMapping: A composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET). It maps HTTP GET requests onto specific handler methods.
- @PostMapping: Similar to @GetMapping, but for HTTP POST requests. It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.POST).
- @Autowired: Enables automatic dependency injection. It allows Spring to resolve and inject collaborating beans into a bean. It can be applied to fields, setter methods, and constructors.

## ORM, JPA, and Hibernate
- ORM: A programming technique that maps object-oriented domain models to relational databases, serving as a bridge and standardizing database interactions.
- JPA: The Java standard for ORM, facilitating the management of relational data in Java applications.
- Hibernate: A robust JPA implementation that simplifies data persistence and retrieval in Java, making database interactions more efficient.

<img src="https://github.com/AbdullahSalihOner/ECommerceBackend/blob/master/ProjectImages/Resim2.png" style = "width:80%"></img>
<img src="https://github.com/AbdullahSalihOner/ECommerceBackend/blob/master/ProjectImages/Resim3.png" style = "width:80%"></img>
<img src="https://github.com/AbdullahSalihOner/ECommerceBackend/blob/master/ProjectImages/Resim4.png" style = "width:80%"></img>




## Getting Started
### To set up and run the project:

- Clone the repository.
- Ensure Java and Maven are installed on your machine.
- Configure the application.properties file with your database and other environment settings.
- Run mvn clean install to build the project and install dependencies.
- Start the application through your IDE or command line.


<h1>Ayrıntılı Proje Raporu</h1>

 <p>Proje raporunun Türkçe detayları burada yer alacaktır.</p>
 <p>Details of the project report will be displayed here.</p>
  https://github.com/AbdullahSalihOner/ECommerceBackend/blob/master/Proje_Rapor.pdf
<a href="[rapor.pdf](https://github.com/AbdullahSalihOner/ECommerceBackend/blob/master/Proje_Rapor.pdf)https://github.com/AbdullahSalihOner/ECommerceBackend/blob/master/Proje_Rapor.pdf" download="Proje_Raporu.pdf">

