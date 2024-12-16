pipeline {
    agent any
    tools {
        nodejs "node"
    }

    stages {
        stage('INCREMENT VERSION') {
            steps {
                script {
                    dir("app") {
                        sh "npm version major"
                        def packageJson = readJSON file: 'package.json'
                        def version = packageJson.version
                        env.IMAGE_NAME = "${version}-${BUILD_NUMBER}"
                    }
                }
            }
        }
        stage('RUN TEST') {
            steps {
                script {
                    dir("app") {
                        sh "npm install"
                        sh "npm run test"
                    }
                }
            }
        }
        stage('BUILD AND PUSH DOCKER IMAGE') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    script {
                        sh "docker build -t dancedevops/my-node-app:${IMAGE_NAME} ."
                        sh 'echo $PASS | docker login -u $USER --password-stdin'
                        sh "docker push dancedevops/my-node-app:${IMAGE_NAME}"
                    }
                }
            }
        }
        stage('COMMIT VERSION UPDATE') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'USER', passwordVariable: 'TOKEN')]) {
                        sh 'git config --global user.email "tsembenhoi@gmail.com"'
                        sh 'git config --global user.name "TsembA"'
                        sh 'git remote set-url origin https://${USER}:${TOKEN}@github.com/TsembA/exercises-8-Build-Automation-CI-CD-with-Jenkins.git'
                        sh '''
                        git checkout jenkins-jobs || git checkout -b jenkins-jobs
                        '''
                        sh 'git add .'
                        sh 'git commit -m "ci: version bump" || echo "No changes to commit."'
                        sh 'git push origin HEAD:jenkins-jobs'
                    }
                }
            }
        }
    }
}
