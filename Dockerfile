# Start with maven:3.8.7-eclipse-temurin-19-alpine base image
FROM maven:3.8.7-eclipse-temurin-19-alpine

# LABEL maintainer="Mateus Vinícius <mateus.limavn@gmail.com>"
# Set the working directory to /app
WORKDIR /app

# Copy the source code to the container
COPY . .

# Build the application with Maven
RUN mvn clean package -X

# Set environment variables if needed
ENV DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres
ENV DATASOURCE_USERNAME=postgres
ENV DATASOURCE_PASSWORD=zsQ2VTy*/!386Mh]


# Expose default Spring Boot port
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "target/CCB-gestao-custos-API-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]

#End