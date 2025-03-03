# Stage 1: Build the application with Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set the working directory
WORKDIR /app

# Copy the POM file first to leverage Docker caching
COPY pom.xml .

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src/ ./src/

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jdk

# Install required libraries for JavaFX
RUN apt-get update && apt-get install -y \
    libx11-6 \
    libgl1 \
    libglib2.0-0 \
    libgtk-3-0 \
    libxrandr2 \
    libxrender1 \
    libxext6 \
    libxi6 \
    && rm -rf /var/lib/apt/lists/*

RUN apt-get update && apt-get install -y xvfb

WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/otp-group6.jar /app/

CMD ["xvfb-run", "java", "-jar", "/app/otp-group6.jar"]
