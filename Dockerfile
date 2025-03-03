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
COPY src/main/resources/ ./src/main/resources/

# Build the application
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

# Install necessary dependencies
RUN apt-get update && apt-get install -y \
    openjfx \
    libgl1 \
    libgtk-3-0 \
    libcanberra-gtk-module \
    && rm -rf /var/lib/apt/lists/*

# Copy the application JAR
COPY target/otp-group6.jar /app/otp-group6.jar

# Set working directory
WORKDIR /app

RUN apt-get update && apt-get install -y xvfb
CMD ["xvfb-run", "java", "--module-path", "/usr/javafx/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "otp-group6.jar"]

