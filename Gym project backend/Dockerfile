FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/gym-project-backend.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=docker"]