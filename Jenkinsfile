pipeline {
    agent any

    tools {
        jdk 'Java 21'
        maven 'Maven 3.9.9'  // Ensure this is correctly configured in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Goliathuzzzz/OTP_Group6.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Code Coverage') {
            steps {
                bat 'mvn jacoco:report'
            }
        }

        stage('Publish Test Results') {  // Fixed spelling
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Publish Coverage Report') {  // Fixed spelling
            steps {
                jacoco()
            }
        }
    }
}
