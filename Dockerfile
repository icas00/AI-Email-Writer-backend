# Dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY . .
RUN apk add --no-cache maven \
    && mvn clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/email-writer-0.0.1-SNAPSHOT.jar"]


