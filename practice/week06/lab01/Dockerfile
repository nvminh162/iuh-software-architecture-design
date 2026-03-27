# ==========================================
# Stage 1: Build the application (Builder)
# ==========================================
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml first to cache dependencies
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (this layer will be cached unless pom.xml changes)
# Giving execute permission to maven wrapper
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the source code
COPY src src

# Build the application, skipping tests for faster build
RUN ./mvnw clean package -DskipTests

# ==========================================
# Stage 2: Minimal Runtime Stage (Production)
# ==========================================
# Use a minimal JRE image instead of a full JDK
FROM eclipse-temurin:17-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Create a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy only the compiled JAR file from the builder stage
COPY --from=builder /app/target/lab01-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
