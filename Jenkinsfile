pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t spring-boot-app:1.0 .'
            }
        }

        stage('Push to Docker Registry') {
            steps {
                sh 'docker tag spring-boot-app:1.0 your-dockerhub-username/spring-boot-app:1.0'
                sh 'docker login -u your-dockerhub-username -p your-dockerhub-password'
                sh 'docker push your-dockerhub-username/spring-boot-app:1.0'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s-deployment.yaml'
            }
        }
    }
}
