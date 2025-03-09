FROM openjdk:17

# Install JavaFX and GUI dependencies
RUN apt-get update && apt-get install -y \
    libgl1-mesa-glx \
    libxext6 \
    libxrender1 \
    libxtst6 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY target/otp-group6.jar app.jar

CMD ["java", "-jar", "app.jar"]
