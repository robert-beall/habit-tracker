# Spring Security
This file outlines the steps I took to implement basic security backed by database users. These are the key, broadstrokes points.

## Step 1: Add Spring Security to Gradle Project
Add the following to the `build.gradle` file [[1](https://docs.spring.io/spring-security/reference/getting-spring-security.html)]:
```
implementation 'org.springframework.boot:spring-boot-starter-security'
```

This adds spring security to the class path of the project and enforces basic authentication on all endpoints.

## Step 2: Add a Web Security Configuration

This configuration class specifies beans related to spring security. It bears the annotation `@EnableWebSecurity` to enable Spring Security’s web security support.

### Security Filter Chain
Establishes a security filter chain bean handled by Spring. This bean specifies which endpoints are and are not authenticated along with login and logout behavior.
#### example [[2](https://spring.io/guides/gs/securing-web)]: 
```
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/", "/home").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin((form) -> form
            .loginPage("/login")
            .permitAll()
        )
        .logout((logout) -> logout.permitAll());

    return http.build();
}
```

Additional beans such as a password encoder or user details service can be added to this configuration as well.

## Step 3: Add User Handling to the Application
### 3A: Add a user entity
A user entity representing user data in db should be created with fields necessary for interfacing with Spring security [[3](https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac)]: 

```
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    /* Optional: Field representing user role */
}
```
### 3B: Add an implementation for UserDetails
The UserDetails interface requires three methods [[4](https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac)]: 

1. Getter for username: `public String getUsername()`
2. Getter for password: `public String getPassword()`
3. Getter for authorities: 
```
public Collection<GrantedAuthority> getAuthorities()
```
I personally found it most useful to have the UserEntity class extend UserDetails as this automatically covers the user and password getters. This leaves implementing `getAuthorities`, which is dependent on the implementation of roles and privileges in the application.

### 3C: Add a UserDetailsService implementation
The UserDetailsService is an interface that defines how Spring Security loads a user from the database. 

The service implements a single method: 

```
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Logic for retrieving user details
    }
}
```

Unlike with **UserDetails**, I found it easiest to decouple this implementation from the main user service. The **UserService** class in this application handles CRUD and related operations on a user entity, focusing primarily on the data itself. Meanwhile, **UserDetailsService** is a bean for use by Spring Security. 

## Enabling Authentication
### Adding and Authentication Entrypoint
In addition to configuring the security filter chain (see [WebSecurityConfig.java](../../src/backend/src/main/java/com/rjb/hobby_tracker/configurations/WebSecurityConfig.java) for details on implementation), an Authentication Entrypoint provides handling for unauthorized access[[8](https://www.javaguides.net/2024/04/authenticationentrypoint-in-spring-security.html)]. 

## Step 4: Adding Roles and Privileges
**Roles** and **Privileges** are both considered *authorities* in Spring Security. 

Roles are broader collections of permissions that define how a user may behave in the application. For example, you may have roles: *ADMIN*, *STAFF*, and *USER*, which each map to a broad set of allowable operations. 

Privileges are more granular permissions such as *READ*, *WRITE*, *DELETE*, etc. 

Spring prefixes all roles with "ROLE_" (e.g. ROLE_ADMIN). 

There are many ways to implement roles and privileges from a data perspective. In this application, there are role and privilege tables along with a mapping table of role-to-privilege, establishing a many-to-many relationship. Additionally, each User can have multiple roles mapped via a user-to-role table. 

From Spring's perspective, it considers all *Granted Authorities* derived from roles and privileges in the database when evaluating permissions.

### Securing Individual Endpoints[[9](https://www.javaguides.net/2024/04/authenticationentrypoint-in-spring-security.html)]
Individual endpoints can be secured by enabling method level security in the web security configuration: 

```
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    ...
}
```

From there, you can use @PreAuthorize to determine whether an endpoint method invocation is permissable based on passed conditions. For example: 

```
@GetMapping()
@PreAuthorize("hasRole('ROLE_ADMIN')")
public List<UserDTO> listUsers() {
    ...
}
```
In this example, the endpoint is only accessible by users with the admin role.

## Testing and Setup
For this implementation, I am using the BCrypt password encoder. Out of convenience, the following website can be used to encode strings in the same manner as the java BCrypt library, enabling testing: 

[BCrypt Online](https://bcrypt-generator.com/)
 
## Work Cited
1. [Getting Spring Security](https://docs.spring.io/spring-security/reference/getting-spring-security.html)
2. [Securing a Web Application](https://spring.io/guides/gs/securing-web)
3.  [Implement JWT authentication in a Spring Boot 3 application](https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac)
4.  [Spring Security: Authentication with a Database-backed UserDetailsService](https://www.baeldung.com/spring-security-authentication-with-a-database)
5.  [Spring Security Basic Authentication with Database and Unit Testing](https://medium.com/@sehgal.mohit06/spring-security-basic-authentication-with-database-and-unit-testing-f40420d094f6)
6.  [BCrypt online](https://bcrypt-generator.com/)
7.  [Spring Security – Roles and Privileges](https://www.baeldung.com/role-and-privilege-for-spring-security-registration)
8.  [AuthenticationEntryPoint in Spring Security](https://www.javaguides.net/2024/04/authenticationentrypoint-in-spring-security.html)
9.  [Implementing Column-Level Security with Spring Boot and Spring Security](https://www.javaguides.net/2024/04/authenticationentrypoint-in-spring-security.html)