pipeline {
    agent any
    tools {
        maven 'maven_3.9.9'
    }
    environment {
        MAVEN_OPTS = "-Dtestfx.headless=true -Dprism.order=sw -Dheadless=true"
        DOCKERHUB_CREDENTIALS_ID = 'Docker_login'
        DOCKERHUB_REPO = 'mikaklaa/otp_group6_test'
        DOCKER_IMAGE_TAG = 'latest'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: "mika", url:'https://github.com/Goliathuzzzz/OTP_Group6.git'
            }
        }
        stage('Build & Test') {
            steps {
                bat 'mvn clean install -Ptest'
            }
        }
        stage('Code Coverage') {
            steps {
                bat 'mvn jacoco:report'
            }
        }
        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Publish Coverage Report') {
            steps {
                jacoco()
            }
        }
        stage('Build Docker Image') {
            steps {
                // Build Docker image
                script {
                    bat "docker build -t ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG} ."
                }
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                // Push Docker image to Docker Hub
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                        docker.image("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}").push()
                    }
                }
            }
        }
        stage('Create and Push Docker Compose File') {
            steps {
                script {
                    // Create a production-ready docker-compose.yml file
                    writeFile file: 'docker-compose.prod.yml', text: """
version: '3.8'

services:
  mariadb:
    image: mariadb:latest
    container_name: mariadb
    command: --default-authentication-plugin=mysql_native_password
    restart: always
    environment:
      MARIADB_ROOT_PASSWORD: maailmanilmaa
      MARIADB_DATABASE: tatskatytot
      MARIADB_USER: appuser
      MARIADB_PASSWORD: maailmanilmaa
    ports:
      - "3306:3306"
    volumes:
      - mariadb_data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "appuser", "-pmaailmanilmaa"]
      interval: 5s
      timeout: 5s
      retries: 10
    networks:
      - app-network

  javafx-app:
    image: ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}
    container_name: javafx-app
    depends_on:
      mariadb:
        condition: service_healthy
    environment:
      - DISPLAY=host.docker.internal:0.0
      - DB_HOST=mariadb
      - DB_PORT=3306
      - DB_NAME=tatskatytot
      - DB_USER=appuser
      - DB_PASSWORD=maailmanilmaa
    networks:
      - app-network
    stdin_open: true
    tty: true

networks:
  app-network:
    driver: bridge

volumes:
  mariadb_data:
"""

                    // Archive the compose file as an artifact
                    archiveArtifacts artifacts: 'docker-compose.prod.yml', fingerprint: true

                    // Optional: Push the compose file to a repository or storage
                    // This could be GitHub, S3, or any other storage solution
                    // For example, pushing to the same Git repo:
                    // bat "git add docker-compose.prod.yml"
                    // bat "git commit -m 'Update production docker-compose file'"
                    // bat "git push origin main"
                }
            }
        }
    }
    post {
        always {
            // Clean up
            cleanWs()
        }
    }
}