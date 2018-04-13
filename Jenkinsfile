pipeline {
    agent {
            AMAZON
    stages {
        stage('Example') {
            steps { 
                echo 'Hello World'
                 sh "mvn -version"
                 sh "java -version"
                 sh "docker version"
                
            }
        }
    }
}
