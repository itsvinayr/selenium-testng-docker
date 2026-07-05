pipeline {

    agent any

    parameters {
        choice(
            name: 'browser',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Select browser'
        )

        choice(
            name: 'execution',
            choices: ['grid', 'local'],
            description: 'Execution mode'
        )
    }

    stages {

        stage('Start Selenium Grid') {
            when {
                expression { params.execution == 'grid' }
            }
            steps {
                bat 'docker compose up -d'
            }
        }

        stage('Run Selenium Tests') {
            steps {
                bat "mvn clean test -Dbrowser=${params.browser} -Dexecution=${params.execution}"
            }
        }
    }

    post {
        always {
            script {
                if (params.execution == 'grid') {
                    bat 'docker compose down'
                }
            }
        }
    }
}