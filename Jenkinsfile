#!/usr/bin/env groovy

@Library('jenkins-shared-library')
def gv

pipeline {

    agent any
    tools {
        nodejs "node"
    }
    stages {
        stage('INCREMENT VERSION') {
            steps {
                script {
                    incrementVersion()
                }
            }
        }

        stage('RUN TEST') {
            steps {
                script {
                    testApp()
                }
            }
        }

        stage('BUILD AND PUSH DOCKER IMAGE') {
            steps {
                script {
                    buildAndPushImage()
                }
            }
        }

        stage('COMMIT VERSION UPDATE') {
            steps {
                script {
                    commitVersionUpdate()
                }
            }
        }
    }
}
