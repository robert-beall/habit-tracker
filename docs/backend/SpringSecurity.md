# Spring Security
This file details challenges encountered working with Spring Security. 
## Securing a Spring Application
### Add Spring Security to Gradle Project
Add the following to the `build.gradle` file [[1](https://docs.spring.io/spring-security/reference/getting-spring-security.html)]:
```
implementation 'org.springframework.boot:spring-boot-starter-security'
```

This adds spring security to the class path of the project and enforces basic authentication on all endpoints.

## Work Cited
1. [Getting Spring Security](https://docs.spring.io/spring-security/reference/getting-spring-security.html)
2. [Securing a Web Application](https://spring.io/guides/gs/securing-web)
3.  [Implement JWT authentication in a Spring Boot 3 application](https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac)
4.  [Spring Security: Authentication with a Database-backed UserDetailsService](https://www.baeldung.com/spring-security-authentication-with-a-database)
5.  *[](https://medium.com/@sehgal.mohit06/spring-security-basic-authentication-with-database-and-unit-testing-f40420d094f6)
6.  [BCrypt online](https://bcrypt-generator.com/)
7.  [](https://www.baeldung.com/role-and-privilege-for-spring-security-registration)