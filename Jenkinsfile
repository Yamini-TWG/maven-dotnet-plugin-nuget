#!/usr/bin/env groovy

podTemplate(label: 'twg-ff-stock-level-service', containers: [
        containerTemplate(name: 'Enfinity_stock', image: 'runmymind/docker-android-sdk:alpine-standalone', ttyEnabled: true, command: 'cat')
]) {
    node('twg-ff-stock-level-service') {

        container('Enfinity_stock'){
            stage ('Checkout code from Git')
            // Checkout code from repository
            checkout scm

            stage ("maven Build ${env.BRANCH_NAME}")
            echo "Hello"
                
            stage ('Docker build to docker image')
            echo "Hello"
            
            stage ('Upload to docker hub')
            echo "Hello"
        }

    }
}
