#!/usr/bin/env groovy

podTemplate(label: 'twg-ff-stock-level-service', containers: [
       containerTemplate(name: 'Enfinity_stock', image: 'ikhripunov/connect-maven', ttyEnabled: true, command: 'cat')
]) {
   node('twg-ff-stock-level-service') {

       container('Stock-level-servvice'){
           stage ('Checkout code from Git')
           // Checkout code from repository
           checkout scm

           stage ("maven Build ${env.BRANCH_NAME}")
           sh "./mvn clean install ${env.BRANCH_NAME}Release"

		   stage ("DcokerBuild ${env.BRANCH_NAME}")
           sh "./dokcer build ${env.BRANCH_NAME}Release"
		   
           

       }

   }
}
