#!/usr/bin/env groovy
pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'JDK'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '5'))
    }
    stages {
        stage('Build') {
            steps {
                dir('.') {
                    sh "mvn clean install -DskipTests"
                }
            }
        }
        stage('Test') {
            steps {
                dir('.') {
                    script {
                        try {
                            sh "mvn test"
                        } finally {
                            junit '**/target/surefire-reports/*.xml'
                        }
                    }
                }
            }
        }
    }
    post {
        always{
            script {
                // workaround for null currentBuild.result when the result is SUCCESS
                currentBuild.result = currentBuild.currentResult

                dir('.') {
                    sh 'mvn clean'
                }
            }
        }
        changed {
            emailext body: "Email from Jenkins. Project: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, URL of build: ${env.BUILD_URL}", recipientProviders: [[$class: 'DevelopersRecipientProvider']], subject: "Jenkins ${env.JOB_NAME} is ${currentBuild.result}"

        }
    }
}
