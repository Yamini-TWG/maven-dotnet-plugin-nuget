#!/usr/bin/env groovy

podTemplate(label: 'twg-stock', containers: [
        containerTemplate(name: 'stock', image: 'runmymind/docker-android-sdk:alpine-standalone', ttyEnabled: true, command: 'cat')
]) {
    node('twg-stock') {

        container('stock'){
            stage ('Checkout code from Git')
            // Checkout code from repository
            checkout scm

            stage ("Build ${env.BRANCH_NAME}")
            echo "Helo"

            stage ('Distribute apk to Crashlytics')
            echo "Helo"
            
            stage ('Upload to Nexus')
            echo "Helo"
        }

    }
}
