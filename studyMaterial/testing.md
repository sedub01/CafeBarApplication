# Testing

Note: Most of these mocks in Test Controllers are to avoid autowiring issues. They aren't actually used in the course of the test, so their behavior isn't important. They just need to exist so autowiring can take place.

Recently I've got unknown error in HomeControllerTest, where compilation ruins, because there were unexpected output values, like code 301, nullable model and empty content. For resolving this I've changed @WebMvcTest to @SpringBootTest and created TestConfig.java. Look at [this solution](https://stackoverflow.com/questions/64012878/getting-error-in-unit-tests-after-implement-spring-security-to-project).

`when().thenReturn()` construction is used to create mocks for imitating dependency injections in unit tests. It helps to test something's behavior without interaction with outer systems. <br>
With the `when()` method, you specify which method you want to intercept from the mock and what to return when it is called. <br>
The `thenReturn()` method tells mock, what to return, when method in `when()` invokes.

MockMVC provides various useful methods for testing MVC applications:
- `andExpect(status().isOk())`: checks that HTTP response has a 200 (OK) status code;
- `andExpect(view().viewName("viewName"))`: checks that response was directed to concrete view with "viewName" name;
- `andExpect(model().attribute("attributeName", "attributeValue"))`: checks for the presence of model's attribite with specific name and value;
- `andExpect(model().attributeExists("attributeName"))`: checks that attribute with specified name exists in model;
- `andReturn()`: returns the result of the request as an MvcResult object that can be used to get additional information about the response, such as headers, content, and so on.

## Useful annotations
- @SpringBootTest allows to create a complete Spring application context for executing integration tests. It initializes the entire application context and can be used to test different layers, components and the interaction between them.
- @WebMvcTest is used to create a context specialized for testing a layer of controllers and MVC components of a web application. It allows to test controllers, request handlers and interaction with the web layer without bringing up the full application context.
- @MockBean used to create and inject mock objects into the application context to replace real dependencies.
- @Test it is a JUnit annotation that can be applied to individual test methods.
- @BeforeEach: in JUnit5 is annotation that marks the methods to be executed before each test method
- @BeforeAll: in JUnit5 is annotation that marks methods that must be executed before all tests in the class
- @Before: deprecated version of @BeforeEach in JUnit 4
- @BeforeClass: deprecated version of @BeforeAll in JUnit 4
- @WithMockUser is used to indicate that a lock user should be created in the test to emulate authorization or authentication on the system.
