pipeline {
    agent any
    
    tools { 
        maven 'm3' 
        jdk 'jdk17' 
    }
    
    stages {
         stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                    echo "JAVA_HOME = ${JAVA_HOME}"
                    mvn -version
                '''
                
            }
        }
        stage('Build') {
            
            steps {
                // Get some code from a GitHub repository
                //git 'https://github.com/mateuszlewicki/recordStore.git'

                // Run Maven on a Unix agent.
                sh 'mvn -Dmaven.test.failure.ignore=true install' 
                //sh "mvn  -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}

