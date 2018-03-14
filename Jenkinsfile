pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        echo 'hello , I am doing build'
      }
    }
    stage('test ') {
      steps {
        echo 'I am doing test'
      }
    }
    stage('triggering a new job') {
      steps {
        build(job: 'po', propagate: true)
      }
    }
  }
}