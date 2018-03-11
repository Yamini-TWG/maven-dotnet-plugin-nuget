#!/usr/bin/env groovy

podTemplate(label: 'twg-ff-stock-level-service', containers: [
       containerTemplate(name: 'Enfinity_stock', image: 'ikhripunov/connect-maven', ttyEnabled: true, command: 'cat')
]) {
   node('twg-ff-stock-level-service') {

       container('Enfinity_stock'){
           stage ('Checkout code from Git')
           // Checkout code from repository
           checkout scm

           stage ("maven Build ${env.BRANCH_NAME}")
           echo "Helo"

		   stage ("DockerBuild ${env.BRANCH_NAME}")
            echo "Helo"
		   
		   stage ('Upload to Docker Hub')
            echo "Helo"
	
		   
           

       }

   }
}
