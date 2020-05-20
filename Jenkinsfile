#!/usr/bin/env groovy
def mvnHome
def JAR_PATH
def Build_Arg
def ServiceName
def DTRRepo

pipeline { 
  agent  any
 
  stages {
        stage ('Checkout code'){
		steps {
			 checkout scm
		    }
	 }
	 stage('Build') {
		steps {
		   script{
    		   mvnHome = tool 'M3'

	    	    sh "'${mvnHome}/bin/mvn' clean install"
		    		// Defining Properties for Sonar build & Dockerfile Args
			    	ServiceName='pandsclient-acc'
		    	    JAR_PATH=sh(returnStdout: true, script: "ls -1 target/*.jar").trim()
		    	    Build_Arg=" --build-arg  SERVICE_JAR_FILE=${JAR_PATH} ."
		   }
        }
     }

	 stage(' Build Image & Push to DTR'){
	  when { branch "master" }
		steps {
		  script{

			docker.withRegistry("${DTR_URL}",'Docker_Creds') {
		    def customImage = docker.build("${DTRRepo}/${ServiceName}:latest","${Build_Arg}")

		 /* Push the container to the custom Registry */
		    customImage.push()
		     }
		}
		}
	}
   }
	 post {
	    failure {
		mail to: 'OBEDPEGITUser@TechMahindra.com',
		     subject: "Failed Pipeline: ${ServiceName}",
		     body: "Something is wrong with ${env.BUILD_URL}"
	    }
	    success {
		mail to: 'OBEDPEGITUser@TechMahindra.com',
		     subject: "Success Pipeline: ${ServiceName}",
		     body: "Check the pipeline at ${env.BUILD_URL}"
	    }
	}
}