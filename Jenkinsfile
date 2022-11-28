def mainDir=""
def ecrLoginHelper="docker-credential-ecr-login"
def region="ap-northeast-2"
def ecrUrl="526336633172.dkr.ecr.ap-northeast-2.amazonaws.com/pipeline-test"
def repository="pipeline-test"
def deployHost="hyper-x.kr"

pipeline {
    agent any

    stages {
        stage('Pull Codes from Github'){
            steps{
                checkout scm
            }
        }
        stage('Build Codes by Gradle') {
            steps {
                sh """
                pwd
                chmod +x gradlew
                ./gradlew clean build
                """
            }
        }
        stage('Build Docker Image by Jib & Push to AWS ECR Repository') {
            steps {
                withAWS(region:"${region}", credentials:"aws-key") {
                    ecrLogin()
                    sh """
                    aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 526336633172.dkr.ecr.ap-northeast-2.amazonaws.com
                    docker build -t pipeline-test .
                    docker tag pipeline-test:latest 526336633172.dkr.ecr.ap-northeast-2.amazonaws.com/pipeline-test:latest
                    docker push 526336633172.dkr.ecr.ap-northeast-2.amazonaws.com/pipeline-test:latest
                    """
                }
            }
        }
        stage('Deploy to AWS EC2 VM'){
            steps{
                sshagent(credentials : ["deploy-key"]) {
                    sh "ssh -o StrictHostKeyChecking=no ubuntu@${deployHost} \
                     'aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${ecrUrl}/${repository}; \
                      docker run -d -p 8087:8080 -t ${ecrUrl}/${repository}:${currentBuild.number};'"
                }
            }
        }
    }
}