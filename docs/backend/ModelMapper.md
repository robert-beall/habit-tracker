# Model Mapper Issues and Fixes
This file will document challenges I encounter with the ModelMapper library and how I resolved them.

## Configuration
Adding and using model mapper in Spring Boot without a configuration throws an error on build. In order to resolve this issue, you must add a configuration class to spring. The basic configuration class will look like this [[1](https://zubairehman.medium.com/how-to-use-modelmapper-in-spring-boot-8a494e316840)]: 

```
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
```
*View the full class at <mark>../src/backend/src/main/java/com/rjb/hobby_tracker/ModelMapperConfig.java</mark>*

### Configuration Class Breakdown
#### @Configuration
This annotation tells spring that the class contains one or more beans that need to be handled at runtime [[2](https://docs.spring.io/spring-framework/docs/4.0.4.RELEASE/javadoc-api/org/springframework/context/annotation/Configuration.html)].
#### @Bean
This annotation specifies a bean, or an object handled by Spring at runtime [[3](https://www.baeldung.com/spring-bean)]

# Work Cited
1. [How to use ModelMapper in Spring Boot](https://zubairehman.medium.com/how-to-use-modelmapper-in-spring-boot-8a494e316840)
2. [Annotation Type Configuration](https://docs.spring.io/spring-framework/docs/4.0.4.RELEASE/javadoc-api/org/springframework/context/annotation/Configuration.html)
3. [What Is a Spring Bean?](https://www.baeldung.com/spring-bean)