// Declarative Jenkinsfile for the netBanking Maven project
// - Cross-platform: uses `bat` on Windows and `sh` on Unix agents
// - Steps: checkout, build (maven clean package), run tests, publish test results, archive artifacts
// - Optional: Allure results publishing (requires Allure Jenkins plugin)

pipeline {
    agent any

    parameters {
        string(name: 'BRANCH', defaultValue: '*/main', description: 'SCM branch spec (example: */main or origin/develop)')
        booleanParam(name: 'SKIP_TESTS', defaultValue: false, description: 'Skip tests during the Maven build')
        string(name: 'MAVEN_GOALS', defaultValue: 'clean package', description: 'Maven goals to run')
    }

    environment {
        // Set MAVEN_OPTS or other env vars here if needed
        MAVEN_OPTS = '-Xmx1024m'
    }

    options {
        // keep some builds
        buildDiscarder(logRotator(numToKeepStr: '20'))
        timestamps()
        ansiColor('xterm')
    }

    stages {
        stage('Checkout') {
            steps {
                // Uses the pipeline's configured SCM; in Multibranch pipeline this checks out the current branch
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    // Compose mvn command. Respect the SKIP_TESTS parameter.
                    def skipFlag = params.SKIP_TESTS ? '-DskipTests=true' : ''
                    def goals = params.MAVEN_GOALS
                    def mvnCmd = "mvn -B ${goals} ${skipFlag}"

                    if (isUnix()) {
                        sh "${mvnCmd}"
                    } else {
                        // Use double quotes to preserve any windows special characters
                        bat "${mvnCmd}"
                    }
                }
            }
        }

        stage('Archive & Publish') {
            steps {
                // Always archive the produced jar(s) and test result directories if present
                script {
                    // Archive artifacts (jar) and test results if they exist
                    def artifactPattern = 'target/*.jar'
                    archiveArtifacts artifacts: artifactPattern, allowEmptyArchive: true

                    // Publish JUnit-style test results (Surefire / Failsafe)
                    junit testResults: 'target/surefire-reports/**/*.xml', allowEmptyResults: true

                    // Optionally capture Allure results folder (if your tests produce them)
                    // This requires the Allure plugin to be installed in Jenkins.
                    if (fileExists('allure-results')) {
                        echo 'Allure results found — will attempt to publish (if Allure plugin is configured).'
                        // The `allure` step is provided by the Allure Jenkins plugin; uncomment if plugin installed.
                        // allure results: [[path: 'allure-results']], includeProperties: false
                    } else {
                        echo 'No allure-results directory found; skipping Allure publishing.'
                    }
                }
            }
        }
    }

    post {
        always {
            // Always archive the console log and any additional artifacts you want
            echo "Build finished. Collecting artifacts / logs."
        }
        success {
            echo 'Build succeeded.'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
