def incrementVersion() {
    sh "npm version major"
    def packageJson = readJSON file: 'package.json'
    def version = packageJson.version
    env.IMAGE_NAME = "${version}-${BUILD_NUMBER}"
}

def testApp() {
    sh "npm install"
    sh "npm run test"
}
def buildAndPushImage() {
    withCredentials([usernamePassword(credentialsId: 'docker-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
        script {
            sh "docker build -t dancedevops/my-node-app:${IMAGE_NAME} -f Dockerfile ."
            sh 'echo $PASS | docker login -u $USER --password-stdin'
            sh "docker push dancedevops/my-node-app:${IMAGE_NAME}"
        }
    }
}

def commitVersionUpdate() {
    withCredentials([usernamePassword(credentialsId: 'github-credentials', usernameVariable: 'USER', passwordVariable: 'TOKEN')]) {
        script {
            sh 'git config --global user.email "tsembenhoi@gmail.com"'
            sh 'git config --global user.name "TsembA"'
            sh 'git remote set-url origin https://${USER}:${TOKEN}@github.com/TsembA/exercises-8-Build-Automation-CI-CD-with-Jenkins.git'
            sh '''
            if ! git diff --cached --exit-code > /dev/null; then
                git commit -m "ci: version bump"
                git push origin HEAD:jenkins-jobs
            else
                echo "No changes to commit."
            fi
            '''
        }
    }
}
return this