pipeline {
    agent any

//     tools {
//         maven 'Maven 3.8.6'
//     }

    environment {
        DOCKER_IMAGE = 'iabur/jenkins-spring-boot-1.0:latest'
        SERVICE_ACCOUNT = 'jenkins-sa' // Replace with your existing service account name
        NAMESPACE = 'default' // Change if needed
    }

    stages {
        stage('Compile and Package Application') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Create Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }

        stage('Upload Docker Image to Registry') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerCredential', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'docker login -u $DOCKER_USER -p $DOCKER_PASSWORD'
                    sh 'docker tag $DOCKER_IMAGE $DOCKER_IMAGE'
                    sh 'docker push $DOCKER_IMAGE'
                }
            }
        }

        stage('Update Kubernetes Deployment') {
            steps {
                echo 'Deploying to Kubernetes cluster'
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
                    sh 'kubectl rollout restart deployment spring-boot-app-deployment'
                }
            }
        }
    }

    post {
        failure {
            echo 'Pipeline failed! Check the logs for details.'
        }
        success {
            echo 'Pipeline succeeded!'
        }
    }
}