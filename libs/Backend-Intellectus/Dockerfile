# Etapa de construcción
FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder

WORKDIR /app

# Copiar solo los archivos necesarios para optimizar la caché de Docker
COPY pom.xml ./
COPY src ./src/

# Instalar dependencias y construir el proyecto
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:24-jre-alpine

WORKDIR /app

# Copiar el archivo JAR desde la etapa de construcción
COPY --from=builder /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Copiar el script SQL para la base de datos
COPY BDScript.sql /docker-entrypoint-initdb.d/

# Exponer el puerto
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
