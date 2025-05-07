pipeline {
    agent any
    tools {
        maven 'maven_3.9.9'
        jdk 'jdk-21'
    }
    environment {
        MAVEN_OPTS = "-Dtestfx.headless=true -Dprism.order=sw -Dheadless=true"
        DOCKERHUB_CREDENTIALS_ID = 'docker-login'
        DOCKERHUB_REPO = 'hetahar/otp2'
        DOCKER_IMAGE_TAG = 'latest'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: "hetatest", url:'https://github.com/Goliathuzzzz/OTP_Group6.git'
            }
        }
        stage('Build & Test') {
            steps {
                bat 'mvn clean install -DskipTests=true -Ptest'
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
    }
}
