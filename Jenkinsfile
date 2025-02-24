pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: "main", url:'https://github.com/Goliathuzzzz/OTP_Group6.git'
            }
        }
        stage('Build') {
            steps {
                bat 'maven clean install'
            }
        }
        stage('Test') {
            steps {
                bat 'maven test'
            }
        }
        stage('Code Coverage') {
            steps {
                bat 'maven jacoco:report'
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
    }
}
