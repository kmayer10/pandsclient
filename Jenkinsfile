#!/usr/bin/env groovy
def mvnHome
def JAR_PATH
def Build_Arg
def ServiceName
def DTRRepo
def DTR_URL = "https://opnind03.host.mobistar.be:32006"

pipeline { 
  agent  {
  	label 'docker'
  }
 
  stages {
        stage ('Checkout code'){
		steps {
			 checkout scm
		    }
	 }
	 stage('Scan Base Image'){
	 	steps {
			sh label: '', script: 'trivy image --exit-code 1 --severity CRITICAL,HIGH registry.redhat.io/redhat-openjdk-18/openjdk18-openshift'
		}
	}
	 stage('Build') {
		steps {
		   script{
    		   mvnHome = tool 'M3'

	    	    sh "'${mvnHome}/bin/mvn' clean install"
				// Defining Properties for Sonar build & Dockerfile Args
				ServiceName='pandsclient-acc'
				DTRRepo="de"
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
}