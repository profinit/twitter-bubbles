#!/usr/bin/env groovy
def notifyStatusChangedViaEmail(buildStatus) {
    if (buildStatus != 'SUCCESS') {
        sendEmail(buildStatus)
    }
}

def sendEmail(buildStatus, deployment=false){
    def emailBody = "Email from Jenkins.<br>\nProject: ${env.JOB_NAME}<br>\nBuild Number: ${env.BUILD_NUMBER}<br>\nURL of build: ${env.BUILD_URL}"
    if (deployment) {
        def props = readProperties file: 'server/src/main/resources/application.properties'
        def port = props['server.port']
        def contextPath = props['server.servlet.context-path']
        def applicationUrl = "http://${env.DEPLOYMENT_HOSTNAME}:${port}${contextPath}"

        emailBody += "<br>\nApplication URL: ${applicationUrl}" +
                "<br>\nApplication info: ${applicationUrl}/actuator/info"
    }

    emailext (
            subject: "Jenkins ${env.JOB_NAME} is ${buildStatus}",
            body: "${emailBody}",
            recipientProviders: [
                    [$class: 'DevelopersRecipientProvider']
            ]
    )
}

pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'JDK'
    }
    environment {
        DEPLOYMENT_HOSTNAME = '52.16.190.152'
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
                            sh "mvn test -Dmaven.test.failure.ignore=true"
                        } finally {
                            junit '**/target/surefire-reports/*.xml'
                        }
                    }
                }
            }
        }
        stage('Deploy') {
            when {
                expression { currentBuild.currentResult == 'SUCCESS' }
            }
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
            notifyStatusChangedViaEmail("${currentBuild.result}")
        }
        success {
            sendEmail("${currentBuild.result}", true)
        }
    }
}
