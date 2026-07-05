pipeline {

    agent any

    stages {

        stage('Start Selenium Grid') {
            steps {
                bat 'docker compose up -d'
            }
        }

        stage('Wait for Grid') {
            steps {
                bat '''
                :wait
                curl http://localhost:4444/status > nul 2>&1
                if errorlevel 1 (
                    ping 127.0.0.1 -n 2 > nul
                    goto wait
                )
                '''
            }
        }

        stage('Run Selenium Tests') {
            steps {
                bat "mvn clean test -Dbrowser=${params.browser}"
            }
        }
    }

    post {

        always {

            bat 'docker compose down'

        }

    }
}