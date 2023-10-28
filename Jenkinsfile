pipeline {
    agent any

    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'jenkins-github-access-token', url:'https://github.com/Spharos-final-project-WOOYANO/Reservation'
            }
        }
        stage('Build'){
            steps{
                sh '''
                    chmod +x ./gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('DockerSize'){
            steps {
                sh '''
                    docker stop reservation-service || true
                    docker rm reservation-service || true
                    docker rmi reservation-service-img || true
                    docker build -t reservation-service-img:latest .
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh 'docker run -d --name reservation-service -p 8002:8000 reservation-service-img'
            }
        }
    }
}
