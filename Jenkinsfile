pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build (no tests)') {
      steps { sh 'mvn -DskipTests clean' }
    }

    stage('Test') {
      steps { sh 'mvn test -DforkCount=1 -DreuseForks=false' }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
      junit 'target/surefire-reports/*.xml'
allure([
  includeProperties: false,
  jdk: '',
  commandline: 'allure',
  results: [[path: 'target/allure-results']]
])
    }
  }
}
