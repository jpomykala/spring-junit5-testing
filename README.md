# spring-junit5-testing
Playing with JUnit5 
Spring Boot version `2.1.1`

- Testing repository layer with `EntityManager` and `Spring Data`
- Integration tests
- `SearchingPostTest.java` fetching data using pure `EntityManager`
- `DogControllerTest.java` testing Bean Validation 2.0 


Application won't run unles you provide proper database details in `application.properties`. During tests application uses H2 database.

### Running tests
`mvn clean test`
