pipeline {
    agent any
    stages {
        stage('Check') {
            steps {
                git branch: 'develop',credentialsId:'0-shingo', url:'https://github.com/Spharos-final-project-WOOYANO/Reservation'
            }
        }
	stage('Secret-File Download'){
	    steps {
	        withCredentials([
		    file(credentialsId: 'Wooyano-Secret-File', variable: 'secret')
		    ])
		{
		    sh "cp \$secret ./src/main/resources/application-secret.yml"
		}
	    }
	}
        stage('Build'){
            steps{
                script {
                    sh '''
                        pwd
                        chmod +x ./gradlew
                        ./gradlew build
                    '''
                    
                }
                    
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
                sh 'docker run -d --network spharos-network --name reservation-service reservation-service-img'

            }
        }
    }
}

