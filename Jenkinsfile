pipeline {
    agent any

    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'0-shingo', url:'https://github.com/Spharos-final-project-WOOYANO/Reservation'
            }
        }
        stage('Build'){
            steps{
                sh '''
                    cd Reservation
                    chmod +x ./gradlew
                    ./gradlew build -x test
                '''
            }
        }
        stage('DockerSize'){
            steps {
                sh '''
                    cd server
                    docker stop Reservation-Service || true
                    docker rm Reservation-Service || true
                    docker rmi Reservation-Service-Img || true
                    docker build -t Reservation-Service-Img:latest .
                '''
            }
        }
        stage('Deploy'){
            steps{
                sh 'docker run -d --name Reservation-Service -p 8080:8000 Reservation-Service-Img'
            }
        }
    }
}
