#!/usr/bin/env groovy

podTemplate(label: 'twg-ff-stock-level-service', containers: [
        containerTemplate(name: 'Enfinity_stock', image: 'ikhripunov/connect-maven:latest', ttyEnabled: true, command: 'cat')
]) {
    node('twg-ff-stock-level-service') {

        container('Enfinity_stock'){
            stage ('Checkout code from Git')
            // Checkout code from repository
            checkout scm

            stage ("maven Build ${env.BRANCH_NAME}")
            sh "./mvn clean install${env.BRANCH_NAME}Release"
                
            stage ('Docker build to docker image')
            sh "./docker build${env.BRANCH_NAME}Release"
            
            stage ('Upload to docker hub')
            sh './docker push twgorg/stock-level-service:1.1-SNAPSHOT'
        }

    }
}
