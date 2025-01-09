@Library('jenkins-shared-library@shared-library') _

pipeline {
    agent any
    tools {
        nodejs "node"
    }

        stage('INCREMENT VERSION') {
            steps {
                script {
                    dir("app") {
                        gv.incrementVersion()
                    }
                }
            }
        }

        stage('RUN TEST') {
            steps {
                script {
                    dir("app") {
                        gv.testApp()
                    }
                }
            }
        }

        stage('BUILD AND PUSH DOCKER IMAGE') {
            steps {
                script {
                    gv.buildAndPushImage()
                }
            }
        }

        stage('COMMIT VERSION UPDATE') {
            steps {
                script {
                    gv.commitVersionUpdate()
                }
            }
        }
    }