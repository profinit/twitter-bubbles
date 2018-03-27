#!/usr/bin/env groovy
pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'JDK'
    }
    options {
        gitLabConnection('Profinit Gitlab')
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '5'))
    }
    stages {
        stage('Build') {
            steps {
                dir('swprods/backend') {
                    sh "mvn clean install -DskipTests"
                }
            }
        }
        stage('Test') {
            steps {
                dir('swprods/backend') {
                    script {
                        try {
                            sh "mvn test -Dmaven.test.failure.ignore=true -Dbackend.testTruststoreFile=ObBpk9LjNqvy55L6v4jtxN-kvuCDmWAWSsQ8sGOoKU3xL3GZH6fPW7g-iqkOkxgq"
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

                dir('') {
                    sh 'mvn clean'
                }
            }
        }
        changed {
            emailext body: "Email from Jenkins. Project: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, URL of build: ${env.BUILD_URL}", recipientProviders: [[$class: 'DevelopersRecipientProvider']], subject: "Jenkins ${env.JOB_NAME} is ${currentBuild.result}"

        }
    }
}
