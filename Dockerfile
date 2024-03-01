FROM maven:3.9.5-eclipse-temurin-17-alpine AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/bank-service.jar
ENTRYPOINT ["java", "-jar", "bank-service.jar"]
