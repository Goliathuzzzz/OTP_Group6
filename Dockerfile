# Use a base image with Maven and Java
FROM maven:latest

# Set metadata
LABEL authors="hetahar"

# Set working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml /app/
COPY src /app/src/

# Install dependencies for JavaFX and GUI
RUN apt-get update && apt-get install -y \
    openjfx \
    mesa-utils \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libgtk-3-0 \
    libcanberra-gtk-module \
    libasound2t64 \
    wget \
    unzip \
    && rm -rf /var/lib/apt/lists/*


# Download and extract JavaFX SDK
RUN wget https://download2.gluonhq.com/openjfx/20.0.1/openjfx-20.0.1_linux-x64_bin-sdk.zip && \
    unzip openjfx-20.0.1_linux-x64_bin-sdk.zip && \
    rm openjfx-20.0.1_linux-x64_bin-sdk.zip

# Set the path to the JavaFX libraries
ENV PATH_TO_FX=/app/javafx-sdk-20.0.1/lib

# Build the application
RUN mvn clean package -DskipTests

# Copy the built JAR file
COPY target/otp-group6.jar /app/otp-group6.jar

# Set the command to run the application with JavaFX
CMD ["java", "--module-path", "/app/javafx-sdk-20.0.1/lib", "--add-modules", "javafx.controls,javafx.fxml", "-Dprism.order=sw", "-Djavafx.platform=x11", "-jar", "/app/otp-group6.jar"]