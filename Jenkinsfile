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
                            // make pipeline fail if tests failed, because we do not want to deploy in that case
                            if (currentBuild.result == 'UNSTABLE'){
                                currentBuild.result = 'FAILURE'
                            }
                        }
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'AWS NoC',
                            transfers:[
                                sshTransfer(
                                    excludes: '',
                                    execCommand: "systemctl restart twitter-bubbles@${env.BRANCH_NAME}",
                                    execTimeout: 120000,
                                    flatten: false,
                                        makeEmptyDirs: false,
                                        noDefaultExcludes: false,
                                        patternSeparator: '[, ]+',
                                        remoteDirectory: "${env.BRANCH_NAME}",
                                        remoteDirectorySDF: false,
                                        removePrefix: 'server/target',
                                        sourceFiles: 'server/target/*.jar'
                                )
                            ],
                            usePromotionTimestamp: false,
                            useWorkspaceInPromotion: false,
                            verbose: true
                        )
                    ]
                )
            }
        }
    }
    post {
        always{
            script {
                // workaround for null currentBuild.result when the result is SUCCESS
                currentBuild.result = currentBuild.currentResult
            }
        }
        changed {
            emailext body: "Email from Jenkins. Project: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, URL of build: ${env.BUILD_URL}", recipientProviders: [[$class: 'DevelopersRecipientProvider']], subject: "Jenkins ${env.JOB_NAME} is ${currentBuild.result}"

        }
    }
}
