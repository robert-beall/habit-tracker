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

# Work Cited
1. [How to use ModelMapper in Spring Boot](https://zubairehman.medium.com/how-to-use-modelmapper-in-spring-boot-8a494e316840)