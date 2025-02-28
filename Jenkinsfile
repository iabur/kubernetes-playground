pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6' // Replace with the name you configured in Jenkins
    }

    environment {
        DOCKER_IMAGE = 'iabur/jenkins-spring-boot-1.0:latest'
        SERVICE_ACCOUNT = 'jenkins-sa' // Replace with your existing service account name
        NAMESPACE = 'default' // Change if needed
    }

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
                withCredentials([usernamePassword(credentialsId: '77ec4acc-1746-4d5e-bcfb-4ad16476effd', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh 'docker login -u $DOCKER_USER -p $DOCKER_PASSWORD'
                    sh 'docker tag jenkins-spring-boot-1.0:latest $DOCKER_IMAGE'
                    sh 'docker push $DOCKER_IMAGE'
                }
            }
        }

        stage('Verify Files') {
            steps {
                echo 'Checking workspace directory'
                sh 'pwd'  // Shows the current directory
                sh 'ls -alh'  // Lists all files to ensure k8s-deployment.yaml exists
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo 'Deploying to Kubernetes using Service Account'
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
                    sh 'kubectl apply -f role.yaml'
                    sh 'kubectl apply -f rolebinding.yaml'
                    sh '''
                        kubectl apply -f k8s-deployment.yaml --validate=false \
                        --as=system:serviceaccount:$NAMESPACE:$SERVICE_ACCOUNT
                    '''
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