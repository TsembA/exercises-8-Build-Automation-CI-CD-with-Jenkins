pipeline
    agent any
    tools {
        nodejs "node"
    }

    stages {
        stage ('INCREMENT VERSION') {
            steps {
                script {
                    dir ("app") {
                        sh "npm version minor"
                        def packageJson=readJSON file:'package.json'
                        def version = readJson.version
                        env.IMAGE_NAME = "$version=$BUILD_NUMBER"
                    }
                }
            }
        }
        stage ('RUN TEST') {
            steps {
                script{
                    dir ("app") {
                        sh "npm install"
                        sh "npm run test"
                    }
                }
            }
        }
        stage ('BUILD AND PUSH DOCKER IMAGE') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'USER', passwordVarialble: 'PASS')]){
                    sh "docker build -t dancedevops/my-node-app:${IMAGE_NAME} ."
                    sh 'echo $PASS | docker login -u $USER --password-stdin'
                    sh "docker push dancedevops/my-node-app:${IMAGENAME}"
                }
            }
        }
    }