pipeline {
    agent any
    tools {
        maven 'maven_3.9.9'
    }
    environment {
        MAVEN_OPTS = "-Dtestfx.headless=true -Dprism.order=sw -Dheadless=true"
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
    }
}
