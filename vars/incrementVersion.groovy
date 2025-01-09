def incrementVersion() {
    sh "npm version major"
    def packageJson = readJSON file: 'package.json'
    def version = packageJson.version
    env.IMAGE_NAME = "${version}-${BUILD_NUMBER}"
}