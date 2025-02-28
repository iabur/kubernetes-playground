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
                sh 'docker build -t jenkins-spring-boot-1.0 .'
            }
        }

        stage('Push to Docker Registry') {
            steps {
                sh 'docker tag jenkins-spring-boot-1.0:latest iabur/jenkins-spring-boot-1.0:latest'
                sh 'docker login -u iabur -p Whatislove'
                sh 'docker push iabur/jenkins-spring-boot-1.0:latest'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s-deployment.yaml'
            }
        }
    }
}
