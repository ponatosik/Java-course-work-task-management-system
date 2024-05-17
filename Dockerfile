# Build stage
FROM openjdk:20-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package

# Run stage
FROM openjdk:20
WORKDIR /app
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_SHOW_SQL=true

ENV SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_SCOPE=openid,profile,email
ENV SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER-URI=https://accounts.google.com
ENV SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK-SET-URI=https://www.googleapis.com/oauth2/v2/certs

COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]