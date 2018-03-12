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

            stage ('Distribute apk to Crashlytics')
            echo "Helo"
            
            stage ('Upload to Nexus')
            echo "Helo"
        }

    }
}
