#!/usr/bin/env groovy

def testApp() {
    sh "npm install"
    sh "npm run test"
}