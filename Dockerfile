FROM maven:latest

LABEL authors="ryhma6"

WORKDIR /app

COPY pom.xml /app/

COPY . /app/

RUN mvn clean package -DskipTests

CMD ["java", "-jar", "target/otp-group6.jar"]