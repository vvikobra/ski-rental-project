pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Получение кода из репозитория...'
                checkout scm
            }
        }
        
        stage('Build Dependencies') {
            steps {
                echo 'Сборка зависимостей (ski-rental-events-contract, ski-rental-contracts)...'
                dir('ski-rental-events-contract') {
                    sh './mvnw clean package -DskipTests'
                }
                dir('ski-rental-contracts') {
                    sh './mvnw clean package -DskipTests'
                }
            }
        }
        
        stage('Build Services') {
            parallel {
                stage('Build Ski Rental') {
                    steps {
                        echo 'Сборка ski-rental...'
                        dir('ski-rental') {
                            sh './mvnw clean package -DskipTests'
                        }
                    }
                }
                stage('Build Analytics Service') {
                    steps {
                        echo 'Сборка analytics-service...'
                        dir('analytics-service') {
                            sh './mvnw clean package -DskipTests'
                        }
                    }
                }
                stage('Build Notification Service') {
                    steps {
                        echo 'Сборка notification-service...'
                        dir('notification-service') {
                            sh './mvnw clean package -DskipTests'
                        }
                    }
                }
                stage('Build Payment Service') {
                    steps {
                        echo 'Сборка payment-service...'
                        dir('payment-service') {
                            sh './mvnw clean package -DskipTests'
                        }
                    }
                }
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline выполнен успешно!'
        }
        failure {
            echo 'Pipeline завершился с ошибкой.'
        }
        always {
            echo 'Архивация артефактов...'
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
        }
    }
}
