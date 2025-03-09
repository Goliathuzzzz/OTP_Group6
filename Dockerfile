FROM openjdk:17

# Install JavaFX and GUI dependencies
RUN apt-get update && apt-get install -y \
    libgl1-mesa-glx \
    libxext6 \
    libxrender1 \
    libxtst6 \
    && rm -rf /var/lib/apt/lists/* \

RUN mvn clean package -DskipTests

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

WORKDIR /app
COPY target/otp-group6.jar app.jar

CMD ["java", "-jar", "app.jar"]
