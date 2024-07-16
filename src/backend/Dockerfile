# Stage 1: Build the application
FROM gradle:jdk17 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build --info

# Stage 2: Run the application
FROM eclipse-temurin:17.0.11_9-jre
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]