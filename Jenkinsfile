#!/usr/bin/env groovy

podTemplate(label: 'twg-stock', containers: [
        containerTemplate(name: 'stock', image: 'ikhripunov/connect-maven:latest', ttyEnabled: true, command: 'cat')
]) {
    node('twg-stock') {

        container('stock'){
            stage ('Checkout code from Git')
            // Checkout code from repository
            checkout scm

            stage ("Build ${env.BRANCH_NAME}")
                sh "./mvn clean install${env.BRANCH_NAME}Release"
                sh "./docker build${env.BRANCH_NamE}Release"

            stage ('Upload to docker')
            sh "./docker push twgorg/stock-level-service:1.1-SNAPSHOT"
        }

    }
}
