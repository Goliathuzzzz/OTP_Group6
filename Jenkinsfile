pipeline {
    agent any
    tools {
        maven 'maven_3.9.9'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: "main", url:'https://github.com/Goliathuzzzz/OTP_Group6.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean install -Dtestfx.robot=glass -Dglass.platform=Monocle -Dmonocle.platform=Headless -Dprism.order=sw'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test -Dtestfx.robot=glass -Dglass.platform=Monocle -Dmonocle.platform=Headless -Dprism.order=sw'
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
