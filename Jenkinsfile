pipeline {

    agent any

    parameters {

        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Select Browser'
        )

        choice(
            name: 'EXECUTION',
            choices: ['grid', 'local'],
            description: 'Execution Mode'
        )

    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Start Selenium Grid') {
            when {
                expression { params.EXECUTION == 'grid' }
            }
            steps {
                bat 'docker compose up -d'
            }
        }

        stage('Wait for Grid') {
            when {
                expression { params.EXECUTION == 'grid' }
            }
            steps {
                bat '''
                :wait
                curl http://localhost:4444/status >nul 2>&1

                if errorlevel 1 (
                    timeout /t 2 >nul
                    goto wait
                )
                '''
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn clean test -Dbrowser=${params.BROWSER} -Dexecution=${params.EXECUTION}"
            }
        }
    }

    post {

        always {

            junit '**/target/surefire-reports/*.xml'

            archiveArtifacts artifacts: 'target/surefire-reports/**', fingerprint: true

            script {

                if (params.EXECUTION == 'grid') {

                    bat 'docker compose down'

                }

            }

        }

    }

}