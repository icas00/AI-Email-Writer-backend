
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app


COPY pom.xml .

RUN mvn -B -f pom.xml dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests


FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app


COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "/app/app.jar"]
