#!/user/bin/env/ groovy
@Library('jenkins-shared-library') _
def gv

pipeline {
    agent any
    tools {
        nodejs "node"
    }

    stages {
        stage("INIT GROOVY SCRIPT") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
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
}
