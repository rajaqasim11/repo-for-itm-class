pipeline {
    agent any

    environment {
        S3_BUCKET_NAME = 'qasim-resume-website'
        FILE_TO_UPLOAD = 'https://github.com/rajaqasim11/repo-for-itm-class/blob/main/index.html'
        AWS_ACCESS_KEY_ID = 'AKIA6LGJSU6GMNQT4RPB'
        AWS_SECRET_ACCESS_KEY = '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'
        AWS_DEFAULT_REGION = 'us-east-1'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Checkout the code from the GitHub repository
                    checkout scm
                }
            }
        }

        stage('Build') {
            steps {
                // Add your build steps here if needed
            }
        }

        stage('Deploy to S3') {
            when {
                // Trigger the stage only when changes are pushed to the main branch
                expression { 
                    return currentBuild.changeSets.any { it.branch == 'origin/main' }
                }
            }
            steps {
                script {
                    // Use AWS CLI to upload the file to the S3 bucket and replace existing file
                    sh "aws configure set aws_access_key_id ${AKIA6LGJSU6GMNQT4RPB}"
                    sh "aws configure set aws_secret_access_key ${P8FxcUbF8A0LyO8OQvhOc/TKIrIqPu68DUDFbOfh}"
                    sh "aws configure set default.region ${us-east-1}"
                    sh "aws s3 cp ${index.html} s3://${qasim-resume-website}/ --acl public-read --overwrite"
                }
            }
        }
    }
}

