FROM maven:3.9.7-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml ./
COPY src ./src

COPY .env ./

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

ARG JAR_FILE=target/tienda-don-doug-dimmadome-0.0.1-SNAPSHOT.jar

COPY --from=build /app/${JAR_FILE} app.jar

# Copia el archivo .env también en esta etapa (opcional si ya se copió antes)
COPY --from=build /app/.env ./

# Expone el puerto de la aplicación
EXPOSE 8089

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
