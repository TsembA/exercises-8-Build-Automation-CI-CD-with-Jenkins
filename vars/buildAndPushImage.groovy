#!/usr/bin/env groovy

def buildAndPushImage() {
    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        script {
            sh "docker build -t dancedevops/my-node-app:${IMAGE_NAME} -f Dockerfile ."
            sh 'echo $PASS | docker login -u $USER --password-stdin'
            sh "docker push dancedevops/my-node-app:${IMAGE_NAME}"
        }
    }
}